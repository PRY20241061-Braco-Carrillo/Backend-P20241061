package com.p20241061.management.api.model.response.relations;

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
    private String urlImage;
}
