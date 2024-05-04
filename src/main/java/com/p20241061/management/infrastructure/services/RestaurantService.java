package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.mapping.RestaurantMapper;
import com.p20241061.management.api.model.request.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.request.update.UpdateRestaurantRequest;
import com.p20241061.management.api.model.response.RestaurantResponse;
import com.p20241061.management.core.repositories.RestaurantRepository;
import com.p20241061.management.infrastructure.interfaces.IRestaurantService;
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

import static com.p20241061.shared.models.enums.CampusName.RESTAURANT_ENTITY;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;


    @Override
    public Mono<GeneralResponse<List<RestaurantResponse>>> getAll(PaginatedRequest paginatedRequest, Boolean available) {

        return paginatedRequest.paginateData(restaurantRepository.findByIsAvailable(available))
                .collectList()
                .flatMap(restaurants -> {

                    List<RestaurantResponse> restaurantResponses = restaurants.stream()
                            .map(restaurantMapper::modelToResponse)
                            .toList();

                    return Mono.just(GeneralResponse.<List<RestaurantResponse>>builder()
                            .code(SuccessCode.SUCCESS.name())
                            .data(restaurantResponses)
                            .build());
                }
        );
    }

    @Override
    public Mono<GeneralResponse<String>> create(CreateRestaurantRequest request) {

        return restaurantRepository.existsByName(request.getName())
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTS.name(), RESTAURANT_ENTITY));
                            }
                            return restaurantRepository.save(restaurantMapper.createRequestToModel(request)).flatMap(createdRestaurant -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.CREATED.name())
                                    .data(RESTAURANT_ENTITY)
                                    .build()));
                        });
    }

    @Override
    public Mono<GeneralResponse<String>> update(UpdateRestaurantRequest request, UUID campusId) {
        return restaurantRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                .flatMap(restaurant -> restaurantRepository.existsByName(request.getName())
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTS.name(), RESTAURANT_ENTITY));
                            }
                            restaurant.setName(request.getName());
                            restaurant.setImageUrl(request.getImageUrl());
                            restaurant.setLogoUrl(request.getLogoUrl());
                            restaurant.setIsAvailable(request.getIsAvailable());

                            return restaurantRepository.save(restaurant).flatMap(updatedRestaurant -> Mono.just(GeneralResponse.<String>builder()
                                    .code(SuccessCode.UPDATED.name())
                                    .data(RESTAURANT_ENTITY)
                                    .build()));
                        }));
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusId) {
        return restaurantRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), RESTAURANT_ENTITY)))
                .flatMap(restaurant -> restaurantRepository.delete(restaurant)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data(RESTAURANT_ENTITY)
                                .build())));
    }
}
