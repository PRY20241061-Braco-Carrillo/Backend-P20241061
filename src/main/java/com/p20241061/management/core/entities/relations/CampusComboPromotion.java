package com.p20241061.management.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("campus_combo_promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusComboPromotion {
    @Id
    private UUID campusComboPromotionId;
    private Boolean isAvailable;
    private UUID campusId;
    private UUID promotionId;
}
