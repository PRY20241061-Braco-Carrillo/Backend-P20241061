package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.CategoryMapper;
import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.update.UpdateCategoryRequest;
import com.p20241061.management.api.model.response.CategoryResponse;
import com.p20241061.management.core.entities.Category;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.infrastructure.interfaces.ICategoryService;
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
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Mono<GeneralResponse<CategoryResponse>> create(CreateCategoryRequest request) {

        return categoryRepository.save(categoryMapper.createRequestToModel(request))
                .flatMap(createdCategory -> Mono.just(GeneralResponse.<CategoryResponse>builder()
                        .code(SuccessCode.CREATED.name())
                        .data(categoryMapper.modelToResponse(createdCategory))
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<CategoryResponse>> update(UpdateCategoryRequest request, UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Category with id " + categoryId + " not found")))
                .flatMap(category -> {

                    category.setName(request.getName());
                    category.setUrlImage(request.getUrlImage());

                    return categoryRepository.save(category)
                            .flatMap(updatedCategory -> Mono.just(GeneralResponse.<CategoryResponse>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(categoryMapper.modelToResponse(updatedCategory))
                                    .build()));
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Category with id " + categoryId + " not found")))
                .flatMap(category -> categoryRepository.delete(category)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Category with id " + categoryId + " deleted successfully")
                                .build())));
    }
}

