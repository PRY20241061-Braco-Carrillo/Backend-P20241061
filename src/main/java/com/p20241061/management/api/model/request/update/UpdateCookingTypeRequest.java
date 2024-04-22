package com.p20241061.management.api.model.request.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCookingTypeRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Availability is required")
    private Boolean isAvailable;

}
