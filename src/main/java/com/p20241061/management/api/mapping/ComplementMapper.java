package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateComplementRequest;
import com.p20241061.management.api.model.response.ComplementResponse;
import com.p20241061.management.core.entities.Complement;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ComplementMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public ComplementResponse modelToResponse(Complement model) {
        return mapper.map(model, ComplementResponse.class);
    }

    public Complement createRequestToModel(CreateComplementRequest request) {
        Complement complement = mapper.map(request, Complement.class);
        complement.setIsAvailable(true);

        return complement;
    }

    public List<ComplementResponse> modelToListResponse(List<Complement> models) {
        return mapper.mapList(models, ComplementResponse.class);
    }

}
