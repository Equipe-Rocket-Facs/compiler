package com.equiperocket.compiler.exception;

public class TypeMismatchException extends CompilationException {

    public TypeMismatchException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
