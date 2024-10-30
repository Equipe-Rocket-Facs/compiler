package com.equiperocket.compiler.exception;

public class DuplicateVariableException extends CompilationException {

    public DuplicateVariableException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
