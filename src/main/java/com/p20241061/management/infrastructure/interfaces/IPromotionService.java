package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.get.GetComboPromotionDetailResponse;
import com.p20241061.management.api.model.response.get.GetComboPromotionResponse;
import com.p20241061.management.api.model.response.get.GetProductVariantPromotionResponse;
import com.p20241061.management.api.model.response.get.GetPromotionByCampusCategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IPromotionService {

    Mono<GeneralResponse<GetProductVariantPromotionResponse>> getProductVariantPromotionById(UUID promotionId);
    Mono<GeneralResponse<List<GetPromotionByCampusCategoryResponse>>> getAllByCampusCategoryId(UUID campusCategoryId);
    Mono<GeneralResponse<List<GetComboPromotionResponse>>> getAllComboPromotion();
    Mono<GeneralResponse<GetComboPromotionDetailResponse>> getComboPromotionDetail(UUID promotionId);
    Mono<GeneralResponse<String>> create(CreatePromotionRequest request);
    Mono<GeneralResponse<String>> update(UpdatePromotionRequest request, UUID promotionId);
    Mono<GeneralResponse<String>> delete(UUID promotionId);
}
