package com.p20241061.management.core.entities;

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
    private String cookingTime;
    private String description;
    private Boolean isBreakfast;
    private Boolean isLunch;
    private Boolean isDinner;
    private String urlImage;
    private Integer freeSauce;
    private UUID nutritionalInformationId;
    private UUID campusCategoryId ;
    private Boolean isAvailable;
}
