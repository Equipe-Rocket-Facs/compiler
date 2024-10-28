package com.equiperocket.compiler.exception;

public class TypeMismatchException extends CompilationException {
    public TypeMismatchException(String message, int line, int position) {
        super(message, line, position);
    }
}