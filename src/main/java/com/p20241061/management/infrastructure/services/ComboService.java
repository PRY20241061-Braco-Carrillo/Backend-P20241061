package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.model.response.ComboResponse;
import com.p20241061.management.api.model.response.get.GetComboDetailResponse;
import com.p20241061.management.api.model.response.get.GetComboProductDetailResponse;
import com.p20241061.management.core.repositories.ComboRepository;
import com.p20241061.management.infrastructure.interfaces.IComboService;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComboService implements IComboService {

    private final ComboRepository comboRepository;

    @Override
    public Mono<GeneralResponse<List<ComboResponse>>> getAll(UUID campusId) {
        return comboRepository.getAllCombos(campusId)
                .flatMap(combos -> comboRepository.getProductByComboId(combos.getComboId())
                        .collectList()
                        .map(comboProduct -> ComboResponse.builder()
                                .comboId(combos.getComboId())
                                .name(combos.getName())
                                .maxCookingTime(combos.getMaxCookingTime())
                                .minCookingTime(combos.getMinCookingTime())
                                .unitOfTimeCookingTime(combos.getUnitOfTimeCookingTime())
                                .amountPrice(combos.getAmountPrice())
                                .currencyPrice(combos.getCurrencyPrice())
                                .urlImage(combos.getUrlImage())
                                .freeSauce(combos.getFreeSauce())
                                .products(comboProduct)
                                .build())
                )
                .collectList()
                .flatMap(combos -> Mono.just(GeneralResponse.<List<ComboResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(combos)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<GetComboDetailResponse>> getComboDetailById(UUID comboId) {
        return comboRepository.findById(comboId)
                .flatMap(combo -> comboRepository.getProductByComboId(comboId)
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
                        .flatMap(comboProductDetail -> comboRepository.getComboComplementByComboId(comboId)
                                .collectList()
                                .flatMap(complement -> Mono.just(GeneralResponse.<GetComboDetailResponse>builder()
                                        .code(SuccessCode.SUCCESS.name())
                                        .data(GetComboDetailResponse.builder()
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
                                                .build())
                                        .build()))
                        )
                );
    }
}
