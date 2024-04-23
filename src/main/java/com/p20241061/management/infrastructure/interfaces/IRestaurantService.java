package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.request.update.UpdateRestaurantRequest;
import com.p20241061.management.api.model.response.RestaurantResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IRestaurantService {
    Mono<GeneralResponse<List<RestaurantResponse>>> getAll(Integer pageNumber, Integer pageSize, Boolean available);
    Mono<GeneralResponse<RestaurantResponse>> create(CreateRestaurantRequest request);
    Mono<GeneralResponse<RestaurantResponse>> update(UpdateRestaurantRequest request, UUID campusId);
    Mono<GeneralResponse<String>> delete(UUID campusId);
}
