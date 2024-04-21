package com.p20241061.management.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MenuRequest {
    private String name;
    private Double price;
    private String cookingTime;
    private String urlImage;
}
