package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.PromotionResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IPromotionService {
    Mono<GeneralResponse<PromotionResponse>> create(CreatePromotionRequest request);
    Mono<GeneralResponse<PromotionResponse>> update(UpdatePromotionRequest request, UUID promotionId);
    Mono<GeneralResponse<String>> delete(UUID promotionId);
}
