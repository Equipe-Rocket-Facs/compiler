package com.equiperocket.compiler.v2.exception;

public class SemanticException extends BaseException {

    public SemanticException(String message, int line, int column) {
        super(message, line, column);
    }

    public SemanticException(String message) {
        super(message);
    }

}
