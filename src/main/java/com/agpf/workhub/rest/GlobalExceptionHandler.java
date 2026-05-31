package com.agpf.workhub.rest;

import com.agpf.workhub.dtos.http.ResponseAPI;
import com.agpf.workhub.exceptions.BusinessException;
import com.agpf.workhub.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseAPI handleException() {
        return new ResponseAPI(HttpStatus.BAD_REQUEST.value(), "Ocorreu um erro inesperado, tente novamente mais tarde.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseAPI handleNotFoundException(NotFoundException exception) {
        return new ResponseAPI(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseAPI handleBusinessException(BusinessException exception) {
        return new ResponseAPI(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseAPI handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ResponseAPI(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseAPI> handleResponseStatusException(ResponseStatusException exception) {
        var response = new ResponseAPI(exception.getStatusCode().value(), exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

}
