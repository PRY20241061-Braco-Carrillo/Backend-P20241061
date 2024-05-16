package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("combo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Combo {
    @Id
    private UUID comboId;
    private String name;
    private Integer maxCookingTime;
    private Integer minCookingTime;
    private String unitOfTimeCookingTime;
    private Double amountPrice;
    private String currencyPrice;
    private String urlImage;
    private Integer freeSauce;
}
