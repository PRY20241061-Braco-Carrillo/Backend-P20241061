package com.p20241061.management.infrastructure.interfaces.restaurant;

import com.p20241061.management.api.model.request.restaurant.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.request.restaurant.update.UpdateRestaurantRequest;
import com.p20241061.management.api.model.response.restaurant.RestaurantResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IRestaurantService {
    Mono<GeneralResponse<List<RestaurantResponse>>> getAll(PaginatedRequest paginatedRequest, Boolean available);

    Mono<GeneralResponse<String>> create(CreateRestaurantRequest request);

    Mono<GeneralResponse<String>> update(UpdateRestaurantRequest request, UUID campusId);

    Mono<GeneralResponse<String>> delete(UUID campusId);
}
