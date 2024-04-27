package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateCampusRequest;
import com.p20241061.management.api.model.request.update.UpdateCampusRequest;
import com.p20241061.management.api.model.response.CampusResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ICampusService {
    Mono<GeneralResponse<CampusResponse>> getById(UUID campusId);
    Mono<GeneralResponse<List<CampusResponse>>> getByRestaurantId(PaginatedRequest paginatedRequest, Boolean available, UUID restaurantId);
    Mono<GeneralResponse<String>> create(CreateCampusRequest request);
    Mono<GeneralResponse<String>> update(UpdateCampusRequest request, UUID campusId);
    Mono<GeneralResponse<String>> delete(UUID campusId);
}
