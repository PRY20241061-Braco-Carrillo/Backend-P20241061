package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateCookingTypeRequest;
import com.p20241061.management.api.model.request.update.UpdateCookingTypeRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICookingTypeService {
    Mono<GeneralResponse<String>> create(CreateCookingTypeRequest request);
    Mono<GeneralResponse<String>> update(UpdateCookingTypeRequest request, UUID cookingTypeId);
    Mono<GeneralResponse<String>> delete(UUID cookingTypeId);
}
