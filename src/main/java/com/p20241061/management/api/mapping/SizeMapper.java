package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateSizeRequest;
import com.p20241061.management.api.model.response.SizeResponse;
import com.p20241061.management.core.entities.Size;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SizeMapper {

    @Autowired
    EnhancedModelMapper mapper;

    public SizeResponse modelToResponse(Size model) {
        return mapper.map(model, SizeResponse.class);
    }

    public Size createRequestToModel(CreateSizeRequest request) {
        Size size = mapper.map(request, Size.class);
        size.setIsAvailable(true);

        return size;
    }

    public List<SizeResponse> modelToListResponse(List<Size> models) {
        return mapper.mapList(models, SizeResponse.class);
    }
}
