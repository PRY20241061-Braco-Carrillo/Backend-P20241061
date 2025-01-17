package com.p20241061.management.infrastructure.interfaces.category;

import com.p20241061.management.api.model.request.category.create.CreateCampusCategoryRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICampusCategoryService {
    Mono<GeneralResponse<String>> create(CreateCampusCategoryRequest request);

    Mono<GeneralResponse<String>> delete(UUID campusCategoryId);
}
