package com.financeiro.receita.exceptions;

public class ReceitaNotFoundException extends RuntimeException {

    public ReceitaNotFoundException(String message) {
        super(message);
    }
}
