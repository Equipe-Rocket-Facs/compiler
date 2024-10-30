package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.validation.TypeValidator;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class ExpressionProcessor {

    private final Map<String, String> variables;

    public ExpressionProcessor(Map<String, String> variables) {
        this.variables = variables;
    }

    public String processExpression(MyLanguageParser.ExprContext ctx) {
        if (ctx.expr().size() == 1) {
            return processParentheses(ctx);
        } else if (ctx.expr().size() == 2) {
            return processBinaryExpression(ctx);
        } else if (ctx.NUM_INT() != null) {
            return processInteger(ctx);
        } else if (ctx.NUM_DEC() != null) {
            return processDecimal(ctx);
        } else if (ctx.ID() != null) {
            return processVariable(ctx);
        }
        return "";
    }

    private String processParentheses(MyLanguageParser.ExprContext ctx) {
        String expr = processExpression(ctx.expr(0));

        return "(" + expr + ")";
    }

    private String processInteger(MyLanguageParser.ExprContext ctx) {
        return ctx.NUM_INT().getText();
    }

    private String processDecimal(MyLanguageParser.ExprContext ctx) {
        return ctx.NUM_DEC().getText();
    }

    private String processVariable(MyLanguageParser.ExprContext ctx) {
        String varName = ctx.ID().getText();

        VariableValidator.checkDeclared(varName, variables, ctx);

        return varName;
    }

    private String processBinaryExpression(MyLanguageParser.ExprContext ctx) {
        String left = processExpression((MyLanguageParser.ExprContext) ctx.getChild(0));
        String operator = ctx.mathOp().getText();
        String right = processExpression((MyLanguageParser.ExprContext) ctx.getChild(2));

        return left + " " + operator + " " + right;
    }

    // TODO: validação está causando erro no Case7.txt
    public String getExpressionType(MyLanguageParser.ExprContext ctx) {
        if (ctx.expr().size() == 2) {
            return getBinaryExpressionType(ctx);
        } else if (ctx.expr().size() == 1) {
            getExpressionType(ctx.expr(0));
        } else if (ctx.NUM_INT() != null) {
            return "int";
        } else if (ctx.NUM_DEC() != null) {
            return "double";
        } else if (ctx.ID() != null) {
            return getVariableType(ctx);
        }
        return "";
    }

    private String getBinaryExpressionType(MyLanguageParser.ExprContext ctx) {
        String leftType = getExpressionType((MyLanguageParser.ExprContext) ctx.getChild(0));
        String rightType = getExpressionType((MyLanguageParser.ExprContext) ctx.getChild(2));

        // Validação necessária em caso da presença de variáveis não numéricas na expressão
        TypeValidator.validateNumeric(leftType, ctx);
        TypeValidator.validateNumeric(rightType, ctx);

        return getResultingType(leftType, rightType);
    }

    private String getResultingType(String type1, String type2) {
        if (type1.equals("double") || type2.equals("double")) {
            return "double";
        }
        return "int";
    }

    private String getVariableType(MyLanguageParser.ExprContext ctx) {
        String type = variables.get(ctx.ID().getText());

        TypeValidator.validateNumeric(type, ctx);

        return type;
    }
}
