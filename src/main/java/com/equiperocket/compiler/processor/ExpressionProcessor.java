package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.validation.TypeValidator;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class ExpressionProcessor {
    private final Map<String, String> variables;
    private final Map<String, Boolean> variablesInitialized;

    public ExpressionProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized) {
        this.variables = variables;
        this.variablesInitialized = variablesInitialized;
    }

    public String processExpression(MyLanguageParser.ExprContext ctx) {
        if (ctx.term() != null && ctx.expr() == null) {
            return processTerm(ctx.term());
        }

        String left = processExpression(ctx.expr());
        String operator = ctx.getChild(1).getText();
        String right = processTerm(ctx.term());

        return left + " " + operator + " " + right;
    }

    private String processTerm(MyLanguageParser.TermContext ctx) {
        if (ctx.factor() != null && ctx.term() == null) {
            return processFactor(ctx.factor());
        }

        String left = processTerm(ctx.term());
        String operator = ctx.getChild(1).getText();
        String right = processFactor(ctx.factor());

        return left + " " + operator + " " + right;
    }

    private String processFactor(MyLanguageParser.FactorContext ctx) {
        if (ctx.NUM_INT() != null) {
            return ctx.NUM_INT().getText();
        } else if (ctx.NUM_DEC() != null) {
            return ctx.NUM_DEC().getText();
        } else if (ctx.ID() != null) {
            return processVariable(ctx);
        } else if (ctx.expr() != null) {
            return "(" + processExpression(ctx.expr()) + ")";
        }
        return "";
    }

    private String processVariable(MyLanguageParser.FactorContext ctx) {
        String varName = ctx.ID().getText();

        VariableValidator.checkDeclared(varName, variables, ctx);
        VariableValidator.checkInitialized(varName, variablesInitialized, ctx);

        return varName;
    }

    public String getExpressionType(MyLanguageParser.ExprContext ctx) {
        if (ctx.term() != null && ctx.expr() == null) {
            return getTermType(ctx.term());
        }

        String leftType = getExpressionType(ctx.expr());
        String rightType = getTermType(ctx.term());

        return getResultingType(leftType, rightType);
    }

    private String getTermType(MyLanguageParser.TermContext ctx) {
        if (ctx.factor() != null && ctx.term() == null) {
            return getFactorType(ctx.factor());
        }

        String leftType = getTermType(ctx.term());
        String rightType = getFactorType(ctx.factor());

        return getResultingType(leftType, rightType);
    }

    private String getFactorType(MyLanguageParser.FactorContext ctx) {
        if (ctx.NUM_INT() != null) {
            return "int";
        } else if (ctx.NUM_DEC() != null) {
            return "double";
        } else if (ctx.ID() != null) {
            return variables.get(ctx.ID().getText());
        } else if (ctx.expr() != null) {
            return getExpressionType(ctx.expr());
        }
        return "";
    }

    private String getResultingType(String type1, String type2) {
        TypeValidator.validateNumeric(type1, null);
        TypeValidator.validateNumeric(type2, null);

        if (type1.equals("double") || type2.equals("double")) {
            return "double";
        }
        return "int";
    }

}
