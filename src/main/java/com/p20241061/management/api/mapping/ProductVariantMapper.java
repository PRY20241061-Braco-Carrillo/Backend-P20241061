package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateProductVariantRequest;
import com.p20241061.management.api.model.response.ProductVariantResponse;
import com.p20241061.management.core.entities.ProductVariant;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductVariantMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public ProductVariantResponse modelToResponse(ProductVariant model) {
        return mapper.map(model, ProductVariantResponse.class);
    }

    public ProductVariant createRequestToModel(CreateProductVariantRequest request) {
        return mapper.map(request, ProductVariant.class);
    }

    public List<ProductVariantResponse> modelToListResponse(List<ProductVariant> models) {
        return mapper.mapList(models, ProductVariantResponse.class);
    }
}
