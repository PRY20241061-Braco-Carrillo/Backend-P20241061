package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateComplementRequest;
import com.p20241061.management.api.model.request.update.UpdateComplementRequest;
import com.p20241061.management.infrastructure.interfaces.IComplementService;
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
public class ComplementHandler {
    private final IComplementService complementService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateComplementRequest> complementRequest = request.bodyToMono(CreateComplementRequest.class)
                .doOnNext(objectValidator::validate);

        return complementRequest
                .flatMap(res -> complementService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateComplementRequest> complementRequest = request.bodyToMono(UpdateComplementRequest.class)
                .doOnNext(objectValidator::validate);

        UUID complementId = UUID.fromString(request.pathVariable("complementId"));

        return complementRequest
                .flatMap(res -> complementService.update(res, complementId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID complementId = UUID.fromString(request.pathVariable("complementId"));

        return complementService.delete(complementId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
