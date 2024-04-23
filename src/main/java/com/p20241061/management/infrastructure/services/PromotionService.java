package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.PromotionMapper;
import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.PromotionResponse;
import com.p20241061.management.core.entities.Promotion;
import com.p20241061.management.core.repositories.PromotionRepository;
import com.p20241061.management.infrastructure.interfaces.IPromotionService;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService  implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    @Override
    public Mono<GeneralResponse<PromotionResponse>> create(CreatePromotionRequest request) {

        return promotionRepository.save(promotionMapper.createRequestToModel(request))
                .flatMap(createdPromotion -> Mono.just(GeneralResponse.<PromotionResponse>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(promotionMapper.modelToResponse(createdPromotion))
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<PromotionResponse>> update(UpdatePromotionRequest request, UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .switchIfEmpty(Mono.error(new Exception("Promotion with id " + promotionId + " not found")))
                .flatMap(promotion -> {
                    promotion.setPrice(request.getPrice());
                    promotion.setCookingTime(request.getCookingTime());
                    promotion.setDiscount(request.getDiscount());
                    promotion.setDiscountType(request.getDiscountType());
                    promotion.setDetail(request.getDetail());
                    promotion.setFreeSauce(request.getFreeSauce());
                    promotion.setFreeComplements(request.getFreeComplements());
                    promotion.setUrlImage(request.getUrlImage());

                    return promotionRepository.save(promotion)
                            .flatMap(updatedPromotion -> Mono.just(GeneralResponse.<PromotionResponse>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(promotionMapper.modelToResponse(updatedPromotion))
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .switchIfEmpty(Mono.error(new Exception("Promotion with id " + promotionId + " not found")))
                .flatMap(promotion -> promotionRepository.delete(promotion)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Promotion with id " + promotionId + " deleted successfully")
                                .build()))
                );
    }
}
