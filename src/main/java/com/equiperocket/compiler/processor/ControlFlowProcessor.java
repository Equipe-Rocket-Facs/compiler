package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;

import java.util.Map;

public class ControlFlowProcessor {
    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;
    private final ExpressionProcessor expressionProcessor;

    public ControlFlowProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
        this.expressionProcessor = new ExpressionProcessor(variables, codeBuilder);
    }

    public void processIfStatement(MyLanguageParser.IfStmtContext ctx) {
        if (!ctx.condition().isEmpty()) {
            codeBuilder.append("if (");
            processCondition(ctx.condition(0));
            codeBuilder.appendLine(") {");
            codeBuilder.increaseIndentation();
            processBlock(ctx.block(0));
            codeBuilder.decreaseIndentation();
            codeBuilder.appendLine("}");

            for (int i = 1; i < ctx.condition().size(); i++) {
                codeBuilder.append("else if (");
                processCondition(ctx.condition(i));
                codeBuilder.appendLine(") {");
                codeBuilder.increaseIndentation();
                processBlock(ctx.block(i));
                codeBuilder.decreaseIndentation();
                codeBuilder.appendLine("}");
            }

            if (ctx.block().size() > ctx.condition().size()) {
                codeBuilder.appendLine("else {");
                codeBuilder.increaseIndentation();
                processBlock(ctx.block(ctx.block().size() - 1));
                codeBuilder.decreaseIndentation();
                codeBuilder.appendLine("}");
            }
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
            processAttribution(ctx.attribution(0), false);
        }
        codeBuilder.append("; ");

        // Condição
        if (ctx.condition() != null) {
            processCondition(ctx.condition());
        }
        codeBuilder.append("; ");

        // Incremento
        if (ctx.attribution().size() > 1) {
            processAttribution(ctx.attribution(1), false);
        }

        codeBuilder.appendLine(") {");
        codeBuilder.increaseIndentation();
        processBlock(ctx.block());
        codeBuilder.decreaseIndentation();
        codeBuilder.appendLine("}");
    }

    private void processCondition(MyLanguageParser.ConditionContext ctx) {
        if (ctx.boolExpr() != null) {
            processBooleanExpression(ctx.boolExpr());
        }
    }

    private void processBooleanExpression(MyLanguageParser.BoolExprContext ctx) {
        if (ctx.expr() != null && ctx.expr().size() == 2) {
            String left = expressionProcessor.processExpression(ctx.expr(0));
            String operator = ctx.relOp().getText();
            String right = expressionProcessor.processExpression(ctx.expr(1));
            codeBuilder.append(left + " " + operator + " " + right);
        } else if (ctx.boolExpr() != null && ctx.boolExpr().size() == 2) {
            processBooleanExpression(ctx.boolExpr(0));
            String operator = ctx.getChild(1).getText();
            codeBuilder.append(operator.equals("E") ? " && " : " || ");
            processBooleanExpression(ctx.boolExpr(1));
        } else if (ctx.BOOL() != null) {
            codeBuilder.append(ctx.BOOL().getText().equals("VERDADEIRO") ? "true" : "false");
        }
    }

    private void processBlock(MyLanguageParser.BlockContext ctx) {
        if (ctx != null && ctx.commands() != null) {
            for (MyLanguageParser.CommandContext cmd : ctx.commands().command()) {
                processCommand(cmd);
            }
        }
    }

    private void processCommand(MyLanguageParser.CommandContext cmd) {
        if (cmd.readInput() != null) {
            new IOProcessor(variables, codeBuilder).processInput(cmd.readInput());
        } else if (cmd.writeOutput() != null) {
            new IOProcessor(variables, codeBuilder).processOutput(cmd.writeOutput());
        } else if (cmd.attribution() != null) {
            processAttribution(cmd.attribution(), true);
        }
    }

    public void processAttribution(MyLanguageParser.AttributionContext ctx, boolean appendSemicolon) {
        String varName = ctx.ID().getText();
        codeBuilder.append(varName + " = ");

        if (ctx.expr() != null) {
            codeBuilder.append(expressionProcessor.processExpression(ctx.expr()));
        } else if (ctx.TEXT() != null) {
            codeBuilder.append(ctx.TEXT().getText());
        } else if (ctx.boolExpr() != null) {
            processBooleanExpression(ctx.boolExpr());
        }

        if (appendSemicolon) {
            codeBuilder.appendLine(";");
        }
    }
}