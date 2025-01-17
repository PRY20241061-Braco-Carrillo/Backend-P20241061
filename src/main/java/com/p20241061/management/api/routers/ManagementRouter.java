package com.p20241061.management.api.routers;

import com.p20241061.management.api.handlers.*;
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
    private static final String PATH_MENU = "/api/menu";
    private static final String PATH_NUTRITIONAL_INFORMATION = "/api/nutritional-information";
    private static final String PATH_PRODUCT = "/api/product";
    private static final String PATH_PRODUCT_VARIANT = "/api/product-variant";
    private static final String PATH_PROMOTION = "/api/promotion";
    private static final String PATH_RESTAURANT = "/api/restaurant";
    private static final String PATH_CAMPUS_CATEGORY = "/api/campus-category";
    private static final String PATH_COMBO = "/api/combo";

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
                .GET(PATH_CATEGORY + "/campus/{campusId}", handler::getCategoriesByCampusId)
                .POST(PATH_CATEGORY, handler::create)
                .PUT(PATH_CATEGORY + "/{categoryId}", handler::update)
                .DELETE(PATH_CATEGORY + "/{categoryId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> complementRtr(ComplementHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_COMPLEMENT + "/campus/{campusId}", handler::getComplementsByCampusId)
                .POST(PATH_COMPLEMENT, handler::create)
                .PUT(PATH_COMPLEMENT + "/{complementId}", handler::update)
                .DELETE(PATH_COMPLEMENT + "/{complementId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> menuRtr(MenuHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_MENU + "/{campusId}", handler::getAllByCampus)
                .GET(PATH_MENU + "/detail/{menuId}", handler::getMenuDetailById)
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
                .GET(PATH_PRODUCT + "/campus-category/{campusCategoryId}", handler::getAllByCampusCategory)
                .POST(PATH_PRODUCT, handler::create)
                .PUT(PATH_PRODUCT + "/{productId}", handler::update)
                .DELETE(PATH_PRODUCT + "/{productId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> productVariantRtr(ProductVariantHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_PRODUCT_VARIANT + "/product/{productId}", handler::getAllByProductId)
                .POST(PATH_PRODUCT_VARIANT, handler::create)
                .PUT(PATH_PRODUCT_VARIANT + "/{productVariantId}", handler::update)
                .DELETE(PATH_PRODUCT_VARIANT + "/{productVariantId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> promotionRtr(PromotionHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_PROMOTION + "/campus/{campusId}", handler::getAllByCampus)
                .GET(PATH_PROMOTION + "/campus-category/{campusCategoryId}", handler::getAllByCampusCategoryId)
                .GET(PATH_PROMOTION + "/productVariant/{promotionId}", handler::getProductVariantPromotionById)
                .GET(PATH_PROMOTION + "/combo/campus/{campusId}", handler::getAllComboPromotion)
                .GET(PATH_PROMOTION + "/combo/{promotionId}", handler::getComboPromotionDetail)
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
    RouterFunction<ServerResponse> campusCategoryRtr(CampusCategoryHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_CAMPUS_CATEGORY, handler::create)
                .DELETE(PATH_CAMPUS_CATEGORY + "/{campusCategoryId}", handler::delete)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> comboRtr(ComboHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_COMBO + "/campus/{campusId}", handler::getAll)
                .GET(PATH_COMBO + "/{comboId}", handler::getComboDetailById)
                .build();
    }


}
