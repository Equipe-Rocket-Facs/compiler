package com.equiperocket.compiler.v1.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.v1.util.CodeBuilder;
import com.equiperocket.compiler.v1.validation.TypeValidator;
import com.equiperocket.compiler.v1.validation.VariableValidator;

import java.util.Map;

public class AttributionProcessor {

    private final Map<String, String> variables;
    private final Map<String, Boolean> variablesInitialized;
    private final CodeBuilder codeBuilder;
    private final BoolExpressionProcessor boolExpressionProcessor;
    private final ExpressionProcessor expressionProcessor;

    public AttributionProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.variablesInitialized = variablesInitialized;
        this.codeBuilder = codeBuilder;
        this.boolExpressionProcessor = new BoolExpressionProcessor(variables, variablesInitialized);
        this.expressionProcessor = new ExpressionProcessor(variables, variablesInitialized);
    }

    public void processAttribution(MyLanguageParser.AttributionContext ctx, boolean isInsideStructure) {
        String varName = ctx.ID().getText();

        VariableValidator.checkDeclared(varName, variables, ctx);

        String type = variables.get(varName);
        validateAttribution(type, ctx);

        String value = processAttributionValue(ctx);
        codeBuilder.append(varName).append(" = ").append(value);

        // Tem que vir depois do processamento, pois na atribuição de var 1 a uma var 2, a
        // var 1 precisa ser inicializada, e se viesse antes poderia abrir margem para erro
        variablesInitialized.put(varName, true);

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
