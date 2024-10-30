package com.equiperocket.compiler.exception;

public class UninitializedVariableException extends CompilationException {

    public UninitializedVariableException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
