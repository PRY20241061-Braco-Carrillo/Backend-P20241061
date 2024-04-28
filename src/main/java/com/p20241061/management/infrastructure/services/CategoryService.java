package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.CategoryMapper;
import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.request.update.UpdateCategoryRequest;
import com.p20241061.management.core.repositories.CategoryRepository;
import com.p20241061.management.core.repositories.RestaurantRepository;
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
import static com.p20241061.shared.models.enums.CampusName.RESTAURANT_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Mono<GeneralResponse<String>> create(CreateCategoryRequest request) {

        return restaurantRepository.findById(request.getRestaurantId())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                .flatMap(restaurant -> {

                    Mono<Boolean> existRestaurant = categoryRepository.existsByNameAndRestaurantId(request.getName(), request.getRestaurantId());

                    return existRestaurant
                            .flatMap(exist ->
                                    Boolean.TRUE.equals(exist) ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTS.name(), CATEGORY_ENTITY))
                                            : categoryRepository.save(categoryMapper.createRequestToModel(request))
                                            .flatMap(createdCategory -> Mono.just(GeneralResponse.<String>builder()
                                                    .code(SuccessCode.CREATED.name())
                                                    .data(CATEGORY_ENTITY)
                                                    .build())));

                });
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateCategoryRequest request, UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CATEGORY_ENTITY)))
                .flatMap(category -> restaurantRepository.findById(request.getRestaurantId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                        .flatMap(restaurant -> {

                            Mono<Boolean> existRestaurant = categoryRepository.existsByNameAndRestaurantId(request.getName(), request.getRestaurantId());

                            return existRestaurant
                                    .flatMap(exist ->
                                            {
                                                if (Boolean.TRUE.equals(exist)) {
                                                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTS.name(), CATEGORY_ENTITY));
                                                } else {
                                                    category.setRestaurantId(restaurant.getRestaurantId());
                                                    category.setName(request.getName());
                                                    category.setUrlImage(request.getUrlImage());

                                                    return categoryRepository.save(category)
                                                            .flatMap(updatedCategory -> Mono.just(GeneralResponse.<String>builder()
                                                                    .code(SuccessCode.UPDATED.name())
                                                                    .data(CATEGORY_ENTITY)
                                                                    .build()));
                                                }

                                            }
                                    );
                        }));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), CATEGORY_ENTITY)))
                .flatMap(category -> categoryRepository.delete(category)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(CATEGORY_ENTITY)
                                .build())));
    }
}

