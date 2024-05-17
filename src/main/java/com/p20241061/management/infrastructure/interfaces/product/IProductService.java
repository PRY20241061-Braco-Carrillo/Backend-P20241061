package com.p20241061.management.infrastructure.interfaces.product;

import com.p20241061.management.api.model.request.product.create.CreateProductRequest;
import com.p20241061.management.api.model.request.product.update.UpdateProductRequest;
import com.p20241061.management.api.model.response.product.GetProductByCategoryResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Mono<GeneralResponse<List<GetProductByCategoryResponse>>> getAllByCampusCategory(UUID campusCategoryId, Boolean available);
    Mono<GeneralResponse<String>> create(CreateProductRequest request);
    Mono<GeneralResponse<String>> update(UpdateProductRequest request, UUID productId);
    Mono<GeneralResponse<String>> delete(UUID productId);
}
