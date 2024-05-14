package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.request.update.UpdateProductRequest;
import com.p20241061.management.api.model.response.get.GetProductByCategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import com.p20241061.shared.utils.PaginatedRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Mono<GeneralResponse<List<GetProductByCategoryResponse>>> getAllByCampusCategory(UUID campusCategoryId, Boolean available);
    Mono<GeneralResponse<String>> create(CreateProductRequest request);
    Mono<GeneralResponse<String>> update(UpdateProductRequest request, UUID productId);
    Mono<GeneralResponse<String>> delete(UUID productId);
}
