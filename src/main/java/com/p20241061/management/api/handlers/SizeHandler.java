package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateSizeRequest;
import com.p20241061.management.api.model.request.update.UpdateSizeRequest;
import com.p20241061.management.infrastructure.interfaces.ISizeService;
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
public class SizeHandler {
    private final ISizeService sizeService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateSizeRequest> sizeRequest = request.bodyToMono(CreateSizeRequest.class)
                .doOnNext(objectValidator::validate);

        return sizeRequest
                .flatMap(res -> sizeService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateSizeRequest> sizeRequest = request.bodyToMono(UpdateSizeRequest.class)
                .doOnNext(objectValidator::validate);

        UUID sizeId = UUID.fromString(request.pathVariable("sizeId"));

        return sizeRequest
                .flatMap(res -> sizeService.update(res, sizeId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID sizeId = UUID.fromString(request.pathVariable("sizeId"));

        return sizeService.delete(sizeId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
