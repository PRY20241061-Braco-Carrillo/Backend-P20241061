package com.p20241061.order.infrastructure.services;

import com.p20241061.management.core.entities.restaurant.Campus;
import com.p20241061.management.core.repositories.restaurant.CampusRepository;
import com.p20241061.order.api.mapping.order.OrderMapper;
import com.p20241061.order.api.mapping.order_request.OrderRequestMapper;
import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.api.model.request.order.UpdateOrderStatusRequest;
import com.p20241061.order.api.model.response.get.GetAllOrderByCampusResponse;
import com.p20241061.order.api.model.response.get.GetOrderDetailResponse;
import com.p20241061.order.core.repositories.order.OrderRepository;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
import com.p20241061.security.core.repositories.UserRepository;
import com.p20241061.shared.config.SignalRClient;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.OrderStatus;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final CampusRepository campusRepository;
    private final OrderMapper orderMapper;
    private final OrderRequestService orderRequestService;
    private final OrderRequestMapper orderRequestMapper;
    private final SignalRClient signalRClient;

    @Override
    public Mono<GeneralResponse<List<GetAllOrderByCampusResponse>>> getAllOrderByCampus(UUID campusId) {
        return orderRepository.getAllOrderByCampus(campusId)
                .collectList()
                .flatMap(orders -> Mono.just(GeneralResponse.<List<GetAllOrderByCampusResponse>>builder()
                        .code(HttpStatus.OK.name())
                        .data(orders)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<GetOrderDetailResponse>> getOrderDetail(UUID orderRequestId) {
        return orderRepository.getOrderByOrderRequestId(orderRequestId)
                .flatMap(order -> orderRepository.getProductDetailByOrder(orderRequestId)
                        .collectList()
                        .flatMap(orderProduct -> orderRepository.getComplementDetailByOrder(orderRequestId)
                                .collectList()
                                .flatMap(orderComplement -> orderRepository.getOrderComboDetailByOrder(orderRequestId)
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
                                        .flatMap(orderCombo -> orderRepository.getOrderPromotionProductDetailByOrder(orderRequestId)
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
                                                .flatMap(orderProductPromotion -> orderRepository.getMenuDetailByOrder(orderRequestId)
                                                        .flatMap(orderMenu -> orderRepository.getMenuProductDetailByMenuId(orderMenu.getOrderMenuId())
                                                                .collectList()
                                                                .flatMap(orderMenuProduct -> {
                                                                    orderMenu.setProducts(orderMenuProduct);
                                                                    return Mono.just(orderMenu);
                                                                }))
                                                        .collectList()
                                                        .flatMap(orderMenu -> Mono.just(GeneralResponse.<GetOrderDetailResponse>builder()
                                                                .code(SuccessCode.SUCCESS.name())
                                                                .data(GetOrderDetailResponse.builder()
                                                                        .orderId(order.getOrderId())
                                                                        .orderStatus(order.getOrderStatus())
                                                                        .tableNumber(order.getTableNumber())
                                                                        .forTable(order.getForTable())
                                                                        .orderRequestDate(order.getOrderRequestDate())
                                                                        .totalPrice(order.getTotalPrice())
                                                                        .products(orderProduct)
                                                                        .complements(orderComplement)
                                                                        .combos(orderCombo)
                                                                        .productPromotions(orderProductPromotion)
                                                                        .menus(orderMenu)
                                                                        .build())
                                                                .build())))))));

    }

    @Override
    public Mono<GeneralResponse<GetAllOrderByCampusResponse>> getOrderByTableNumber(String tableNumber) {
        return orderRepository.getOrderByTableNumber(tableNumber)
                .map(order -> GeneralResponse.<GetAllOrderByCampusResponse>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(order)
                        .build());
    }

    @Override
    public Mono<GeneralResponse<String>> hasTokenBeenValidated(UUID orderRequestId) {
        return orderRequestRepository.findById(orderRequestId)
                .flatMap(orderRequest -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(orderRequest.getIsConfirmation() ? "VALIDATED" : "NOT_VALIDATED")
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateOrderRequest request) {

        return orderRequestRepository.findById(request.getOrderRequestId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order request not found")))
                .flatMap(orderRequest -> orderRepository.existsByOrderRequestId(request.getOrderRequestId())
                        .flatMap(existsOrderRequest -> {

                            if(existsOrderRequest) {
                                return Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.ALREADY_EXISTS.name(), "Order already exist by order request id"));
                            }

                            orderRequest.setTotalPrice(orderRequestMapper.getOrderRequestTotalPrice(request.getOrderRequest()));

                            Flux<String> createdOrderRequestFlux = Flux.concat(
                                    orderRequestService.saveOrderProduct(request.getOrderRequest().getProducts(), orderRequest.getOrderRequestId()),
                                    orderRequestService.saveOrderComplement(request.getOrderRequest().getComplements(), orderRequest.getOrderRequestId()),
                                    orderRequestService.saveOrderCombo(request.getOrderRequest().getCombos(), orderRequest.getOrderRequestId()),
                                    orderRequestService.saveOrderComboPromotion(request.getOrderRequest().getComboPromotions(), orderRequest.getOrderRequestId()),
                                    orderRequestService.saveOrderProductPromotion(request.getOrderRequest().getProductPromotions(), orderRequest.getOrderRequestId()),
                                    orderRequestService.saveOrderMenu(request.getOrderRequest().getMenus(), orderRequest.getOrderRequestId())
                            );

                            return createdOrderRequestFlux
                                    .then(orderRequestRepository.save(orderRequest))
                                    .flatMap(orderRequestUpdated -> {
                                        Mono<Campus> findCampus = campusRepository.findById(request.getCampusId())
                                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Campus not found")));

                                        if (request.getUserId() != null) {
                                            return userRepository.findById(request.getUserId())
                                                    .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "User not found")))
                                                    .flatMap(user -> findCampus
                                                            .flatMap(campus -> orderRepository.save(orderMapper.createRequestToModel(request))
                                                                    .map(order -> {
                                                                                //signalRClient.sendMessage(campus.getCampusId().toString(), "ORDER_CREATED");

                                                                                return GeneralResponse.<String>builder()
                                                                                        .code(HttpStatus.CREATED.name())
                                                                                        .data("Order created successfully")
                                                                                        .build();
                                                                            }
                                                                    )
                                                            )
                                                    );
                                        } else {
                                            return findCampus
                                                    .flatMap(campus -> orderRepository.save(orderMapper.createRequestToModel(request))
                                                            .map(order -> {
                                                                        //signalRClient.sendMessage(campus.getCampusId().toString(), "ORDER_CREATED");

                                                                        return GeneralResponse.<String>builder()
                                                                                .code(HttpStatus.CREATED.name())
                                                                                .data("Order created successfully")
                                                                                .build();
                                                                    }
                                                            )
                                                    );
                                        }
                                    });
                        }));
    }


    @Override
    public Mono<GeneralResponse<String>> updateOrderStatus(UpdateOrderStatusRequest orderStatusRequest) {
        return orderRepository.findById(orderStatusRequest.getOrderId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order not found")))
                .flatMap(order -> {
                    try {
                        OrderStatus status = OrderStatus.valueOf(orderStatusRequest.getOrderStatus());
                        order.setOrderStatus(status.name());
                        return orderRepository.save(order)
                                .thenReturn(GeneralResponse.<String>builder()
                                        .code(SuccessCode.UPDATED.name())
                                        .data("Order Status updated successfully")
                                        .build());
                    } catch (IllegalArgumentException e) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.name(), "Order Status is not valid"));
                    }
                });
    }

    @Override
    public Mono<GeneralResponse<String>> deleteOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order not found")))
                .flatMap(order -> orderRepository.delete(order)
                        .then(Mono.defer(() -> orderRequestRepository.deleteById(order.getOrderRequestId())
                                .then(Mono.just(GeneralResponse.<String>builder()
                                        .code(SuccessCode.DELETED.name())
                                        .data("Order deleted successfully")
                                        .build()))))
                );
    }

    private Mono<Boolean> alreadyExistOrderToOrderRequestId(UUID orderRequestId) {
        return orderRepository.existsByOrderRequestId(orderRequestId);
    }
}
