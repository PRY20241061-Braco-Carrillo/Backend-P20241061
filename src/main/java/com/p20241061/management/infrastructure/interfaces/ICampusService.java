package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateCampusRequest;
import com.p20241061.management.api.model.request.update.UpdateCampusRequest;
import com.p20241061.management.api.model.response.CampusResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ICampusService {
    Mono<GeneralResponse<List<CampusResponse>>> getByRestaurantId(Integer pageNumber, Integer pageSize, Boolean available, UUID restaurantId);
    Mono<GeneralResponse<CampusResponse>> create(CreateCampusRequest request);
    Mono<GeneralResponse<CampusResponse>> update(UpdateCampusRequest request, UUID campusId);
    Mono<GeneralResponse<String>> delete(UUID campusId);
}
