package com.asphyxia.havoc.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomException extends RuntimeException {

    private String error;
    private HttpStatus status;

}
