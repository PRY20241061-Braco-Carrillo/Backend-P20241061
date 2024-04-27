package com.p20241061.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {

    private String code;

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

}
