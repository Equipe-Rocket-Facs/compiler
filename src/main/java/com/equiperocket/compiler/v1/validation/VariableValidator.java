package com.equiperocket.compiler.v1.validation;

import com.equiperocket.compiler.v1.exception.DuplicateVariableException;
import com.equiperocket.compiler.v1.exception.UndeclaredVariableException;
import com.equiperocket.compiler.v1.exception.UninitializedVariableException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Map;

public class VariableValidator {

    public static void checkDeclared(String varName, Map<String, String> variables, ParserRuleContext ctx) {
        if (!variables.containsKey(varName)) {
            throw new UndeclaredVariableException(
                    String.format("Variável não declarada: %s", varName),
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }

    public static void checkNotDeclared(String varName, Map<String, String> variables, ParserRuleContext ctx) {
        if (variables.containsKey(varName)) {
            throw new DuplicateVariableException(
                    String.format("Variável já declarada: %s", varName),
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }

    public static void checkInitialized(String varName, Map<String, Boolean> variablesInitialized, ParserRuleContext ctx) {
        if (!variablesInitialized.get(varName)) {
            throw new UninitializedVariableException(
                    String.format("Variável não inicializada: %s", varName),
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }
}
