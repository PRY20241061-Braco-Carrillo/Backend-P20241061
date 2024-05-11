package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreatePromotionRequest;
import com.p20241061.management.api.model.response.PromotionResponse;
import com.p20241061.management.core.entities.Promotion;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PromotionMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public PromotionResponse modelToResponse(Promotion model) {
        return mapper.map(model, PromotionResponse.class);
    }

    public Promotion createRequestToModel(CreatePromotionRequest request) {
        return mapper.map(request, Promotion.class);
    }

    public List<PromotionResponse> modelToListResponse(List<Promotion> models) {
        return mapper.mapList(models, PromotionResponse.class);
    }
}
