package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.update.UpdateCategoryRequest;
import com.p20241061.management.api.model.response.CategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICategoryService {
    Mono<GeneralResponse<CategoryResponse>> create(CreateCategoryRequest request);
    Mono<GeneralResponse<CategoryResponse>> update(UpdateCategoryRequest request, UUID categoryId);
    Mono<GeneralResponse<String>> delete(UUID categoryId);
}
