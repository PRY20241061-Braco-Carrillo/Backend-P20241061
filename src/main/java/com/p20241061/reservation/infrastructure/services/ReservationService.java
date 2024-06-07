package com.p20241061.reservation.infrastructure.services;

import com.p20241061.order.core.repositories.order.OrderRepository;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderRequestService;
import com.p20241061.reservation.api.mapping.ReservationMapper;
import com.p20241061.reservation.api.model.request.ChangeReservationStatusRequest;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.api.model.response.GetReservationDetailResponse;
import com.p20241061.reservation.api.model.response.GetReservationResponseByCampus;
import com.p20241061.reservation.core.repositories.ReservationRepository;
import com.p20241061.reservation.infrastructure.interfaces.IReservationService;
import com.p20241061.security.core.repositories.UserRepository;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final IOrderRequestService orderRequestService;
    private final ReservationMapper reservationMapper;
    private final OrderRequestRepository orderRequestRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Mono<GeneralResponse<String>> createReservation(CreateReservationRequest createReservationRequest) {

        return userRepository.findById(createReservationRequest.getUserId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "User not found")))
                .flatMap(user -> orderRequestService.create(createReservationRequest.getOrder())
                        .flatMap(order -> reservationRepository.save(reservationMapper.createRequestToModel(createReservationRequest, order.getData(), user))
                                .flatMap(reservation -> Mono.just(GeneralResponse.<String>builder()
                                        .code(SuccessCode.CREATED.name())
                                        .data("Reservation created successfully")
                                        .build()))));
    }

    @Override
    public Mono<GeneralResponse<List<GetReservationResponseByCampus>>> getReservationByCampus(UUID campusId) {
        return reservationRepository.getReservationByCampusId(campusId)
                .collectList()
                .flatMap(reservations -> Mono.just(GeneralResponse.<List<GetReservationResponseByCampus>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(reservationMapper.modelListToResponseList(reservations))
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<GetReservationDetailResponse>> getReservationDetail(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .flatMap(reservation -> orderRepository.getProductDetailByOrder(reservation.getOrderRequestId())
                        .collectList()
                        .flatMap(orderProduct -> orderRepository.getComplementDetailByOrder(reservation.getOrderRequestId())
                                .collectList()
                                .flatMap(orderComplement -> orderRepository.getOrderComboDetailByOrder(reservation.getOrderRequestId())
                                        .flatMap(orderCombo -> orderRepository.getOrderComboProductDetailByComboId(orderCombo.getOrderComboId())
                                                .collectList()
                                                .flatMap(orderComboProduct -> orderRepository.getOrderComboComplementDetailByComboId(orderCombo.getOrderComboId())
                                                        .collectList()
                                                        .flatMap(orderComboComplement -> {
                                                            orderCombo.setProducts(orderComboProduct);
                                                            orderCombo.setComplements(orderComboComplement);
                                                            return Mono.just(orderCombo);
                                                        })))
                                        .collectList()
                                        .flatMap(orderCombo -> orderRepository.getOrderPromotionProductDetailByOrder(reservation.getOrderRequestId())
                                                .flatMap(orderPromotion -> orderRepository.getOrderPromotionProductVariantDetailByPromotionId(orderPromotion.getOrderPromotionId())
                                                        .collectList()
                                                        .flatMap(orderPromotionProductVariant -> orderRepository.getOrderPromotionProductComplementDetailByPromotionId(orderPromotion.getOrderPromotionId())
                                                                .collectList()
                                                                .flatMap(orderPromotionComplement -> {
                                                                    orderPromotion.setProducts(orderPromotionProductVariant);
                                                                    orderPromotion.setComplements(orderPromotionComplement);
                                                                    return Mono.just(orderPromotion);
                                                                })))
                                                .collectList()
                                                .flatMap(orderProductPromotion -> orderRepository.getMenuDetailByOrder(reservation.getOrderRequestId())
                                                        .flatMap(orderMenu -> orderRepository.getMenuProductDetailByMenuId(orderMenu.getOrderMenuId())
                                                                .collectList()
                                                                .flatMap(orderMenuProduct -> {
                                                                    orderMenu.setProducts(orderMenuProduct);
                                                                    return Mono.just(orderMenu);
                                                                }))
                                                        .collectList()
                                                        .flatMap(orderMenu -> Mono.just(GeneralResponse.<GetReservationDetailResponse>builder()
                                                                .code(SuccessCode.SUCCESS.name())
                                                                .data(GetReservationDetailResponse.builder()
                                                                        .reservationId(reservation.getReservationId())
                                                                        .reservationStatus(reservation.getReservationStatus())
                                                                        .reservationDate(reservation.getReservationDate())
                                                                        .message(reservation.getMessage())
                                                                        .userQualification(reservation.getUserQualification())
                                                                        .products(orderProduct)
                                                                        .complements(orderComplement)
                                                                        .combos(orderCombo)
                                                                        .productPromotions(orderProductPromotion)
                                                                        .menus(orderMenu)
                                                                        .build())
                                                                .build())))))));
    }

    @Override
    public Mono<GeneralResponse<String>> changeReservationStatus(ChangeReservationStatusRequest changeReservationStatusRequest) {
        return null;
    }

    @Override
    public Mono<GeneralResponse<String>> deleteReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Reservation not found")))
                .flatMap(reservation -> orderRequestRepository.deleteById(reservation.getOrderRequestId()))
                .then(Mono.defer(() -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data("Reservation deleted successfully")
                        .build())));
    }
}
