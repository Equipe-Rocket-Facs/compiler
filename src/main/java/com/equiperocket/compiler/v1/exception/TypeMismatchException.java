package com.equiperocket.compiler.v1.exception;

public class TypeMismatchException extends CompilationException {

    public TypeMismatchException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
