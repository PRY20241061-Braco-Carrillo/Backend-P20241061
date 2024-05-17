package com.p20241061.management.core.entities.category;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("campus_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusCategory {
    @Id
    private UUID campusCategoryId;
    private UUID categoryId;
    private UUID campusId;
}
