package com.p20241061.management.api.model.request.category.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCampusCategoryRequest {
    private UUID categoryId;
    private UUID campusId;
}
