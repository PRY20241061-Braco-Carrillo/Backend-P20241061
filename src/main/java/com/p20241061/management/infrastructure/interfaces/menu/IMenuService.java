package com.p20241061.management.infrastructure.interfaces.menu;

import com.p20241061.management.api.model.request.menu.create.CreateMenuRequest;
import com.p20241061.management.api.model.request.menu.update.UpdateMenuRequest;
import com.p20241061.management.api.model.response.menu.MenuResponse;
import com.p20241061.management.api.model.response.menu.GetMenuDetailsResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IMenuService {
    Mono<GeneralResponse<List<MenuResponse>>> getAllByCampus(UUID campusId);
    Mono<GeneralResponse<GetMenuDetailsResponse>> getMenuDetailById(UUID menuId);
    Mono<GeneralResponse<String>> create(CreateMenuRequest request);
    Mono<GeneralResponse<String>> update(UpdateMenuRequest request, UUID menuId);
    Mono<GeneralResponse<String>> delete(UUID menuId);
}
