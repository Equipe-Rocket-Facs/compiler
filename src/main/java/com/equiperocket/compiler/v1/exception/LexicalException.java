package com.equiperocket.compiler.v1.exception;

public class LexicalException extends CompilationException {

    public LexicalException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
