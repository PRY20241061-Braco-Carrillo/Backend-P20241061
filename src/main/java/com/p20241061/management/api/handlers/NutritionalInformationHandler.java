package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.product.update.UpdateNutritionalInformationRequest;
import com.p20241061.management.infrastructure.interfaces.product.INutritionalInformationService;
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
public class NutritionalInformationHandler {

    private final INutritionalInformationService nutritionalInformationService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateNutritionalInformationRequest> nutritionalInformationRequest = request.bodyToMono(UpdateNutritionalInformationRequest.class)
                .doOnNext(objectValidator::validate);

        UUID nutritionalInformationId = UUID.fromString(request.pathVariable("nutritionalInformationId"));

        return nutritionalInformationRequest
                .flatMap(res -> nutritionalInformationService.update(res, nutritionalInformationId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }


}
