package com.p20241061.management.infrastructure.interfaces.relations;

import com.p20241061.management.api.model.request.create.relations.CreateCampusCategoryRequest;
import com.p20241061.management.api.model.response.relations.GetCategoriesByCampusResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ICampusCategoryService {
    Mono<GeneralResponse<String>> create(CreateCampusCategoryRequest request);
    Mono<GeneralResponse<String>> delete(UUID campusCategoryId);
}