package com.p20241061.management.api.model.response.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetCategoriesByCampusResponse {
    private UUID campusCategoryId;
    private String name;
    private Boolean isPromotion;
    private Boolean isCombo;
    private Boolean isMenu;
    private String urlImage;
}
