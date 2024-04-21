package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampusResponse {
    private UUID campusId;
    private String name;
    private String address;
    private String phoneNumber;
    private Map<String, Map<String, Map<String, String>>> openHour;
    private Boolean toTakeHome;
    private Boolean toDelivery;
    private UUID restaurantId;
    private Boolean isAvailable;
}
