package com.equiperocket.compiler.v1.exception;

public class NotANumberException extends CompilationException {

    public NotANumberException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
