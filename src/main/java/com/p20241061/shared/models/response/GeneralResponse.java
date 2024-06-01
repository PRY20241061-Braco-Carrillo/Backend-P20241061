package com.p20241061.shared.models.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GeneralResponse<T> {

    private String code;
    private T data;
}
