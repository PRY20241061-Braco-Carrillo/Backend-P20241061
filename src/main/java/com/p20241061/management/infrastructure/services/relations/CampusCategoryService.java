package com.p20241061.management.infrastructure.services.relations;

import com.p20241061.management.api.model.request.create.relations.CreateCampusCategoryRequest;
import com.p20241061.management.core.entities.relations.CampusCategory;
import com.p20241061.management.core.repositories.CampusRepository;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.api.model.response.relations.GetCategoriesByCampusResponse;
import com.p20241061.management.core.repositories.relations.CampusCategoryRepository;
import com.p20241061.management.infrastructure.interfaces.relations.ICampusCategoryService;
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

import static com.p20241061.shared.models.enums.CampusName.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampusCategoryService implements ICampusCategoryService {

    private final CampusCategoryRepository campusCategoryRepository;
    private final CampusRepository campusRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Mono<GeneralResponse<String>> create(CreateCampusCategoryRequest request) {
        return campusRepository.findById(request.getCampusId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_ENTITY)))
                .flatMap(campus -> categoryRepository.findById(request.getCategoryId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CATEGORY_ENTITY)))
                        .flatMap(category -> {
                            CampusCategory campusCategory = CampusCategory.builder()
                                    .categoryId(category.getCategoryId())
                                    .campusId(campus.getCampusId())
                                    .build();

                            return campusCategoryRepository.save(campusCategory)
                                    .flatMap(createdCampusCategory -> Mono.just(GeneralResponse.<String>builder()
                                            .code(HttpStatus.CREATED.name())
                                            .data(CAMPUS_ENTITY)
                                            .build()));
                        })
                );
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusCategoryId) {
        return campusCategoryRepository.findById(campusCategoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CAMPUS_CATEGORY_ENTITY)))
                .flatMap(campusCategory -> campusCategoryRepository.delete(campusCategory)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(CAMPUS_CATEGORY_ENTITY)
                                .build())
                        )
                );
    }
}
