package com.equiperocket.compiler.v1.exception;

public class CompilationException extends RuntimeException {

    public CompilationException(String message, int line, int position) {
        super(String.format("Linha %d, posição %d - %s", line, position, message));
    }
}
