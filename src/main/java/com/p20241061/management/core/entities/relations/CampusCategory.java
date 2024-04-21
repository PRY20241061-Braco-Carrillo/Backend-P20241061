package com.p20241061.management.core.entities.relations;

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
    private UUID categoryId;
    @Id
    private UUID campusId;
}
