package com.p20241061.management.api.routers;

import com.p20241061.management.api.handlers.CampusHandler;
import com.p20241061.management.api.handlers.CategoryHandler;
import com.p20241061.management.api.handlers.ComplementHandler;
import com.p20241061.management.api.handlers.RestaurantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class ManagementRouter {

    private static final String PATH_CAMPUS = "/api/campus";
    private static final String PATH_RESTAURANT = "/api/restaurant";
    private static final String PATH_CATEGORY = "/api/category";
    private static final String PATH_COMPLEMENT = "/api/complement";

    @Bean
    RouterFunction<ServerResponse> campusRtr(CampusHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_CAMPUS, handler::create)
                .PUT(PATH_CAMPUS + "/{campusId}", handler::update)
                .DELETE(PATH_CAMPUS + "/{campusId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> restaurantRtr(RestaurantHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_RESTAURANT, handler::create)
                .PUT(PATH_RESTAURANT + "/{restaurantId}", handler::update)
                .DELETE(PATH_RESTAURANT + "/{restaurantId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> categoryRtr(CategoryHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_CATEGORY, handler::create)
                .PUT(PATH_CATEGORY + "/{categoryId}", handler::update)
                .DELETE(PATH_CATEGORY + "/{categoryId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> complementRtr(ComplementHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_COMPLEMENT, handler::create)
                .PUT(PATH_COMPLEMENT + "/{complementId}", handler::update)
                .DELETE(PATH_COMPLEMENT + "/{complementId}", handler::delete)
                .build();
    }
}
