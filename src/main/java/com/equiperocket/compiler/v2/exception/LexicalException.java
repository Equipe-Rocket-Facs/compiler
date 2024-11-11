package com.equiperocket.compiler.v2.exception;

public class LexicalException extends BaseException {

    public LexicalException(String message, int line, int column) {
        super(message, line, column);
    }
}
