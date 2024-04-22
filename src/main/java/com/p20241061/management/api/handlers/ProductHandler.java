package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.request.update.UpdateProductRequest;
import com.p20241061.management.infrastructure.interfaces.IProductService;
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
public class ProductHandler {
    private final IProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateProductRequest> productRequest = request.bodyToMono(CreateProductRequest.class)
                .doOnNext(objectValidator::validate);

        return productRequest
                .flatMap(res -> productService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateProductRequest> productRequest = request.bodyToMono(UpdateProductRequest.class)
                .doOnNext(objectValidator::validate);

        UUID productId = UUID.fromString(request.pathVariable("productId"));

        return productRequest
                .flatMap(res -> productService.update(res, productId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID productId = UUID.fromString(request.pathVariable("productId"));

        return productService.delete(productId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
