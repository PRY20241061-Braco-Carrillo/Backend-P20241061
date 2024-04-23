package com.p20241061.management.api.model.response.relations;

import com.p20241061.management.core.entities.Campus;
import com.p20241061.management.core.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampusCategoryResponse {
    private UUID campusCategoryId;
    private Category category;
    private Campus campus;
}
