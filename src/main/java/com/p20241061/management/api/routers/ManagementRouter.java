package com.p20241061.management.api.routers;

import com.p20241061.management.api.handlers.*;
import com.p20241061.management.core.entities.Product;
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
    private static final String PATH_CATEGORY = "/api/category";
    private static final String PATH_COMPLEMENT = "/api/complement";
    private static final String PATH_COOKING_TYPE = "/api/cooking-type";
    private static final String PATH_MENU = "/api/menu";
    private static final String PATH_NUTRITIONAL_INFORMATION = "/api/nutritional-information";
    private static final String PATH_PRODUCT = "/api/product";
    private static final String PATH_PRODUCT_VARIANT = "/api/product-variant";
    private static final String PATH_PROMOTION = "/api/promotion";
    private static final String PATH_RESTAURANT = "/api/restaurant";
    private static final String PATH_SIZE = "/api/size";

    private static final String PATH_CAMPUS_CATEGORY = "/api/campus-category";

    @Bean
    RouterFunction<ServerResponse> campusRtr(CampusHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_CAMPUS + "/{campusId}", handler::getById)
                .GET(PATH_CAMPUS + "/restaurant/{restaurantId}", handler::getByRestaurantId)
                .POST(PATH_CAMPUS, handler::create)
                .PUT(PATH_CAMPUS + "/{campusId}", handler::update)
                .DELETE(PATH_CAMPUS + "/{campusId}", handler::delete)
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

    @Bean
    RouterFunction<ServerResponse> cookingTypeRtr(CookingTypeHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_COOKING_TYPE, handler::create)
                .PUT(PATH_COOKING_TYPE + "/{cookingTypeId}", handler::update)
                .DELETE(PATH_COOKING_TYPE + "/{cookingTypeId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> menuRtr(MenuHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_MENU, handler::create)
                .PUT(PATH_MENU + "/{menuId}", handler::update)
                .DELETE(PATH_MENU + "/{menuId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> nutritionalInformationRtr(NutritionalInformationHandler handler) {
        return RouterFunctions.route()
                .PUT(PATH_NUTRITIONAL_INFORMATION + "/{nutritionalInformationId}", handler::update)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> productRtr(ProductHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_PRODUCT, handler::create)
                .PUT(PATH_PRODUCT + "/{productId}", handler::update)
                .DELETE(PATH_PRODUCT + "/{productId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> productVariantRtr(ProductVariantHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_PRODUCT_VARIANT, handler::create)
                .PUT(PATH_PRODUCT_VARIANT + "/{productVariantId}", handler::update)
                .DELETE(PATH_PRODUCT_VARIANT + "/{productVariantId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> promotionRtr(PromotionHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_PROMOTION, handler::create)
                .PUT(PATH_PROMOTION + "/{promotionId}", handler::update)
                .DELETE(PATH_PROMOTION + "/{promotionId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> restaurantRtr(RestaurantHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_RESTAURANT, handler::getAll)
                .POST(PATH_RESTAURANT, handler::create)
                .PUT(PATH_RESTAURANT + "/{restaurantId}", handler::update)
                .DELETE(PATH_RESTAURANT + "/{restaurantId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> sizeRtr(SizeHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_SIZE, handler::create)
                .PUT(PATH_SIZE + "/{sizeId}", handler::update)
                .DELETE(PATH_SIZE + "/{sizeId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> campusCategoryRtr(CampusCategoryHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_CAMPUS_CATEGORY + "/campus/{campusId}", handler::getCategoriesByCampusId)
                .POST(PATH_CAMPUS_CATEGORY, handler::create)
                .DELETE(PATH_CAMPUS_CATEGORY + "/{campusCategoryId}", handler::delete)
                .build();
    }


}
