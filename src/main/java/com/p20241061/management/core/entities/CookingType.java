package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("cooking_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookingType {
    @Id
    private UUID cookingTypeId;
    private String name;
    private Boolean isAvailable;
}
