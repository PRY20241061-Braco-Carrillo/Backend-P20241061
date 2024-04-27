package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.CookingTypeMapper;
import com.p20241061.management.api.model.request.create.CreateCookingTypeRequest;
import com.p20241061.management.api.model.request.update.UpdateCookingTypeRequest;
import com.p20241061.management.api.model.response.CookingTypeResponse;
import com.p20241061.management.core.entities.CookingType;
import com.p20241061.management.core.repositories.CookingTypeRepository;
import com.p20241061.management.infrastructure.interfaces.ICookingTypeService;
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

import static com.p20241061.shared.models.enums.CampusName.COOKING_TYPE_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class CookingTypeService implements ICookingTypeService {
    private final CookingTypeRepository cookingTypeRepository;
    private final CookingTypeMapper cookingTypeMapper;

    @Override
    public Mono<GeneralResponse<String>> create(CreateCookingTypeRequest request) {

        return cookingTypeRepository.save(cookingTypeMapper.createRequestToModel(request))
                .flatMap(createdCookingType -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(COOKING_TYPE_ENTITY)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateCookingTypeRequest request, UUID cookingTypeId) {
        return cookingTypeRepository.findById(cookingTypeId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), COOKING_TYPE_ENTITY)))
                .flatMap(cookingType -> {
                    cookingType.setName(request.getName());
                    cookingType.setIsAvailable(request.getIsAvailable());

                    return cookingTypeRepository.save(cookingType)
                            .flatMap(updatedCookingType -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(COOKING_TYPE_ENTITY)
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID cookingTypeId) {
        return cookingTypeRepository.findById(cookingTypeId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), COOKING_TYPE_ENTITY)))
                .flatMap(cookingType -> cookingTypeRepository.delete(cookingType)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(COOKING_TYPE_ENTITY)
                                .build())));
    }
}
