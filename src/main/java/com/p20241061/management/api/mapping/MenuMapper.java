package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateMenuRequest;
import com.p20241061.management.api.model.response.MenuResponse;
import com.p20241061.management.core.entities.Menu;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public MenuResponse modelToResponse(Menu model) {
        return mapper.map(model, MenuResponse.class);
    }

    public Menu createRequestToModel(CreateMenuRequest request) {
        return mapper.map(request, Menu.class);

    }

    public List<MenuResponse> modelToListResponse(List<Menu> models) {
        return mapper.mapList(models, MenuResponse.class);
    }
}
