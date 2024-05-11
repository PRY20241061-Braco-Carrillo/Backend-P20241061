package com.p20241061.management.api.handlers;

import com.p20241061.management.infrastructure.interfaces.IComboService;
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
public class ComboHandler {
    private final IComboService comboService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return comboService.getAll()
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getComboDetailById(ServerRequest request) {

        UUID comboId = UUID.fromString(request.pathVariable("comboId"));

        return comboService.getComboDetailById(comboId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
