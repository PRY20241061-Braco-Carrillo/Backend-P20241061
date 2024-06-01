package com.p20241061.management.infrastructure.interfaces.promotion;

import com.p20241061.management.api.model.request.promotion.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.promotion.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.promotion.GetComboPromotionDetailResponse;
import com.p20241061.management.api.model.response.promotion.GetComboPromotionResponse;
import com.p20241061.management.api.model.response.promotion.GetProductVariantPromotionResponse;
import com.p20241061.management.api.model.response.promotion.GetPromotionByCampusCategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IPromotionService {

    Mono<GeneralResponse<GetProductVariantPromotionResponse>> getProductVariantPromotionById(UUID promotionId);

    Mono<GeneralResponse<List<GetPromotionByCampusCategoryResponse>>> getAllByCampusCategoryId(UUID campusCategoryId);

    Mono<GeneralResponse<List<GetComboPromotionResponse>>> getAllComboPromotion(UUID campusId);

    Mono<GeneralResponse<GetComboPromotionDetailResponse>> getComboPromotionDetail(UUID promotionId);

    Mono<GeneralResponse<String>> create(CreatePromotionRequest request);

    Mono<GeneralResponse<String>> update(UpdatePromotionRequest request, UUID promotionId);

    Mono<GeneralResponse<String>> delete(UUID promotionId);
}
