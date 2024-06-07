package com.p20241061.reservation.infrastructure.services;

import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderRequestService;
import com.p20241061.reservation.api.mapping.ReservationMapper;
import com.p20241061.reservation.api.model.request.ChangeReservationStatusRequest;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.api.model.response.GetReservationResponseByCampus;
import com.p20241061.reservation.core.repositories.ReservationRepository;
import com.p20241061.reservation.infrastructure.interfaces.IReservationService;
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

    @Override
    public Mono<GeneralResponse<String>> createReservation(CreateReservationRequest createReservationRequest) {

        return orderRequestService.create(createReservationRequest.getOrder())
                .flatMap(order -> reservationRepository.save(reservationMapper.createRequestToModel(createReservationRequest, order.getData()))
                        .flatMap(reservation -> Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.CREATED.name())
                                .data("Reservation created successfully")
                                .build())));
    }

    @Override
    public Mono<GeneralResponse<List<GetReservationResponseByCampus>>> getReservationByCampus(UUID campusId) {
        return null;
    }

    @Override
    public Mono<GeneralResponse<String>> changeReservationStatus(ChangeReservationStatusRequest changeReservationStatusRequest) {
        return null;
    }

    @Override
    public Mono<GeneralResponse<String>> deleteReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Reservation not found")))
                .flatMap(reservation ->  orderRequestRepository.deleteById(reservation.getOrderRequestId()))
                .then(Mono.defer(() -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data("Reservation deleted successfully")
                        .build())));
    }
}
