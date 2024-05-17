package com.p20241061.management.core.entities.menu;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    @Id
    private UUID menuId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String urlImage;
    private Boolean isAvailable;
    private UUID campusId;
}
