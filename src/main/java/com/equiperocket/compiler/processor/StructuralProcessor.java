package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;

import java.util.Map;

public class StructuralProcessor {

    private final CodeBuilder codeBuilder;
    private final BoolExpressionProcessor boolExpressionProcessor;
    private final CommandProcessor commandProcessor;
    private final AttributionProcessor attributionProcessor;

    public StructuralProcessor(Map<String, String> variables, CodeBuilder codeBuilder, CommandProcessor commandProcessor) {
        this.codeBuilder = codeBuilder;
        this.boolExpressionProcessor = new BoolExpressionProcessor(variables);
        this.commandProcessor = commandProcessor;
        this.attributionProcessor = new AttributionProcessor(variables, codeBuilder);
    }

    public void processIfStatement(MyLanguageParser.IfStmtContext ctx) {
        codeBuilder.append("if (");

        processCondition(ctx.condition(0));

        codeBuilder.appendLine(") {");
        codeBuilder.increaseIndentation();

        processBlock(ctx.block(0));

        codeBuilder.decreaseIndentation();
        codeBuilder.appendLine("}");

        // Else - if
        for (int i = 1; i < ctx.condition().size(); i++) {
            codeBuilder.append("else if (");

            processCondition(ctx.condition(i));

            codeBuilder.appendLine(") {");
            codeBuilder.increaseIndentation();

            processBlock(ctx.block(i));

            codeBuilder.decreaseIndentation();
            codeBuilder.appendLine("}");
        }

        // Else
        if (ctx.block().size() > ctx.condition().size()) {
            codeBuilder.appendLine("else {");
            codeBuilder.increaseIndentation();

            processBlock(ctx.block(ctx.block().size() - 1));

            codeBuilder.decreaseIndentation();
            codeBuilder.appendLine("}");
        }
    }

    public void processWhileStatement(MyLanguageParser.WhileStmtContext ctx) {
        codeBuilder.append("while (");

        processCondition(ctx.condition());

        codeBuilder.appendLine(") {");
        codeBuilder.increaseIndentation();

        processBlock(ctx.block());

        codeBuilder.decreaseIndentation();
        codeBuilder.appendLine("}");
    }

    public void processForStatement(MyLanguageParser.ForStmtContext ctx) {
        codeBuilder.append("for (");

        // Inicialização
        if (!ctx.attribution().isEmpty()) {
            processAttribution(ctx.attribution(0));
        }
        codeBuilder.append("; ");

        // Condição
        if (ctx.condition() != null) {
            processCondition(ctx.condition());
        }
        codeBuilder.append("; ");

        // Incremento (Opcional)
        if (ctx.attribution().size() > 1) {
            processAttribution(ctx.attribution(1));
        }

        codeBuilder.appendLine(") {");
        codeBuilder.increaseIndentation();

        processBlock(ctx.block());

        codeBuilder.decreaseIndentation();
        codeBuilder.appendLine("}");
    }

    private void processCondition(MyLanguageParser.ConditionContext ctx) {
        codeBuilder.append(boolExpressionProcessor.processBoolExpression(ctx.boolExpr()));
    }

    private void processBlock(MyLanguageParser.BlockContext ctx) {
        for (MyLanguageParser.CommandContext cmd : ctx.commands().command()) {
            commandProcessor.processCommand(cmd, true);
        }
    }

    private void processAttribution(MyLanguageParser.AttributionContext ctx) {
        attributionProcessor.processAttribution(ctx, true);
    }
}
