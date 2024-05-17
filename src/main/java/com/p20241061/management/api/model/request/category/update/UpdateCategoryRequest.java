package com.p20241061.management.api.model.request.category.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String urlImage;
    @NotNull(message = "Restaurant id is required")
    private UUID restaurantId;
}
