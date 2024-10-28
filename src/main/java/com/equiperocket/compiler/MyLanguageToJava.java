package com.equiperocket.compiler;

import com.equiperocket.compiler.processor.ControlFlowProcessor;
import com.equiperocket.compiler.processor.DeclarationProcessor;
import com.equiperocket.compiler.processor.IOProcessor;
import com.equiperocket.compiler.util.CodeBuilder;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class MyLanguageToJava implements MyLanguageListener {
    private final CodeBuilder codeBuilder;
    private final DeclarationProcessor declarationProcessor;
    private final IOProcessor ioProcessor;
    private final ControlFlowProcessor controlFlowProcessor;

    public MyLanguageToJava() {
        this.codeBuilder = new CodeBuilder();
        Map<String, String> variables = new HashMap<>();
        this.declarationProcessor = new DeclarationProcessor(variables, codeBuilder);
        this.ioProcessor = new IOProcessor(variables, codeBuilder);
        this.controlFlowProcessor = new ControlFlowProcessor(variables, codeBuilder);
    }

    @Override
    public void enterProg(MyLanguageParser.ProgContext ctx) {
        codeBuilder.appendClassHeader();
    }

    @Override
    public void exitProg(MyLanguageParser.ProgContext ctx) {
        codeBuilder.appendClassFooter();
    }

    @Override
    public void enterDecl(MyLanguageParser.DeclContext ctx) {
        declarationProcessor.processDeclaration(ctx);
    }

    @Override
    public void enterReadInput(MyLanguageParser.ReadInputContext ctx) {
        ioProcessor.processInput(ctx);
    }

    @Override
    public void enterWriteOutput(MyLanguageParser.WriteOutputContext ctx) {
        ioProcessor.processOutput(ctx);
    }

    @Override
    public void enterIfStmt(MyLanguageParser.IfStmtContext ctx) {
        controlFlowProcessor.processIfStatement(ctx);
    }

    @Override
    public void enterWhileStmt(MyLanguageParser.WhileStmtContext ctx) {
        controlFlowProcessor.processWhileStatement(ctx);
    }

    @Override
    public void enterForStmt(MyLanguageParser.ForStmtContext ctx) {
        controlFlowProcessor.processForStatement(ctx);
    }

    @Override public void enterDecls(MyLanguageParser.DeclsContext ctx) {}
    @Override public void exitDecls(MyLanguageParser.DeclsContext ctx) {}
    @Override public void exitDecl(MyLanguageParser.DeclContext ctx) {}
    @Override public void enterType(MyLanguageParser.TypeContext ctx) {}
    @Override public void exitType(MyLanguageParser.TypeContext ctx) {}
    @Override public void enterDeclItemList(MyLanguageParser.DeclItemListContext ctx) {}
    @Override public void exitDeclItemList(MyLanguageParser.DeclItemListContext ctx) {}
    @Override public void enterDeclItem(MyLanguageParser.DeclItemContext ctx) {}
    @Override public void exitDeclItem(MyLanguageParser.DeclItemContext ctx) {}
    @Override public void enterCommands(MyLanguageParser.CommandsContext ctx) {}
    @Override public void exitCommands(MyLanguageParser.CommandsContext ctx) {}
    @Override public void enterCommand(MyLanguageParser.CommandContext ctx) {}
    @Override public void exitCommand(MyLanguageParser.CommandContext ctx) {}
    @Override public void exitReadInput(MyLanguageParser.ReadInputContext ctx) {}
    @Override public void exitWriteOutput(MyLanguageParser.WriteOutputContext ctx) {}
    @Override public void enterAttribution(MyLanguageParser.AttributionContext ctx) {}
    @Override public void exitAttribution(MyLanguageParser.AttributionContext ctx) {}
    @Override public void exitIfStmt(MyLanguageParser.IfStmtContext ctx) {}
    @Override public void exitWhileStmt(MyLanguageParser.WhileStmtContext ctx) {}
    @Override public void exitForStmt(MyLanguageParser.ForStmtContext ctx) {}
    @Override public void enterBlock(MyLanguageParser.BlockContext ctx) {}
    @Override public void exitBlock(MyLanguageParser.BlockContext ctx) {}
    @Override public void enterCondition(MyLanguageParser.ConditionContext ctx) {}
    @Override public void exitCondition(MyLanguageParser.ConditionContext ctx) {}
    @Override public void enterBoolExpr(MyLanguageParser.BoolExprContext ctx) {}
    @Override public void exitBoolExpr(MyLanguageParser.BoolExprContext ctx) {}
    @Override public void enterExpr(MyLanguageParser.ExprContext ctx) {}
    @Override public void exitExpr(MyLanguageParser.ExprContext ctx) {}
    @Override public void enterRelOp(MyLanguageParser.RelOpContext ctx) {}
    @Override public void exitRelOp(MyLanguageParser.RelOpContext ctx) {}
    @Override public void visitTerminal(TerminalNode node) {}
    @Override public void visitErrorNode(ErrorNode node) {}
    @Override public void exitEveryRule(ParserRuleContext ctx) {}
    @Override public void enterEveryRule(ParserRuleContext ctx) {}

    public String generateJavaCode() {return codeBuilder.build();}