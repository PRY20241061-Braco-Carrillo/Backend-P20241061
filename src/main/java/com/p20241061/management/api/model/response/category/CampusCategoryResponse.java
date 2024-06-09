package com.p20241061.management.api.model.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.core.entities.category.Category;
import com.p20241061.management.core.entities.restaurant.Campus;
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
