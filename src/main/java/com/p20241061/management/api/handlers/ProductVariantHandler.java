package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.product_variant.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.request.product_variant.update.UpdateProductVariantRequest;
import com.p20241061.management.infrastructure.interfaces.product_variant.IProductVariantService;
import com.p20241061.shared.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductVariantHandler {

    private final IProductVariantService productVariantService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAllByProductId(ServerRequest request) {
        UUID productId = UUID.fromString(request.pathVariable("productId"));

        return productVariantService.getProductDetailResponse(productId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateProductVariantRequest> productVariantRequest = request.bodyToMono(CreateProductVariantRequest.class)
                .doOnNext(objectValidator::validate);

        return productVariantRequest
                .flatMap(res -> productVariantService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateProductVariantRequest> productVariantRequest = request.bodyToMono(UpdateProductVariantRequest.class)
                .doOnNext(objectValidator::validate);

        UUID productVariantId = UUID.fromString(request.pathVariable("productVariantId"));

        return productVariantRequest
                .flatMap(res -> productVariantService.update(res, productVariantId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID productVariantId = UUID.fromString(request.pathVariable("productVariantId"));

        return productVariantService.delete(productVariantId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
