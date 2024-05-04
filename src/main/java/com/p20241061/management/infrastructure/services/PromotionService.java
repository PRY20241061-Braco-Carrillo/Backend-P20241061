package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.PromotionMapper;
import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.request.update.UpdatePromotionRequest;
import com.p20241061.management.api.model.response.GetAllByCampusCategoryResponse;
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

    @Override
    public Mono<GeneralResponse<List<GetAllByCampusCategoryResponse>>> getAllByCampusCategoryId(PaginatedRequest paginatedRequest, UUID campusCategoryId) {
        return promotionRepository.getAllByCampusCategoryId(campusCategoryId)
                .collectList()
                .flatMap(promotions -> Mono.just(GeneralResponse.<List<GetAllByCampusCategoryResponse>>builder()
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
                    promotion.setPrice(request.getPrice());
                    promotion.setCookingTime(request.getCookingTime());
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
