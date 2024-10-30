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
        if (ctx.boolExpr().size() == 2) {
            return processOperators(ctx);
        } else if (ctx.boolExpr().size() == 1 && ctx.getChildCount() == 2) {
            return processNotOperator(ctx);
        } else if (ctx.relExpr() != null) {
            return processRelationalExpression(ctx);
        } else if (ctx.boolExpr().size() == 1 && ctx.getChildCount() == 3) {
            return processParentheses(ctx);
        } else if (ctx.BOOL() != null) {
            return processBool(ctx);
        } else if (ctx.ID() != null) {
            return processVariable(ctx);
        }
        return "";
    }

    private String processOperators(MyLanguageParser.BoolExprContext ctx) {
        String left = processBoolExpression(ctx.boolExpr(0));
        String operator = processOperator(ctx);
        String right = processBoolExpression(ctx.boolExpr(1));

        return left + " " + operator + " " + right;
    }

    private String processOperator(MyLanguageParser.BoolExprContext ctx) {
        String operator = ctx.getChild(1).getText();

        if (operator.equals("OU")) {
            return "||";
        } else if (operator.equals("E")) {
            return "&&";
        }
        return operator;
    }

    private String processNotOperator(MyLanguageParser.BoolExprContext ctx) {
        return "!" + processParentheses(ctx);
    }

    private String processRelationalExpression(MyLanguageParser.BoolExprContext ctx) {
        String left = expressionProcessor.processExpression(ctx.relExpr().expr(0));
        String operator = ctx.relExpr().relOp().getText();
        String right = expressionProcessor.processExpression(ctx.relExpr().expr(1));

        return left + " " + operator + " " + right;
    }

    private String processParentheses(MyLanguageParser.BoolExprContext ctx) {
        String boolExpr = processBoolExpression(ctx.boolExpr(0));

        return "(" + boolExpr + ")";
    }

    private String processBool(MyLanguageParser.BoolExprContext ctx) {
        return ctx.BOOL().getText().equals("VERDADEIRO") ? "true" : "false";
    }

    private String processVariable(MyLanguageParser.BoolExprContext ctx) {
        String type = variables.get(ctx.ID().getText());
        String varName = ctx.ID().getText();

        TypeValidator.validateBoolean(type, ctx);

        VariableValidator.checkInitialized(varName, variablesInitialized, ctx);

        return varName;
    }
}
