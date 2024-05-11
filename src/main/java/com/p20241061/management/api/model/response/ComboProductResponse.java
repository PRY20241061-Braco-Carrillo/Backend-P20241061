package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ComboProductResponse {
    private UUID productId;
    private String name;
    private String description;
    private String urlImage;
    private Integer productAmount;
}
