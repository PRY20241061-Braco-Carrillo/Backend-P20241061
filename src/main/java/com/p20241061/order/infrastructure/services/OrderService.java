package com.p20241061.order.infrastructure.services;

import com.p20241061.management.core.repositories.restaurant.CampusRepository;
import com.p20241061.order.api.mapping.order.OrderMapper;
import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.api.model.response.GetAllOrderByCampusResponse;
import com.p20241061.order.core.repositories.order.OrderRepository;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
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
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final CampusRepository campusRepository;
    private final OrderMapper orderMapper;

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
    public Mono<GeneralResponse<String>> getOrderDetail(UUID orderId) {
        return null;
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateOrderRequest request) {

        return orderRepository.existsByOrderRequestId(request.getOrderRequestId())
                .flatMap(existOrder -> {
                    if (existOrder) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTS.name(), "Order already exists"));
                    }

                    if (request.getUserId() != null) {
                        return userRepository.findById(request.getUserId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "User not found")))
                                .flatMap(user -> campusRepository.findById(request.getCampusId())
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Campus not found")))
                                        .flatMap(campus -> orderRequestRepository.existsById(request.getOrderRequestId()))
                                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order request not found")))
                                        .flatMap(orderRequest -> orderRepository.save(orderMapper.createRequestToModel(request))
                                                .map(order -> GeneralResponse.<String>builder()
                                                        .code(HttpStatus.CREATED.name())
                                                        .data("Order created successfully")
                                                        .build())));
                    } else {
                        return campusRepository.findById(request.getCampusId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Campus not found")))
                                .flatMap(campus -> orderRequestRepository.existsById(request.getOrderRequestId()))
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order request not found")))
                                .flatMap(orderRequest -> orderRepository.save(orderMapper.createRequestToModel(request))
                                        .map(order -> GeneralResponse.<String>builder()
                                                .code(HttpStatus.CREATED.name())
                                                .data("Order created successfully")
                                                .build()));
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
}
