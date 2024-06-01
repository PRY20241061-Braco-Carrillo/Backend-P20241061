package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.restaurant.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.request.restaurant.update.UpdateRestaurantRequest;
import com.p20241061.management.infrastructure.interfaces.restaurant.IRestaurantService;
import com.p20241061.shared.utils.PaginatedRequest;
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
public class RestaurantHandler {
    private final IRestaurantService restaurantService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Integer pageNumber = Integer.parseInt(request.queryParam("pageNumber").orElse("0"));
        Integer pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("5"));
        Boolean available = request.queryParam("available").orElse("true").equals("true");

        return restaurantService.getAll(new PaginatedRequest(pageNumber, pageSize, "name", true), available)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateRestaurantRequest> restaurantRequest = request.bodyToMono(CreateRestaurantRequest.class)
                .doOnNext(objectValidator::validate);

        return restaurantRequest
                .flatMap(res -> restaurantService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateRestaurantRequest> restaurantRequest = request.bodyToMono(UpdateRestaurantRequest.class)
                .doOnNext(objectValidator::validate);

        UUID restaurantId = UUID.fromString(request.pathVariable("restaurantId"));

        return restaurantRequest
                .flatMap(res -> restaurantService.update(res, restaurantId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID restaurantId = UUID.fromString(request.pathVariable("restaurantId"));

        return restaurantService.delete(restaurantId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
