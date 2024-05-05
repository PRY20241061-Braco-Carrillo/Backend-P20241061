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

@Component
@Slf4j
@RequiredArgsConstructor
public class ComboHandler {
//    private final IComboService comboService;
//    private final ObjectValidator objectValidator;
//
//    public Mono<ServerResponse> getAll(ServerRequest request) {
//        return comboService.getAll()
//                .flatMap(response -> ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(response));
//    }
}
