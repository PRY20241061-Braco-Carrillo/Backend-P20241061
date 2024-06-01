package com.p20241061.management.api.model.request.complement.create;

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
public class CreateComplementRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Is sauce is required")
    private Boolean isSauce;
}
