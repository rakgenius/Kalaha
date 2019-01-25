package com.rakgenius.kalaha.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
@AllArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;

    public static ExceptionResponse of(final String message,
                                       HttpStatus status){
        return new ExceptionResponse(message, status);
    }
}
