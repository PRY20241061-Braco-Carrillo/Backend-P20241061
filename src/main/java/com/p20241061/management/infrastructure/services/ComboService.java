package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.model.response.ComboResponse;
import com.p20241061.management.api.model.response.get.GetComboDetailResponse;
import com.p20241061.management.core.repositories.ComboRepository;
import com.p20241061.management.infrastructure.interfaces.IComboService;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComboService implements IComboService {

    private final ComboRepository comboRepository;
    @Override
    public Mono<GeneralResponse<List<ComboResponse>>> getAll() {
        return comboRepository.getAllByIsAvailable(true)
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
    public Mono<GeneralResponse<GetComboDetailResponse>> getComboDetailById(String comboId) {
        return null;
    }
}
