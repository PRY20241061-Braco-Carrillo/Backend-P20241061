package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.infrastructure.interfaces.IPromotionService;
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
public class PromotionHandler {
    private final IPromotionService promotionService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreatePromotionRequest> promotionRequest = request.bodyToMono(CreatePromotionRequest.class)
                .doOnNext(objectValidator::validate);

        return promotionRequest
                .flatMap(res -> promotionService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdatePromotionRequest> promotionRequest = request.bodyToMono(UpdatePromotionRequest.class)
                .doOnNext(objectValidator::validate);

        UUID promotionId = UUID.fromString(request.pathVariable("promotionId"));

        return promotionRequest
                .flatMap(res -> promotionService.update(res, promotionId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID promotionId = UUID.fromString(request.pathVariable("promotionId"));

        return promotionService.delete(promotionId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
