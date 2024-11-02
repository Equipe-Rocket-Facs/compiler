package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.validation.TypeValidator;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class BoolExpressionProcessor {

    private final Map<String, String> variables;
    private final Map<String, Boolean> variablesInitialized;
    private final ExpressionProcessor expressionProcessor;

    public BoolExpressionProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized) {
        this.variables = variables;
        this.variablesInitialized = variablesInitialized;
        this.expressionProcessor = new ExpressionProcessor(variables, variablesInitialized);
    }

    public String processBoolExpression(MyLanguageParser.BoolExprContext ctx) {
        if (ctx.boolExpr() != null) {
            String left = processBoolExpression(ctx.boolExpr());
            String right = processBoolTerm(ctx.boolTerm());

            return left + " || " + right;
        }
        return processBoolTerm(ctx.boolTerm());
    }

    private String processBoolTerm(MyLanguageParser.BoolTermContext ctx) {
        if (ctx.boolTerm() != null) {
            String left = processBoolTerm(ctx.boolTerm());
            String right = processBoolFactor(ctx.boolFactor());

            return left + " && " + right;
        }
        return processBoolFactor(ctx.boolFactor());
    }

    private String processBoolFactor(MyLanguageParser.BoolFactorContext ctx) {
        if (ctx.boolFactor() != null) {
            String left = processBoolFactor(ctx.boolFactor());
            String operator = ctx.getChild(1).getText();
            String right = processBoolExprBase(ctx.boolExprBase());

            return left + " " + operator + " " + right;
        }
        return processBoolExprBase(ctx.boolExprBase());
    }

    private String processBoolExprBase(MyLanguageParser.BoolExprBaseContext ctx) {
        if (ctx.getText().startsWith("NAO")) {
            return "!" + processBoolExpression(ctx.boolExpr());
        } else if (ctx.relExpr() != null) {
            return processRelationalExpression(ctx.relExpr());
        } else if (ctx.BOOL() != null) {
            return ctx.BOOL().getText().equals("VERDADEIRO") ? "true" : "false";
        } else if (ctx.ID() != null) {
            return processVariable(ctx);
        } else if (ctx.boolExpr() != null) {
            return "(" + processBoolExpression(ctx.boolExpr()) + ")";
        }
        return "";
    }

    private String processRelationalExpression(MyLanguageParser.RelExprContext ctx) {
        String left = expressionProcessor.processExpression(ctx.expr(0));
        String operator = ctx.relOp().getText();
        String right = expressionProcessor.processExpression(ctx.expr(1));

        return left + " " + operator + " " + right;
    }

    private String processVariable(MyLanguageParser.BoolExprBaseContext ctx) {
        String type = variables.get(ctx.ID().getText());
        String varName = ctx.ID().getText();

        TypeValidator.validateBoolean(type, ctx);

        VariableValidator.checkInitialized(varName, variablesInitialized, ctx);

        return varName;
    }
}
