package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.CreateCampusRequest;
import com.p20241061.management.api.model.request.update.UpdateCampusRequest;
import com.p20241061.management.infrastructure.interfaces.ICampusService;
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
public class CampusHandler {
    private final ICampusService campusService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getById(ServerRequest request) {

        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return campusService.getById(campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getByRestaurantId(ServerRequest request) {

        Integer pageNumber = Integer.parseInt(request.queryParam("pageNumber").orElse("0"));
        Integer pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("5"));
        Boolean available = request.queryParam("available").orElse("true").equals("true");
        UUID restaurantId = UUID.fromString(request.pathVariable("restaurantId"));

        return campusService.getByRestaurantId(new PaginatedRequest(pageNumber, pageSize, "name", true), available, restaurantId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateCampusRequest> campusRequest = request.bodyToMono(CreateCampusRequest.class)
                .doOnNext(objectValidator::validate);

        return campusRequest
                .flatMap(res -> campusService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateCampusRequest> campusRequest = request.bodyToMono(UpdateCampusRequest.class)
                .doOnNext(objectValidator::validate);

        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return campusRequest
                .flatMap(res -> campusService.update(res, campusId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return campusService.delete(campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
