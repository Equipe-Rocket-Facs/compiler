package com.equiperocket.compiler.v1.exception;

public class UndeclaredVariableException extends CompilationException {

    public UndeclaredVariableException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
