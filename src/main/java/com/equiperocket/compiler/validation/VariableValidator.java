package com.equiperocket.compiler.validation;

import com.equiperocket.compiler.exception.DuplicateVariableException;
import com.equiperocket.compiler.exception.UndeclaredVariableException;
import org.antlr.v4.runtime.ParserRuleContext;
import java.util.Map;

public class VariableValidator {
    public static void checkDeclared(String varName, Map<String, String> variables, ParserRuleContext ctx) {
        if (!variables.containsKey(varName)) {
            throw new UndeclaredVariableException(
                    varName,
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }

    public static void checkNotDeclared(String varName, Map<String, String> variables, ParserRuleContext ctx) {
        if (variables.containsKey(varName)) {
            throw new DuplicateVariableException(
                    varName,
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }
}