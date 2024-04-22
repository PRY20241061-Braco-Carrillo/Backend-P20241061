package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateMenuRequest;
import com.p20241061.management.api.model.request.update.UpdateMenuRequest;
import com.p20241061.management.infrastructure.interfaces.IMenuService;
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
public class MenuHandler {
    private final IMenuService menuService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateMenuRequest> menuRequest = request.bodyToMono(CreateMenuRequest.class)
                .doOnNext(objectValidator::validate);

        return menuRequest
                .flatMap(res -> menuService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateMenuRequest> menuRequest = request.bodyToMono(UpdateMenuRequest.class)
                .doOnNext(objectValidator::validate);

        UUID menuId = UUID.fromString(request.pathVariable("menuId"));

        return menuRequest
                .flatMap(res -> menuService.update(res, menuId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID menuId = UUID.fromString(request.pathVariable("menuId"));

        return menuService.delete(menuId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
