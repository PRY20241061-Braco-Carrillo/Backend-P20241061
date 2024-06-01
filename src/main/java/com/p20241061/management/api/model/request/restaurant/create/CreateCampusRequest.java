package com.p20241061.management.api.model.request.restaurant.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCampusRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotNull(message = "Open hour is required")
    private Map<String, Map<String, Map<String, String>>> openHour;
    @NotNull(message = "To take home is required")
    private Boolean toTakeHome;
    @NotNull(message = "To delivery is required")
    private Boolean toDelivery;
    @NotNull(message = "Restaurant id is required")
    private UUID restaurantId;
}
