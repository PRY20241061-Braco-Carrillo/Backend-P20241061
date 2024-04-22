package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.model.request.create.CreateCookingTypeRequest;
import com.p20241061.management.api.model.request.update.UpdateCookingTypeRequest;
import com.p20241061.management.api.model.response.CookingTypeResponse;
import com.p20241061.management.core.entities.CookingType;
import com.p20241061.management.core.repositories.CookingTypeRepository;
import com.p20241061.management.infrastructure.interfaces.ICookingTypeService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CookingTypeService implements ICookingTypeService {
    private final CookingTypeRepository cookingTypeRepository;

    @Override
    public Mono<GeneralResponse<CookingTypeResponse>> create(CreateCookingTypeRequest request) {
        CookingType cookingType = CookingType.builder()
                .name(request.getName())
                .isAvailable(true)
                .build();

        return cookingTypeRepository.save(cookingType)
                .flatMap(createdCookingType -> {
                    CookingTypeResponse cookingTypeResponse = CookingTypeResponse.builder()
                            .cookingTypeId(createdCookingType.getCookingTypeId())
                            .name(createdCookingType.getName())
                            .isAvailable(createdCookingType.getIsAvailable())
                            .build();

                    return Mono.just(GeneralResponse.<CookingTypeResponse>builder()
                            .code(SuccessCode.CREATED.name())
                            .data(cookingTypeResponse)
                            .build());
                });
    }

    @Override
    public Mono<GeneralResponse<CookingTypeResponse>> update(UpdateCookingTypeRequest request, UUID cookingTypeId) {
        return cookingTypeRepository.findById(cookingTypeId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Cooking type with id " + cookingTypeId + " not found")))
                .flatMap(cookingType -> {
                    cookingType.setName(request.getName());
                    cookingType.setIsAvailable(request.getIsAvailable());

                    return cookingTypeRepository.save(cookingType)
                            .flatMap(updatedCookingType -> {
                                CookingTypeResponse cookingTypeResponse = CookingTypeResponse.builder()
                                        .cookingTypeId(updatedCookingType.getCookingTypeId())
                                        .name(updatedCookingType.getName())
                                        .isAvailable(updatedCookingType.getIsAvailable())
                                        .build();

                                return Mono.just(GeneralResponse.<CookingTypeResponse>builder()
                                        .code(SuccessCode.UPDATED.name())
                                        .data(cookingTypeResponse)
                                        .build());
                            });
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID cookingTypeId) {
        return cookingTypeRepository.findById(cookingTypeId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Cooking type with id " + cookingTypeId + " not found")))
                .flatMap(cookingType -> cookingTypeRepository.delete(cookingType)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Cooking type with id " + cookingTypeId + " deleted successfully")
                                .build())));
    }
}
