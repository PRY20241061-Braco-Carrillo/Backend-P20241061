package com.p20241061.shared.validation;


import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    @SneakyThrows
    public <T> T validate(T object) {

        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if (!errors.isEmpty()) {
            String message = errors.stream().map(err -> err.getMessage()).collect(Collectors.joining(", "));
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.name(), message);
        }

        return object;
    }
}
