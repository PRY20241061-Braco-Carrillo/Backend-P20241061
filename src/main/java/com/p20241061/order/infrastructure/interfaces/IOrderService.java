package com.p20241061.order.infrastructure.interfaces;

import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.api.model.request.order.UpdateOrderStatusRequest;
import com.p20241061.order.api.model.response.get.GetAllOrderByCampusResponse;
import com.p20241061.order.api.model.response.get.GetOrderDetailResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    Mono<GeneralResponse<List<GetAllOrderByCampusResponse>>> getAllOrderByCampus(UUID campusId);
    Mono<GeneralResponse<GetOrderDetailResponse>> getOrderDetail(UUID orderRequest);
    Mono<GeneralResponse<GetAllOrderByCampusResponse>> getOrderByTableNumber(String tableNumber);
    Mono<GeneralResponse<String>> create(CreateOrderRequest request);
    Mono<GeneralResponse<String>> updateOrderStatus(UpdateOrderStatusRequest orderStatusRequest);
    Mono<GeneralResponse<String>> deleteOrder(UUID orderId);
}
