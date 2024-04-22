package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplementResponse {
    private UUID complementId;
    private String name;
    private Double price;
    private Boolean isSauce;
    private Boolean isAvailable;
}
