package com.equiperocket.compiler.exception;

import com.equiperocket.compiler.exception.custom.CodeException;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExceptionHandler {

    protected void throwException(String msg, ParserRuleContext ctx) {
        String errorMsg = createMessage(msg, ctx);

        throw new CodeException(errorMsg);
    }

    private String createMessage(String msg, ParserRuleContext ctx) {
        int line = ctx.getStart().getLine();
        int charPosition = ctx.getStart().getCharPositionInLine();

        return "Linha " + line + ", Posição " + charPosition + " - " + msg;
    }
}
