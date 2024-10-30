package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.validation.TypeValidator;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class AttributionProcessor {

    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;
    private final BoolExpressionProcessor boolExpressionProcessor;
    private final ExpressionProcessor expressionProcessor;

    public AttributionProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
        this.boolExpressionProcessor = new BoolExpressionProcessor(variables);
        this.expressionProcessor = new ExpressionProcessor(variables);
    }

    public void processAttribution(MyLanguageParser.AttributionContext ctx, boolean isInsideStructure) {
        String varName = ctx.ID().getText();

        VariableValidator.checkDeclared(varName, variables, ctx);

        String type = variables.get(varName);
        validateAttribution(type, ctx);

        codeBuilder.append(varName).append(" = ");
        codeBuilder.append(processAttributionValue(ctx));

        if (!isInsideStructure) {
            codeBuilder.appendLine(";");
        }
    }

    private void validateAttribution(String type, MyLanguageParser.AttributionContext ctx) {
        if (ctx.expr() != null) {
            String actualType = expressionProcessor.getExpressionType(ctx.expr());

            TypeValidator.validateTypes(actualType, type, ctx);
        } else if (ctx.boolExpr() != null) {
            TypeValidator.validateTypes("boolean", type, ctx);
        } else if (ctx.TEXT() != null) {
            TypeValidator.validateTypes("String", type, ctx);
        }
    }

    public String processAttributionValue(MyLanguageParser.AttributionContext ctx) {
        if (ctx.expr() != null) {
            return processExpression(ctx);
        } else if (ctx.boolExpr() != null) {
            return processBoolExpression(ctx);
        } else if (ctx.TEXT() != null) {
            return processText(ctx);
        }
        return "";
    }

    private String processBoolExpression(MyLanguageParser.AttributionContext ctx) {
        return boolExpressionProcessor.processBoolExpression(ctx.boolExpr());
    }

    private String processExpression(MyLanguageParser.AttributionContext ctx) {
        return expressionProcessor.processExpression(ctx.expr());
    }

    private String processText(MyLanguageParser.AttributionContext ctx) {
        return ctx.TEXT().getText();
    }
}
