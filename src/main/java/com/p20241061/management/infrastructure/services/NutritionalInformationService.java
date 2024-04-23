package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.NutritionalInformationMapper;
import com.p20241061.management.api.model.request.update.UpdateNutritionalInformationRequest;
import com.p20241061.management.api.model.response.NutritionalInformationResponse;
import com.p20241061.management.core.repositories.NutritionalInformationRepository;
import com.p20241061.management.infrastructure.interfaces.INutritionalInformationService;
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
public class NutritionalInformationService implements INutritionalInformationService {

    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final NutritionalInformationMapper nutritionalInformationMapper;

    @Override
    public Mono<GeneralResponse<NutritionalInformationResponse>> update(UpdateNutritionalInformationRequest request, UUID nutritionalInformationId) {
        return nutritionalInformationRepository.findById(nutritionalInformationId)
                .flatMap(nutritionalInformation -> {
                    nutritionalInformation.setCalories(request.getCalories());
                    nutritionalInformation.setProteins(request.getProteins());
                    nutritionalInformation.setTotalFat(request.getTotalFat());
                    nutritionalInformation.setCarbohydrates(request.getCarbohydrates());
                    nutritionalInformation.setIsVegan(request.getIsVegan());
                    nutritionalInformation.setIsVegetarian(request.getIsVegetarian());
                    nutritionalInformation.setIsLowCalories(request.getIsLowCalories());
                    nutritionalInformation.setIsHighProtein(request.getIsHighProtein());
                    nutritionalInformation.setIsWithoutGluten(request.getIsWithoutGluten());
                    nutritionalInformation.setIsWithoutNut(request.getIsWithoutNut());
                    nutritionalInformation.setIsWithoutLactose(request.getIsWithoutLactose());
                    nutritionalInformation.setIsWithoutEggs(request.getIsWithoutEggs());
                    nutritionalInformation.setIsWithoutSeafood(request.getIsWithoutSeafood());
                    nutritionalInformation.setIsWithoutPig(request.getIsWithoutPig());

                    return nutritionalInformationRepository.save(nutritionalInformation).map(updatedNutritionalInformation -> GeneralResponse.<NutritionalInformationResponse>builder()
                            .code(SuccessCode.UPDATED.name())
                            .data(nutritionalInformationMapper.modelToResponse(updatedNutritionalInformation)).build());
                }
        );
    }
}
