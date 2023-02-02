package com.financeiro.receita.exceptions.handler;

import com.financeiro.receita.exceptions.ErrorMessage;
import com.financeiro.receita.exceptions.ReceitaDuplicadaException;
import com.financeiro.receita.exceptions.ReceitaNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ReceitaExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Houve um erro inesperado ao processar a requisição!";

    @ExceptionHandler(ReceitaDuplicadaException.class)
    public final ResponseEntity<?> handlerReceitaDuplicadaException(ReceitaDuplicadaException ex) {
        logger.error("ReceitaDuplicadaException", ex);

        var exceptionResponse = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @ExceptionHandler(ReceitaNotFoundException.class)
    public final ResponseEntity<?> handlerReceitaNotFoundException(ReceitaNotFoundException ex) {
        logger.error("ReceitaNotFoundException", ex);

        var exceptionResponse = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handlerException(Exception ex) {
        logger.error(GENERIC_ERROR_MESSAGE);

        var exceptionResponse = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

}
