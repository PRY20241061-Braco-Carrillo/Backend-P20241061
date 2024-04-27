package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.CategoryMapper;
import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.update.UpdateCategoryRequest;
import com.p20241061.management.api.model.response.CategoryResponse;
import com.p20241061.management.core.entities.Category;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.infrastructure.interfaces.ICategoryService;
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

import static com.p20241061.shared.models.enums.CampusName.CATEGORY_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Mono<GeneralResponse<String>> create(CreateCategoryRequest request) {

        return categoryRepository.save(categoryMapper.createRequestToModel(request))
                .flatMap(createdCategory -> Mono.just(GeneralResponse.<String>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(CATEGORY_ENTITY)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateCategoryRequest request, UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), CATEGORY_ENTITY)))
                .flatMap(category -> {

                    category.setName(request.getName());
                    category.setUrlImage(request.getUrlImage());

                    return categoryRepository.save(category)
                            .flatMap(updatedCategory -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(CATEGORY_ENTITY)
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.NOT_FOUND.name(), CATEGORY_ENTITY)))
                .flatMap(category -> categoryRepository.delete(category)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(CATEGORY_ENTITY)
                                .build())));
    }
}

