package com.equiperocket.compiler.v1.exception;

public class SyntaxException extends CompilationException {

    public SyntaxException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
