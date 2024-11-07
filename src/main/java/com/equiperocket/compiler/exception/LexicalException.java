package com.equiperocket.compiler.exception;

public class LexicalException extends CompilationException {

    public LexicalException(String msg, int line, int position) {
        super(msg, line, position);
    }
}
