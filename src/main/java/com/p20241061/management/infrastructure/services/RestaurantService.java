package com.p20241061.management.infrastructure.services;

import com.p20241061.management.api.model.request.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.request.update.UpdateRestaurantRequest;
import com.p20241061.management.api.model.response.RestaurantResponse;
import com.p20241061.management.core.entities.Restaurant;
import com.p20241061.management.core.repositories.RestaurantRepository;
import com.p20241061.management.infrastructure.interfaces.IRestaurantService;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.SuccessCode;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;


    @Override
    public Mono<GeneralResponse<List<RestaurantResponse>>> getAll(PaginatedRequest paginatedRequest, Boolean available) {

        return paginatedRequest.paginateData(restaurantRepository.findByIsAvailable(available))
                .collectList()
                .flatMap(restaurants -> {

                    List<RestaurantResponse> restaurantResponses = restaurants.stream()
                            .map(restaurant -> RestaurantResponse.builder()
                                    .restaurantId(restaurant.getRestaurantId())
                                    .name(restaurant.getName())
                                    .imageUrl(restaurant.getImageUrl())
                                    .isAvailable(restaurant.getIsAvailable())
                                    .build())
                            .toList();

                    return Mono.just(GeneralResponse.<List<RestaurantResponse>>builder()
                            .code(SuccessCode.SUCCESS.name())
                            .data(restaurantResponses)
                            .build());
                }
        );
    }

    @Override
    public Mono<GeneralResponse<RestaurantResponse>> create(CreateRestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .logoUrl(request.getLogoUrl())
                .isAvailable(true)
                .build();

        return restaurantRepository.save(restaurant).flatMap(createdRestaurant -> {

            RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                    .restaurantId(createdRestaurant.getRestaurantId())
                    .name(createdRestaurant.getName())
                    .imageUrl(createdRestaurant.getImageUrl())
                    .logoUrl(createdRestaurant.getLogoUrl())
                    .isAvailable(createdRestaurant.getIsAvailable())
                    .build();

            return Mono.just(GeneralResponse.<RestaurantResponse>builder()
                    .code(SuccessCode.CREATED.name())
                    .data(restaurantResponse)
                    .build());
        });
    }

    @Override
    public Mono<GeneralResponse<RestaurantResponse>> update(UpdateRestaurantRequest request, UUID campusId) {
        return restaurantRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Restaurant with id " + campusId + " not found")))
                .flatMap(restaurant -> {

                    restaurant.setName(request.getName());
                    restaurant.setImageUrl(request.getImageUrl());
                    restaurant.setLogoUrl(request.getLogoUrl());
                    restaurant.setIsAvailable(request.getIsAvailable());

                    return restaurantRepository.save(restaurant).flatMap(updatedRestaurant -> {

                        RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                                .restaurantId(updatedRestaurant.getRestaurantId())
                                .name(updatedRestaurant.getName())
                                .imageUrl(updatedRestaurant.getImageUrl())
                                .logoUrl(updatedRestaurant.getLogoUrl())
                                .isAvailable(updatedRestaurant.getIsAvailable())
                                .build();

                        return Mono.just(GeneralResponse.<RestaurantResponse>builder()
                                .code(SuccessCode.UPDATED.name())
                                .data(restaurantResponse)
                                .build());
                    });
                });
    }

    @Override
    public Mono<GeneralResponse<String>> delete(UUID campusId) {
        return restaurantRepository.findById(campusId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Restaurant with id " + campusId + " not found")))
                .flatMap(restaurant -> restaurantRepository.delete(restaurant)
                        .then(Mono.just(GeneralResponse.<String>builder()
                                .code(SuccessCode.DELETED.name())
                                .data("Restaurant with id " + campusId + " deleted successfully")
                                .build())));
    }
}
