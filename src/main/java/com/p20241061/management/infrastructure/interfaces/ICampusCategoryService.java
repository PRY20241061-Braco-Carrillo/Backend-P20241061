package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.relations.CreateCampusCategoryRequest;
import com.p20241061.management.api.model.response.relations.CampusCategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICampusCategoryService {
    Mono<GeneralResponse<CampusCategoryResponse>> create(CreateCampusCategoryRequest request);
    Mono<GeneralResponse<String>> delete(UUID campusCategoryId);
}
