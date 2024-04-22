package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateCookingTypeRequest;
import com.p20241061.management.api.model.request.update.UpdateCookingTypeRequest;
import com.p20241061.management.infrastructure.interfaces.ICookingTypeService;
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
public class CookingTypeHandler {

    private final ICookingTypeService cookingTypeService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateCookingTypeRequest> cookingTypeRequest = request.bodyToMono(CreateCookingTypeRequest.class)
                .doOnNext(objectValidator::validate);

        return cookingTypeRequest
                .flatMap(res -> cookingTypeService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateCookingTypeRequest> cookingTypeRequest = request.bodyToMono(UpdateCookingTypeRequest.class)
                .doOnNext(objectValidator::validate);

        UUID cookingTypeId = UUID.fromString(request.pathVariable("cookingTypeId"));

        return cookingTypeRequest
                .flatMap(res -> cookingTypeService.update(res, cookingTypeId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID cookingTypeId = UUID.fromString(request.pathVariable("cookingTypeId"));

        return cookingTypeService.delete(cookingTypeId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
