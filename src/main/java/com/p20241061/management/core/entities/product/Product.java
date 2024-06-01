package com.p20241061.management.core.entities.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private UUID productId;
    private String name;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String description;
    private Boolean isBreakfast;
    private Boolean isLunch;
    private Boolean isDinner;
    private String urlImage;
    private String urlGlb;
    private Integer freeSauce;
    private Boolean isAvailable;
    private Boolean hasVariant;
    private UUID nutritionalInformationId;
}
