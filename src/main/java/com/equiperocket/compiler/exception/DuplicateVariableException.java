package com.equiperocket.compiler.exception;

public class DuplicateVariableException extends CompilationException {
    public DuplicateVariableException(String varName, int line, int position) {
        super("Variável já declarada: " + varName, line, position);
    }
}