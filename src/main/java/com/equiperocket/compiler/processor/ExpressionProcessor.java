package com.equiperocket.compiler.processor;


import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class ExpressionProcessor {
    private final Map<String, String> variables;

    public ExpressionProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
    }

    public String processExpression(MyLanguageParser.ExprContext ctx) {
        if (ctx == null) return "";

        if (ctx.NUM_INT() != null) return ctx.NUM_INT().getText();
        if (ctx.NUM_DEC() != null) return ctx.NUM_DEC().getText();
        if (ctx.ID() != null) {
            String varName = ctx.ID().getText();
            VariableValidator.checkDeclared(varName, variables, ctx);
            return varName;
        }

        if (ctx.getChildCount() == 3) {
            return processBinaryExpression(ctx);
        }

        return ctx.getText();
    }

    public String inferExpressionType(MyLanguageParser.ExprContext ctx) {
        if (ctx.NUM_INT() != null) return "int";
        if (ctx.NUM_DEC() != null) return "double";
        if (ctx.ID() != null) return variables.get(ctx.ID().getText());

        if (ctx.getChildCount() == 3) {
            String leftType = inferExpressionType((MyLanguageParser.ExprContext)ctx.getChild(0));
            String rightType = inferExpressionType((MyLanguageParser.ExprContext)ctx.getChild(2));
            return getResultingType(leftType, rightType);
        }

        return "int";
    }

    private String processBinaryExpression(MyLanguageParser.ExprContext ctx) {
        String left = processExpression((MyLanguageParser.ExprContext)ctx.getChild(0));
        String operator = ctx.getChild(1).getText();
        String right = processExpression((MyLanguageParser.ExprContext)ctx.getChild(2));
        return left + " " + operator + " " + right;
    }

    private String getResultingType(String type1, String type2) {
        if (type1.equals("double") || type2.equals("double")) return "double";
        return "int";
    }
}