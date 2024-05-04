package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateMenuRequest;
import com.p20241061.management.api.model.request.update.UpdateMenuRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IMenuService {
    Mono<GeneralResponse<String>> create(CreateMenuRequest request);
    Mono<GeneralResponse<String>> update(UpdateMenuRequest request, UUID menuId);
    Mono<GeneralResponse<String>> delete(UUID menuId);
}
