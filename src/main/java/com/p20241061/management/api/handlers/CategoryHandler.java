package com.p20241061.management.api.handlers;

import com.p20241061.management.api.model.request.category.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.category.update.UpdateCategoryRequest;
import com.p20241061.management.infrastructure.interfaces.category.ICategoryService;
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
public class CategoryHandler {

    private final ICategoryService categoryService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getCategoriesByCampusId(ServerRequest request) {
        Integer pageNumber = Integer.parseInt(request.queryParam("pageNumber").orElse("0"));
        Integer pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("5"));
        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return categoryService.getCategoryByCampusId(new PaginatedRequest(pageNumber, pageSize, "name", true), campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateCategoryRequest> categoryRequest = request.bodyToMono(CreateCategoryRequest.class)
                .doOnNext(objectValidator::validate);

        return categoryRequest
                .flatMap(res -> categoryService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UpdateCategoryRequest> categoryRequest = request.bodyToMono(UpdateCategoryRequest.class)
                .doOnNext(objectValidator::validate);

        UUID categoryId = UUID.fromString(request.pathVariable("categoryId"));

        return categoryRequest
                .flatMap(res -> categoryService.update(res, categoryId)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID categoryId = UUID.fromString(request.pathVariable("categoryId"));

        return categoryService.delete(categoryId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
