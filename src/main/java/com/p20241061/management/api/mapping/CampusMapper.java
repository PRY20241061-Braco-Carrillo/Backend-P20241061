package com.p20241061.management.api.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.p20241061.management.api.model.request.restaurant.create.CreateCampusRequest;
import com.p20241061.management.api.model.response.restaurant.CampusResponse;
import com.p20241061.management.core.entities.restaurant.Campus;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampusMapper {

    @Autowired
    EnhancedModelMapper mapper;
    @Autowired
    ObjectMapper objectMapper;

    public CampusResponse modelToResponse(Campus model) throws JsonProcessingException {
        CampusResponse response = mapper.map(model, CampusResponse.class);
        response.setOpenHour(objectMapper.readValue(model.getOpenHour(), new TypeReference<>() {
        }));

        return response;
    }

    public Campus createRequestToModel(CreateCampusRequest request) throws JsonProcessingException {
        Campus campus = mapper.map(request, Campus.class);
        campus.setIsAvailable(true);
        campus.setOpenHour(objectMapper.writeValueAsString(request.getOpenHour()));

        return campus;
    }

    public List<CampusResponse> modelToListResponse(List<Campus> models) {
        return mapper.mapList(models, CampusResponse.class);
    }
}
