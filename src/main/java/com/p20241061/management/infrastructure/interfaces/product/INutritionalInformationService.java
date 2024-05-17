package com.p20241061.management.infrastructure.interfaces.product;

import com.p20241061.management.api.model.request.product.update.UpdateNutritionalInformationRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface INutritionalInformationService {
    Mono<GeneralResponse<String>> update(UpdateNutritionalInformationRequest request, UUID nutritionalInformationId);
}
