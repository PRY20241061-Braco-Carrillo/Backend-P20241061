package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComboProductResponse {
    private String name;
    private String description;
    private String urlImage;
    private Integer productAmount;
}
