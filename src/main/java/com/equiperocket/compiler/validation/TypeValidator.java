package com.equiperocket.compiler.validation;

import com.equiperocket.compiler.exception.TypeMismatchException;
import org.antlr.v4.runtime.ParserRuleContext;

public class TypeValidator {
    public static void validateTypes(String expectedType, String actualType, ParserRuleContext ctx) {
        if (!expectedType.equals(actualType)) {
            throw new TypeMismatchException(
                    String.format("Tipo esperado: %s, Tipo encontrado: %s", expectedType, actualType),
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }
}