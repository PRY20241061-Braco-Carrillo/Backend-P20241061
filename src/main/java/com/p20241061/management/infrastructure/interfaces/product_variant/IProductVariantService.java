package com.p20241061.management.infrastructure.interfaces.product_variant;

import com.p20241061.management.api.model.request.product_variant.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.request.product_variant.update.UpdateProductVariantRequest;
import com.p20241061.management.api.model.response.product.GetProductDetailResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductVariantService {
    Mono<GeneralResponse<GetProductDetailResponse>> getProductDetailResponse(UUID productId);

    Mono<GeneralResponse<String>> create(CreateProductVariantRequest request);

    Mono<GeneralResponse<String>> update(UpdateProductVariantRequest request, UUID productVariantId);

    Mono<GeneralResponse<String>> delete(UUID productVariantId);
}
