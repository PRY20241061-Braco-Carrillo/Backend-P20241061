package com.p20241061.management.infrastructure.services.product;

import com.p20241061.management.api.model.request.product.update.UpdateNutritionalInformationRequest;
import com.p20241061.management.core.repositories.product.NutritionalInformationRepository;
import com.p20241061.management.infrastructure.interfaces.product.INutritionalInformationService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.p20241061.shared.models.enums.CampusName.NUTRITIONAL_INFORMATION_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class NutritionalInformationService implements INutritionalInformationService {

    private final NutritionalInformationRepository nutritionalInformationRepository;

    @Override
    public Mono<GeneralResponse<String>> update(UpdateNutritionalInformationRequest request, UUID nutritionalInformationId) {
        return nutritionalInformationRepository.findById(nutritionalInformationId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), NUTRITIONAL_INFORMATION_ENTITY)))
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

                    return nutritionalInformationRepository.save(nutritionalInformation).map(updatedNutritionalInformation -> GeneralResponse.<String>builder()
                            .code(SuccessCode.UPDATED.name())
                            .data(NUTRITIONAL_INFORMATION_ENTITY).build());
                }
        );
    }
}
