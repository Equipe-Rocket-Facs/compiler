package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;

import java.util.Map;

public class BoolExpressionProcessor {

    private final ExpressionProcessor expressionProcessor;

    public BoolExpressionProcessor(Map<String, String> variables) {
        this.expressionProcessor = new ExpressionProcessor(variables);
    }

    // TODO: add suporte a "NAO" e (), ambos contém apenas UM boolExpr
    public String processBoolExpression(MyLanguageParser.BoolExprContext ctx) {
        if (ctx.boolExpr().size() == 2) {
            return processLogicOperators(ctx);
        } else if (ctx.expr() != null) {
            return processExpr(ctx);
        } else if (ctx.BOOL() != null) {
            return processBool(ctx);
        }
        return "";
    }

    private String processLogicOperators(MyLanguageParser.BoolExprContext ctx) {
        String left = processBoolExpression(ctx.boolExpr(0));
        String operator = ctx.getChild(1).getText().equals("E") ? "&&" : "||";
        String right = processBoolExpression(ctx.boolExpr(1));

        return left + " " + operator + " " + right;
    }

    private String processExpr(MyLanguageParser.BoolExprContext ctx) {
        String left = expressionProcessor.processExpression(ctx.expr(0));
        String operator = ctx.relOp().getText();
        String right = expressionProcessor.processExpression(ctx.expr(1));

        return left + " " + operator + " " + right;
    }

    private String processBool(MyLanguageParser.BoolExprContext ctx) {
        return ctx.BOOL().getText().equals("VERDADEIRO") ? "true" : "false";
    }
}