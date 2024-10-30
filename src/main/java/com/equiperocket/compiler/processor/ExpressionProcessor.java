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
        if (ctx.NUM_INT() != null) {
            return ctx.NUM_INT().getText();
        } else if (ctx.NUM_DEC() != null) {
            return ctx.NUM_DEC().getText();
        } else if (ctx.ID() != null) {
            return processVariable(ctx);
        } else if (ctx.getChildCount() == 3) {
            return processBinaryExpression(ctx);
        }
        return "";
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

    public String getExpressionType(MyLanguageParser.ExprContext ctx) {
        if (ctx.NUM_INT() != null) return "int";
        if (ctx.NUM_DEC() != null) return "double";
        if (ctx.ID() != null) return variables.get(ctx.ID().getText());

        if (ctx.getChildCount() == 3) {
            String leftType = getExpressionType((MyLanguageParser.ExprContext) ctx.getChild(0));
            String rightType = getExpressionType((MyLanguageParser.ExprContext) ctx.getChild(2));

            // Validação necessária em caso da presença de variáveis não numéricas na expressão
            TypeValidator.validateNumeric(leftType, ctx);
            TypeValidator.validateNumeric(rightType, ctx);

            return getResultingType(leftType, rightType);
        }
        return "";
    }

    private String getResultingType(String type1, String type2) {
        if (type1.equals("double") || type2.equals("double")) {
            return "double";
        }
        return "int";
    }
}
