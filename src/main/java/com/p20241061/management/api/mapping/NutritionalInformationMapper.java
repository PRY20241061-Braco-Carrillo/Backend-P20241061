package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateNutritionalInformationRequest;
import com.p20241061.management.api.model.response.NutritionalInformationResponse;
import com.p20241061.management.core.entities.NutritionalInformation;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NutritionalInformationMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public NutritionalInformationResponse modelToResponse(NutritionalInformation model) {
        return mapper.map(model, NutritionalInformationResponse.class);
    }

    public NutritionalInformation createRequestToModel(CreateNutritionalInformationRequest request) {
        return mapper.map(request, NutritionalInformation.class);
    }

    public List<NutritionalInformationResponse> modelToListResponse(List<NutritionalInformation> models) {
        return mapper.mapList(models, NutritionalInformationResponse.class);
    }
}
