package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    private UUID promotionId;
    private Double price;
    private String cookingTime;
    private Double discount;
    private String discountType;
    private String detail;
    private Integer freeSauce;
    private Integer freeComplements;
    private String urlImage;
}
