package com.p20241061.order.infrastructure.services;

import com.p20241061.order.api.mapping.order_request.OrderRequestMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboComplementMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboProductMapper;
import com.p20241061.order.api.mapping.order_request.complement.OrderComplementMapper;
import com.p20241061.order.api.mapping.order_request.menu.OrderMenuMapper;
import com.p20241061.order.api.mapping.order_request.menu.OrderMenuProductMapper;
import com.p20241061.order.api.mapping.order_request.product.OrderProductMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionComboMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionComplementMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionProductMapper;
import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboRequest;
import com.p20241061.order.api.model.request.order_request.complement.create.OrderComplementRequest;
import com.p20241061.order.api.model.request.order_request.menu.create.OrderMenuRequest;
import com.p20241061.order.api.model.request.order_request.product.create.OrderProductRequest;
import com.p20241061.order.api.model.request.order_request.promotion.create.OrderComboPromotionRequest;
import com.p20241061.order.api.model.request.order_request.promotion.create.OrderProductPromotionRequest;
import com.p20241061.order.api.model.response.CreateOrderRequestResponse;
import com.p20241061.order.api.model.response.ValidateOrderRequestCodeResponse;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.core.repositories.order_request.combo.OrderComboComplementRepository;
import com.p20241061.order.core.repositories.order_request.combo.OrderComboProductRepository;
import com.p20241061.order.core.repositories.order_request.combo.OrderComboRepository;
import com.p20241061.order.core.repositories.order_request.complement.OrderComplementRepository;
import com.p20241061.order.core.repositories.order_request.menu.OrderMenuProductRepository;
import com.p20241061.order.core.repositories.order_request.menu.OrderMenuRepository;
import com.p20241061.order.core.repositories.order_request.product.OrderProductRepository;
import com.p20241061.order.core.repositories.order_request.promotion.OrderPromotionComboRepository;
import com.p20241061.order.core.repositories.order_request.promotion.OrderPromotionComplementRepository;
import com.p20241061.order.core.repositories.order_request.promotion.OrderPromotionProductRepository;
import com.p20241061.order.core.repositories.order_request.promotion.OrderPromotionRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderRequestService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderRequestService implements IOrderRequestService {

    private final OrderRequestRepository orderRequestRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderComboRepository orderComboRepository;
    private final OrderComboProductRepository orderComboProductRepository;
    private final OrderComboComplementRepository orderComboComplementRepository;
    private final OrderComplementRepository orderComplementRepository;
    private final OrderPromotionRepository orderPromotionRepository;
    private final OrderPromotionComboRepository orderPromotionComboRepository;
    private final OrderPromotionProductRepository orderPromotionProductRepository;
    private final OrderPromotionComplementRepository orderPromotionComplementRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuProductRepository orderMenuProductRepository;

    private final OrderRequestMapper orderRequestMapper;
    private final OrderProductMapper orderProductMapper;
    private final OrderComboMapper orderComboMapper;
    private final OrderComboProductMapper orderComboProductMapper;
    private final OrderComboComplementMapper orderComboComplementMapper;
    private final OrderComplementMapper orderComplementMapper;
    private final OrderPromotionMapper orderPromotionMapper;
    private final OrderPromotionComboMapper orderPromotionComboMapper;
    private final OrderPromotionProductMapper orderPromotionProductMapper;
    private final OrderPromotionComplementMapper orderPromotionComplementMapper;
    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuProductMapper orderMenuProductMapper;

    @Override
    public Mono<GeneralResponse<CreateOrderRequestResponse>> create(CreateOrderRequestRequest request) {
        return orderRequestRepository.save(orderRequestMapper.createRequestToModel(request))
                .flatMap(orderRequest ->
                        saveOrderProduct(request.getProducts(), orderRequest.getOrderRequestId())
                                .thenMany(saveOrderComplement(request.getComplements(), orderRequest.getOrderRequestId()))
                                .thenMany(saveOrderCombo(request.getCombos(), orderRequest.getOrderRequestId()))
                                .thenMany(saveOrderComboPromotion(request.getComboPromotions(), orderRequest.getOrderRequestId()))
                                .thenMany(saveOrderProductPromotion(request.getProductPromotions(), orderRequest.getOrderRequestId()))
                                .thenMany(saveOrderMenu(request.getMenus(), orderRequest.getOrderRequestId()))
                                .then(Mono.just(GeneralResponse.<CreateOrderRequestResponse>builder()
                                        .code(SuccessCode.CREATED.name())
                                        .data(CreateOrderRequestResponse.builder()
                                                .orderRequestId(orderRequest.getOrderRequestId())
                                                .confirmationToken(orderRequest.getConfirmationToken())
                                                .totalPrice(orderRequest.getTotalPrice())
                                                .build())
                                        .build())
                                )
                );
    }

    @Override
    public Mono<GeneralResponse<ValidateOrderRequestCodeResponse>> validateOrderRequestCode(String confirmationToken) {
        return orderRequestRepository.findByConfirmationToken(confirmationToken)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Confirmation token invalid")))
                .flatMap(orderRequest -> Mono.just(GeneralResponse.<ValidateOrderRequestCodeResponse>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(ValidateOrderRequestCodeResponse.builder()
                                .orderRequestId(orderRequest.getOrderRequestId())
                                .build())
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<String>> deleteOrderRequest(UUID orderRequestId) {
        return orderRequestRepository.findById(orderRequestId)
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order Request not found")))
                .flatMap(orderRequest -> orderRequestRepository.deleteById(orderRequestId)
                .then(Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.DELETED.name())
                        .data("Order Request deleted")
                        .build())));
    }

    private Flux<String> saveOrderProduct(List<OrderProductRequest> products, UUID orderRequestId) {
        return Flux.fromIterable(products)
                .flatMap(product -> orderProductRepository.save(orderProductMapper.createRequestToModel(product, orderRequestId))
                        .flatMap(orderProduct -> Mono.just("Order Product created")));
    }

    private Flux<String> saveOrderComplement(List<OrderComplementRequest> complements, UUID orderRequestId) {
        return Flux.fromIterable(complements)
                .flatMap(complement -> orderComplementRepository.save(orderComplementMapper.createRequestToModel(complement, orderRequestId))
                        .flatMap(orderComplement -> Mono.just("Order Complement created")));

    }

    private Flux<String> saveOrderCombo(List<OrderComboRequest> combos, UUID orderRequestId) {
        return Flux.fromIterable(combos)
                .flatMap(combo -> orderComboRepository.save(orderComboMapper.createRequestToModel(combo, orderRequestId))
                        .flatMap(orderCombo ->
                                Flux.fromIterable(combo.getProducts())
                                        .flatMap(product -> orderComboProductRepository.save(orderComboProductMapper.createRequestToModel(product, orderCombo.getOrderComboId())))
                                        .thenMany(
                                                Flux.fromIterable(combo.getComplements())
                                                        .flatMap(complement -> orderComboComplementRepository.save(orderComboComplementMapper.createRequestToModel(complement, orderCombo.getOrderComboId())))
                                        )
                                        .then(Mono.just("Order Combo created"))
                        )
                );
    }

    private Flux<String> saveOrderComboPromotion(List<OrderComboPromotionRequest> comboPromotions, UUID orderRequestId) {
        return Flux.fromIterable(comboPromotions)
                .flatMapSequential(comboPromotion ->
                        orderPromotionRepository.save(orderPromotionMapper.createRequestToModel(comboPromotion, null, orderRequestId))
                                .flatMap(orderPromotion ->
                                        Flux.fromIterable(comboPromotion.getCombos())
                                                .flatMap(orderPromotionCombo ->
                                                        orderComboRepository.save(orderComboMapper.createRequestToModel(orderPromotionCombo.getCombo(), orderRequestId))
                                                                .flatMap(orderCombo ->
                                                                        Flux.fromIterable(orderPromotionCombo.getCombo().getProducts())
                                                                                .flatMap(product -> orderComboProductRepository.save(orderComboProductMapper.createRequestToModel(product, orderCombo.getOrderComboId())))
                                                                                .thenMany(Flux.fromIterable(orderPromotionCombo.getCombo().getComplements())
                                                                                        .flatMap(complement -> orderComboComplementRepository.save(orderComboComplementMapper.createRequestToModel(complement, orderCombo.getOrderComboId())))
                                                                                )
                                                                                .then(orderPromotionComboRepository.save(orderPromotionComboMapper.createRequestToModel(orderCombo.getOrderComboId(), orderPromotion.getOrderPromotionId())))
                                                                )
                                                )
                                                .then(Mono.just("Order Promotion Combo created"))
                                )
                );
    }

    private Flux<String> saveOrderProductPromotion(List<OrderProductPromotionRequest> productPromotions, UUID orderRequestId) {
        return Flux.fromIterable(productPromotions)
                .flatMap(productPromotion ->
                        orderPromotionRepository.save(orderPromotionMapper.createRequestToModel(null, productPromotion, orderRequestId))
                                .flatMap(orderPromotion ->
                                        Flux.fromIterable(productPromotion.getProducts())
                                                .flatMap(orderPromotionProduct ->
                                                        orderPromotionProductRepository.save(orderPromotionProductMapper.createRequestToModel(orderPromotionProduct, orderPromotion.getOrderPromotionId())))
                                                .thenMany(
                                                        Flux.fromIterable(productPromotion.getComplements())
                                                                .flatMap(orderPromotionComplement -> orderPromotionComplementRepository.save(orderPromotionComplementMapper.createRequestToModel(orderPromotionComplement, orderPromotion.getOrderPromotionId())))
                                                )
                                                .then(Mono.just("Order Product Promotion created for promotionId: " + orderPromotion.getOrderPromotionId())))
                );
    }

    private Flux<String> saveOrderMenu(List<OrderMenuRequest> menus, UUID orderRequestId) {
        return Flux.fromIterable(menus)
                .flatMap(menu -> orderMenuRepository.save(orderMenuMapper.createRequestToModel(menu, orderRequestId))
                        .flatMap(orderMenu ->
                                Flux.fromIterable(menu.getProducts())
                                        .flatMap(orderMenuProduct -> orderMenuProductRepository.save(orderMenuProductMapper.createRequestToModel(orderMenuProduct, orderMenu.getOrderMenuId())))
                                        .then(Mono.just("Order Menu created"))
                        )
                );
    }


}
