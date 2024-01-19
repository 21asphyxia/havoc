package com.asphyxia.havoc.handler.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessage {
    public Integer status;

    public String message;

    public Object data;

    public ResponseMessage() {
    }

    public ResponseMessage(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseEntity<ResponseMessage> ok(String message, Object data) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    public static ResponseEntity<ResponseMessage> created(String message, Object data) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.CREATED.value(), message, data), HttpStatus.CREATED);
    }

    public static ResponseEntity<ResponseMessage> badRequest(String message) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseMessage> notFound(String message) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND.value(), message), HttpStatus.NOT_FOUND);
    }
}
