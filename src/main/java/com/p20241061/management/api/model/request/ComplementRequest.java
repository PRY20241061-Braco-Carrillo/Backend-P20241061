package com.p20241061.management.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplementRequest {
    private String name;
    private Double price;
    private Boolean isSauce;
}