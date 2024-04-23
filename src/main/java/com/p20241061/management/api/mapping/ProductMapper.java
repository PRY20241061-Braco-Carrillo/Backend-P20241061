package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.response.ProductResponse;
import com.p20241061.management.core.entities.Product;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public ProductResponse modelToResponse(Product model) {
        return mapper.map(model, ProductResponse.class);
    }

    public Product createRequestToModel(CreatePromotionRequest request) {
        Product product = mapper.map(request, Product.class);
        product.setIsAvailable(true);

        return product;
    }

    public List<ProductResponse> modelToListResponse(List<Product> models) {
        return mapper.mapList(models, ProductResponse.class);
    }
}
