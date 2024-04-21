package com.p20241061.management.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("complement_promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplementPromotion {
    @Id
    private UUID complementId;
    @Id
    private UUID promotionId;
}
