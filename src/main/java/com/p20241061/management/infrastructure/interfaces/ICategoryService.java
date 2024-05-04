package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.update.UpdateCategoryRequest;
import com.p20241061.management.api.model.response.relations.GetCategoriesByCampusResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    Mono<GeneralResponse<List<GetCategoriesByCampusResponse>>> getCategoryByCampusId(PaginatedRequest paginatedRequest, UUID campusId);
    Mono<GeneralResponse<String>> create(CreateCategoryRequest request);
    Mono<GeneralResponse<String>> update(UpdateCategoryRequest request, UUID categoryId);
    Mono<GeneralResponse<String>> delete(UUID categoryId);
}
