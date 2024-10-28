package com.equiperocket.compiler.exception;

public class UndeclaredVariableException extends CompilationException {
    public UndeclaredVariableException(String varName, int line, int position) {
        super("Variável não declarada: " + varName, line, position);
    }
}