package com.p20241061.management.api.model.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCategoriesByCampusResponse {
    private UUID campusCategoryId;
    private String name;
    private Boolean isPromotion;
    private Boolean isCombo;
    private Boolean isMenu;
    private String urlImage;
}
