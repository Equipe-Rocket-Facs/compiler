package com.equiperocket.compiler.v1.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.v1.util.CodeBuilder;

import java.util.Map;

public class CommandProcessor {

    private final IOProcessor ioProcessor;
    private final AttributionProcessor attributionProcessor;
    private final StructuralProcessor structuralProcessor;

    public CommandProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized, CodeBuilder codeBuilder) {
        this.ioProcessor = new IOProcessor(variables, variablesInitialized, codeBuilder);
        this.attributionProcessor = new AttributionProcessor(variables, variablesInitialized, codeBuilder);
        this.structuralProcessor = new StructuralProcessor(variables, variablesInitialized, codeBuilder, this);
    }

    public void processCommands(MyLanguageParser.CommandsContext ctx) {
        for (MyLanguageParser.CommandContext cmd : ctx.command()) {
            processCommand(cmd, false);
        }
    }

    public void processCommand(MyLanguageParser.CommandContext ctx, boolean isStructureCalling) {
        if (isDuplicationPossible(ctx, isStructureCalling)) {
            return;
        }

        if (ctx.readInput() != null) {
            ioProcessor.processInput(ctx.readInput());
        } else if (ctx.writeOutput() != null) {
            ioProcessor.processOutput(ctx.writeOutput());
        } else if (ctx.attribution() != null) {
            attributionProcessor.processAttribution(ctx.attribution(), false);
        } else if (ctx.ifStmt() != null) {
            structuralProcessor.processIfStatement(ctx.ifStmt());
        } else if (ctx.whileStmt() != null) {
            structuralProcessor.processWhileStatement(ctx.whileStmt());
        } else if (ctx.forStmt() != null) {
            structuralProcessor.processForStatement(ctx.forStmt());
        }
    }

    private boolean isDuplicationPossible(MyLanguageParser.CommandContext ctx, boolean isStructureCalling) {
        return ctx.getParent().getParent() instanceof MyLanguageParser.BlockContext && !isStructureCalling;
    }
}
