package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateCategoryRequest;
import com.p20241061.management.api.model.response.CategoryResponse;
import com.p20241061.management.core.entities.Category;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public CategoryResponse modelToResponse(Category model) {
        return mapper.map(model, CategoryResponse.class);
    }

    public Category createRequestToModel(CreateCategoryRequest request) {
        return mapper.map(request, Category.class);
    }

    public List<CategoryResponse> modelToListResponse(List<Category> models) {
        return mapper.mapList(models, CategoryResponse.class);
    }
}
