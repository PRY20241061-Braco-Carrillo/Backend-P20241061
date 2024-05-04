package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.ComplementMapper;
import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.mapping.PromotionMapper;
import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.GetComboPromotionResponse;
import com.p20241061.management.api.model.response.GetProductVariantByProductResponse;
import com.p20241061.management.api.model.response.GetProductVariantPromotionResponse;
import com.p20241061.management.api.model.response.GetPromotionByCampusCategoryResponse;
import com.p20241061.management.core.repositories.ComplementRepository;
import com.p20241061.management.core.repositories.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.PromotionRepository;
import com.p20241061.management.infrastructure.interfaces.IPromotionService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.p20241061.shared.models.enums.CampusName.PROMOTION_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService  implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final NutritionalInformationMapper nutritionalInformationMapper;
    private final ComplementRepository complementRepository;
    private final ComplementMapper complementMapper;

    @Override
    public Mono<GeneralResponse<GetProductVariantPromotionResponse>> getProductVariantPromotionById(UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PROMOTION_ENTITY)))
                .flatMap(promotion -> promotionRepository.getVariantPromotionDetail(promotionId)
                        .collectList()
                        .flatMap(productVariants -> nutritionalInformationRepository.findById(productVariants.get(0).getNutritionalInformationId())
                                .flatMap(nutritionalInformation -> complementRepository.getComplementByProductId(productVariants.get(0).getProductId())
                                        .collectList()
                                        .flatMap(complements -> Mono.just(GeneralResponse.<GetProductVariantPromotionResponse>builder()
                                                .code(SuccessCode.SUCCESS.name())
                                                .data(GetProductVariantPromotionResponse.builder()
                                                        .promotion(promotionMapper.modelToResponse(promotion))
                                                        .productVariants(productVariants)
                                                        .nutritionalInformation(nutritionalInformationMapper.modelToResponse(nutritionalInformation))
                                                        .complements(complementMapper.modelToListResponse(complements))
                                                        .build())
                                                .build()) )
                ))
        );
    }

    @Override
    public Mono<GeneralResponse<List<GetComboPromotionResponse>>> getComboPromotionById(UUID promotionId) {
        return null;
    }

    @Override
    public Mono<GeneralResponse<List<GetPromotionByCampusCategoryResponse>>> getAllByCampusCategoryId(PaginatedRequest paginatedRequest, UUID campusCategoryId) {
        return promotionRepository.getAllByCampusCategoryId(campusCategoryId)
                .collectList()
                .flatMap(promotions -> Mono.just(GeneralResponse.<List<GetPromotionByCampusCategoryResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(promotions)
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreatePromotionRequest request) {

        return promotionRepository.save(promotionMapper.createRequestToModel(request))
                .flatMap(createdPromotion -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(PROMOTION_ENTITY)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdatePromotionRequest request, UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PROMOTION_ENTITY)))
                .flatMap(promotion -> {
                    promotion.setDiscount(request.getDiscount());
                    promotion.setDiscountType(request.getDiscountType());
                    promotion.setDetail(request.getDetail());
                    promotion.setFreeSauce(request.getFreeSauce());
                    promotion.setFreeComplements(request.getFreeComplements());
                    promotion.setUrlImage(request.getUrlImage());

                    return promotionRepository.save(promotion)
                            .flatMap(updatedPromotion -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(PROMOTION_ENTITY)
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), PROMOTION_ENTITY)))
                .flatMap(promotion -> promotionRepository.delete(promotion)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(PROMOTION_ENTITY)
                                .build()))
                );
    }
}
