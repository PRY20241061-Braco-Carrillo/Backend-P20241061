package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateCampusRequest;
import com.p20241061.management.api.model.response.CampusResponse;
import com.p20241061.management.core.entities.Campus;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampusMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public CampusResponse modelToResponse(Campus model) {
        return mapper.map(model, CampusResponse.class);
    }

    public Campus createRequestToModel(CreateCampusRequest request) {
        Campus campus = mapper.map(request, Campus.class);
        campus.setIsAvailable(true);

        return campus;
    }

    public List<CampusResponse> modelToListResponse(List<Campus> models) {
        return mapper.mapList(models, CampusResponse.class);
    }
}
