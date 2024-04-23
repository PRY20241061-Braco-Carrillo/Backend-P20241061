package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.create.relations.CreateCampusCategoryRequest;
import com.p20241061.management.infrastructure.interfaces.ICampusCategoryService;
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
public class CampusCategoryHandler {

    private final ICampusCategoryService campusCategoryService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getCategoriesByCampusId(ServerRequest request) {
        Integer pageNumber = Integer.parseInt(request.queryParam("pageNumber").orElse("0"));
        Integer pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("5"));
        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return campusCategoryService.getCategoryByCampusId(new PaginatedRequest(pageNumber, pageSize, "name", true), campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateCampusCategoryRequest> campusCategoryRequest = request.bodyToMono(CreateCampusCategoryRequest.class)
                .doOnNext(objectValidator::validate);

        return campusCategoryRequest
                .flatMap(res -> campusCategoryService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID campusCategoryId = UUID.fromString(request.pathVariable("campusCategoryId"));

        return campusCategoryService.delete(campusCategoryId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
