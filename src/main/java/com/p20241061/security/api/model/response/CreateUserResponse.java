package com.p20241061.security.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {

    private UUID userId;
    private String names;
    private String lastNames;
    private String email;
    private String roles;
    private Integer cancelReservation;
    private Integer acceptReservation;
}
