package com.p20241061.management.api.mapping;

import com.p20241061.management.api.model.request.create.CreateRestaurantRequest;
import com.p20241061.management.api.model.response.RestaurantResponse;
import com.p20241061.management.core.entities.Restaurant;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RestaurantMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public RestaurantResponse modelToResponse(Restaurant model) {
        return mapper.map(model, RestaurantResponse.class);
    }

    public Restaurant createRequestToModel(CreateRestaurantRequest request) {
        Restaurant restaurant = mapper.map(request, Restaurant.class);
        restaurant.setIsAvailable(true);

        return restaurant;
    }

    public List<RestaurantResponse> modelToListResponse(List<Restaurant> models) {
        return mapper.mapList(models, RestaurantResponse.class);
    }
}
