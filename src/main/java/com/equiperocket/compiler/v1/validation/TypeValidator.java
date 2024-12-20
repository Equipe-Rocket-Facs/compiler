package com.equiperocket.compiler.v1.validation;

import com.equiperocket.compiler.v1.exception.NotANumberException;
import com.equiperocket.compiler.v1.exception.TypeMismatchException;
import org.antlr.v4.runtime.ParserRuleContext;

public class TypeValidator {

    public static void validateTypes(String actualType, String expectedType, ParserRuleContext ctx) {
        if (!expectedType.equals(actualType)) {
            throw new TypeMismatchException(
                    String.format("Tentativa de atribuir %s a uma variável do tipo %s", actualType, expectedType),
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }

    public static void validateNumeric(String type, ParserRuleContext ctx) {
        if (!type.equals("int") && !type.equals("double")) {
            throw new NotANumberException(
                    "Apenas números entre expressões matemáticas",
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }

    public static void validateBoolean(String type, ParserRuleContext ctx) {
        if (!type.equals("boolean")) {
            throw new TypeMismatchException(
                    "Expressões booleanas não aceitam números e Strings",
                    ctx.getStart().getLine(),
                    ctx.getStart().getCharPositionInLine()
            );
        }
    }
}
