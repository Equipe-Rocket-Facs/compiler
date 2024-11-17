package com.equiperocket.compiler.v2.exception;

public class BaseException extends RuntimeException {

    public BaseException(String message, int line, int column) {
        super(message + " at line " + line + ", column " + column);
    }

    public BaseException(String message) {
        super(message);
    }
}
