package com.equiperocket.compiler.exception;

public class CompilationException extends RuntimeException {
    public CompilationException(String message, int line, int position) {
        super(String.format("Erro na linha %d, posição %d: %s", line, position, message));
    }
}