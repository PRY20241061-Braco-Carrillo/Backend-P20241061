package com.p20241061.management.infrastructure.interfaces;

import com.p20241061.management.api.model.request.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.request.update.UpdateProductVariantRequest;
import com.p20241061.management.api.model.response.ProductVariantResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductVariantService {
    Mono<GeneralResponse<ProductVariantResponse>> create(CreateProductVariantRequest request);
    Mono<GeneralResponse<ProductVariantResponse>> update(UpdateProductVariantRequest request, UUID productVariantId);
    Mono<GeneralResponse<String>> delete(UUID productVariantId);
}
