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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;


    @Override
    public Mono<GeneralResponse<RestaurantResponse>> create(CreateRestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .isAvailable(true)
                .build();

        return restaurantRepository.save(restaurant).flatMap(createdRestaurant -> {

            RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                    .restaurantId(createdRestaurant.getRestaurantId())
                    .name(createdRestaurant.getName())
                    .imageUrl(createdRestaurant.getImageUrl())
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
                    restaurant.setIsAvailable(request.getIsAvailable());

                    return restaurantRepository.save(restaurant).flatMap(updatedRestaurant -> {

                        RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                                .restaurantId(updatedRestaurant.getRestaurantId())
                                .name(updatedRestaurant.getName())
                                .imageUrl(updatedRestaurant.getImageUrl())
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
