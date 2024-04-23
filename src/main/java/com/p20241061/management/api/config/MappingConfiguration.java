package com.p20241061.management.api.config;

import com.p20241061.management.api.mapping.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("managementMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public CampusMapper campusMapper() {
        return new CampusMapper();
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public ComplementMapper complementMapper() {
        return new ComplementMapper();
    }

    @Bean
    public CookingTypeMapper cookingTypeMapper() {
        return new CookingTypeMapper();
    }

    @Bean
    public MenuMapper menuMapper() {
        return new MenuMapper();
    }

    @Bean
    public NutritionalInformationMapper nutritionalInformationMapper() {
        return new NutritionalInformationMapper();
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    public ProductVariantMapper productVariantMapper() {
        return new ProductVariantMapper();
    }

    @Bean
    public PromotionMapper promotionMapper() {
        return new PromotionMapper();
    }

    @Bean
    public RestaurantMapper restaurantMapper() {
        return new RestaurantMapper();
    }

    @Bean
    public SizeMapper sizeMapper() {
        return new SizeMapper();
    }
}
