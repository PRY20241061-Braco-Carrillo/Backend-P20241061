package com.p20241061.management.api.model.response.relations;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetCategoriesByCampusResponse {
    private UUID campusCategoryId;
    private String name;
    private String urlImage;
}
