package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.CategoryMapper;
import com.p20241061.management.api.model.request.create.relations.CreateCampusCategoryRequest;
import com.p20241061.management.api.model.response.CategoryResponse;
import com.p20241061.management.api.model.response.relations.CampusCategoryResponse;
import com.p20241061.management.core.entities.relations.CampusCategory;
import com.p20241061.management.core.repositories.CampusRepository;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.core.repositories.relations.CampusCategoryRepository;
import com.p20241061.management.infrastructure.interfaces.ICampusCategoryService;
import com.p20241061.shared.exceptions.CustomException;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class CampusCategoryService implements ICampusCategoryService {

    private final CampusCategoryRepository campusCategoryRepository;
    private final CampusRepository campusRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Mono<GeneralResponse<List<CategoryResponse>>> getCategoryByCampusId(PaginatedRequest paginatedRequest, UUID campusId) {


        return paginatedRequest.paginateData(campusCategoryRepository.getCampusCategoriesByCampusId(campusId))
                .flatMap(campusCategory -> categoryRepository.findById(campusCategory.getCategoryId())
                        .map(categoryMapper::modelToResponse))
                .collectList()
                .flatMap(categoryResponses -> Mono.just(GeneralResponse.<List<CategoryResponse>>builder()
                        .code(SuccessCode.SUCCESS.name())
                        .data(categoryResponses)
                        .build()));
    }

    @Override
    public Mono<GeneralResponse<CampusCategoryResponse>> create(CreateCampusCategoryRequest request) {
        return campusRepository.findById(request.getCampusId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus with id " + request.getCampusId() + " not found")))
                .flatMap(campus -> categoryRepository.findById(request.getCategoryId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Category with id " + request.getCategoryId() + " not found")))
                        .flatMap(category -> {
                            CampusCategory campusCategory = CampusCategory.builder()
                                    .categoryId(category.getCategoryId())
                                    .campusId(campus.getCampusId())
                                    .build();

                            return campusCategoryRepository.save(campusCategory)
                                    .flatMap(createdCampusCategory -> {
                                        CampusCategoryResponse campusCategoryResponse = CampusCategoryResponse.builder()
                                                .campusCategoryId(createdCampusCategory.getCampusCategoryId())
                                                .campus(campus)
                                                .category(category)
                                                .build();

                                        return Mono.just(GeneralResponse.<CampusCategoryResponse>builder()
                                                .code(HttpStatus.CREATED.name())
                                                .data(campusCategoryResponse)
                                                .build());
                                    });
                        })
                );
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusCategoryId) {
        return campusCategoryRepository.findById(campusCategoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Campus category with id " + campusCategoryId + " not found")))
                .flatMap(campusCategory -> campusCategoryRepository.delete(campusCategory)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Campus category with id " + campusCategoryId + " deleted successfully")
                                .build())
                        )
                );
    }
}
