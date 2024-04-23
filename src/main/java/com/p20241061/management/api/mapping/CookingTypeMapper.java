package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateCookingTypeRequest;
import com.p20241061.management.api.model.response.CookingTypeResponse;
import com.p20241061.management.core.entities.CookingType;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CookingTypeMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public CookingTypeResponse modelToResponse(CookingType model) {
        return mapper.map(model, CookingTypeResponse.class);
    }

    public CookingType createRequestToModel(CreateCookingTypeRequest request) {
        CookingType cookingType = mapper.map(request, CookingType.class);
        cookingType.setIsAvailable(true);

        return cookingType;
    }

    public List<CookingTypeResponse> modelToListResponse(List<CookingType> models) {
        return mapper.mapList(models, CookingTypeResponse.class);
    }
}
