package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateProductRequest;
import com.p20241061.management.api.model.response.ProductResponse;
import com.p20241061.management.core.entities.Product;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public ProductResponse modelToResponse(Product model) {
        return mapper.map(model, ProductResponse.class);
    }

    public Product createRequestToModel(CreateProductRequest request, UUID nutritionalInformationId) {
        Product product = mapper.map(request, Product.class);
        product.setIsAvailable(true);
        product.setNutritionalInformationId(nutritionalInformationId);

        return product;
    }

    public List<ProductResponse> modelToListResponse(List<Product> models) {
        return mapper.mapList(models, ProductResponse.class);
    }
}
