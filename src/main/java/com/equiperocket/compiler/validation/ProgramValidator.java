package com.equiperocket.compiler.validation;

import com.equiperocket.compiler.exception.SyntaxException;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgramValidator {

    private static final String BEGIN_PROGRAM = "programa";
    private static final String END_PROGRAM = "fimprog";

    public static void checkBegin(String beginProgramDeclaration, ParserRuleContext ctx) {
        if (beginProgramDeclaration == null || !beginProgramDeclaration.equals(BEGIN_PROGRAM)) {
            throw new SyntaxException(String.format("'%s' ausente no início do código", BEGIN_PROGRAM));
        }
    }

    public static void checkEnd(String endProgramDeclaration, ParserRuleContext ctx) {
        if (endProgramDeclaration == null || !endProgramDeclaration.equals(END_PROGRAM)) {
            throw new SyntaxException(String.format("'%s' ausente no final do código", END_PROGRAM));
        }
    }
}
