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
    private String name;
    private Double discount;
    private String discountType;
    private String detail;
    private String urlImage;
    private Integer freeSauce;
    private Boolean isAvailable;
    private Boolean hasVariant;
    private Integer freeComplements;
    private UUID comboId;
}
