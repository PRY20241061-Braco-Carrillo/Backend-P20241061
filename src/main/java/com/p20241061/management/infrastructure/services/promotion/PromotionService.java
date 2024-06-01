package com.p20241061.management.infrastructure.services.promotion;

import com.p20241061.management.api.mapping.ComplementMapper;
import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.mapping.PromotionMapper;
import com.p20241061.management.api.model.request.promotion.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.promotion.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.combo.GetComboDetailResponse;
import com.p20241061.management.api.model.response.combo.GetComboProductDetailResponse;
import com.p20241061.management.api.model.response.promotion.GetComboPromotionDetailResponse;
import com.p20241061.management.api.model.response.promotion.GetComboPromotionResponse;
import com.p20241061.management.api.model.response.promotion.GetProductVariantPromotionResponse;
import com.p20241061.management.api.model.response.promotion.GetPromotionByCampusCategoryResponse;
import com.p20241061.management.core.repositories.combo.ComboRepository;
import com.p20241061.management.core.repositories.complement.ComplementRepository;
import com.p20241061.management.core.repositories.product.NutritionalInformationRepository;
import com.p20241061.management.core.repositories.promotion.PromotionRepository;
import com.p20241061.management.infrastructure.interfaces.promotion.IPromotionService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
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
public class PromotionService implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final NutritionalInformationMapper nutritionalInformationMapper;
    private final ComplementRepository complementRepository;
    private final ComplementMapper complementMapper;
    private final ComboRepository comboRepository;

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
                                                .build()))
                                ))
                );
    }

    @Override
    public Mono<GeneralResponse<List<GetPromotionByCampusCategoryResponse>>> getAllByCampusCategoryId(UUID campusCategoryId) {
        return promotionRepository.getAllByCampusCategoryId(campusCategoryId)
                .collectList()
                .flatMap(promotions -> Mono.just(GeneralResponse.<List<GetPromotionByCampusCategoryResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(promotions)
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<List<GetComboPromotionResponse>>> getAllComboPromotion(UUID campusId) {
        return promotionRepository.getAllComboPromotion(campusId)
                .flatMap(comboPromotion -> comboRepository.getProductByComboId(comboPromotion.getComboId())
                        .collectList()
                        .map(comboProduct -> GetComboPromotionResponse.builder()
                                .promotionId(comboPromotion.getPromotionId())
                                .comboId(comboPromotion.getComboId())
                                .name(comboPromotion.getName())
                                .minCookingTime(comboPromotion.getMinCookingTime())
                                .maxCookingTime(comboPromotion.getMaxCookingTime())
                                .unitOfTimeCookingTime(comboPromotion.getUnitOfTimeCookingTime())
                                .amountPrice(comboPromotion.getAmountPrice())
                                .currencyPrice(comboPromotion.getCurrencyPrice())
                                .discount(comboPromotion.getDiscount())
                                .discountType(comboPromotion.getDiscountType())
                                .urlImage(comboPromotion.getUrlImage())
                                .products(comboProduct)
                                .build())
                )
                .collectList()
                .flatMap(comboPromotions -> Mono.just(GeneralResponse.<List<GetComboPromotionResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(comboPromotions)
                        .build())
                );
    }

    @Override
    public Mono<GeneralResponse<GetComboPromotionDetailResponse>> getComboPromotionDetail(UUID promotionId) {
        return promotionRepository.findById(promotionId)
                .flatMap(promotion -> comboRepository.findById(promotion.getComboId())
                        .flatMap(combo -> comboRepository.getProductByComboId(promotion.getComboId())
                                .flatMap(comboProduct -> comboRepository.getComboProductVariantByProductId(comboProduct.getProductId())
                                        .collectList()
                                        .map(productVariants -> GetComboProductDetailResponse.builder()
                                                .productId(comboProduct.getProductId())
                                                .name(comboProduct.getName())
                                                .description(comboProduct.getDescription())
                                                .urlImage(comboProduct.getUrlImage())
                                                .productAmount(comboProduct.getProductAmount())
                                                .productVariants(productVariants)
                                                .build())
                                )
                                .collectList()
                                .flatMap(comboProductDetail -> comboRepository.getComboComplementByComboId(promotion.getComboId())
                                        .collectList()
                                        .map(complement -> GetComboDetailResponse.builder()
                                                .comboId(combo.getComboId())
                                                .name(combo.getName())
                                                .maxCookingTime(combo.getMaxCookingTime())
                                                .minCookingTime(combo.getMinCookingTime())
                                                .unitOfTimeCookingTime(combo.getUnitOfTimeCookingTime())
                                                .amountPrice(combo.getAmountPrice())
                                                .currencyPrice(combo.getCurrencyPrice())
                                                .urlImage(combo.getUrlImage())
                                                .freeSauce(combo.getFreeSauce())
                                                .products(comboProductDetail)
                                                .complements(complement)
                                                .build()
                                        )
                                )
                        )
                        .flatMap(comboDetail -> Mono.just(GeneralResponse.<GetComboPromotionDetailResponse>builder()
                                .code(SuccessCode.SUCCESS.name())
                                .data(GetComboPromotionDetailResponse.builder()
                                        .promotionId(promotion.getPromotionId())
                                        .name(promotion.getName())
                                        .discount(promotion.getDiscount())
                                        .discountType(promotion.getDiscountType())
                                        .detail(promotion.getDetail())
                                        .urlImage(promotion.getUrlImage())
                                        .freeSauce(promotion.getFreeSauce())
                                        .comboId(promotion.getComboId())
                                        .comboDetail(comboDetail)
                                        .build())
                                .build())
                        ));
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
