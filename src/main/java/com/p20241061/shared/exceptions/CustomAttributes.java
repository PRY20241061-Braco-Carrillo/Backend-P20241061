package com.p20241061.shared.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAttributes extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorAttribute = new HashMap<>();
        Throwable throwable = super.getError(request);

        if (throwable instanceof CustomException customException) {
            errorAttribute.put("message", customException.getMessage());
            errorAttribute.put("code", customException.getCode());
            errorAttribute.put("httpStatus", customException.getHttpStatus().value());
        }

        return errorAttribute;
    }
}
