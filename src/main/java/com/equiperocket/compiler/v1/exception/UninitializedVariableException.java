package com.equiperocket.compiler.v1.exception;

public class UninitializedVariableException extends CompilationException {

    public UninitializedVariableException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
