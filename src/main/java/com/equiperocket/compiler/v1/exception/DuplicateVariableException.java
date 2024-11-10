package com.equiperocket.compiler.v1.exception;

public class DuplicateVariableException extends CompilationException {

    public DuplicateVariableException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
