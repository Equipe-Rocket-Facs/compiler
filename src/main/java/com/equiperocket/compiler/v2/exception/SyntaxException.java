package com.equiperocket.compiler.v2.exception;

public class SyntaxException extends BaseException {

    public SyntaxException(String message, int line, int column) {
        super(message, line, column);
    }

    public SyntaxException(String message) {
        super(message);
    }
}
