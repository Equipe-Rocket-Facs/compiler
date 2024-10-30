package com.equiperocket.compiler;

import com.equiperocket.compiler.processor.CommandProcessor;
import com.equiperocket.compiler.processor.DeclarationProcessor;
import com.equiperocket.compiler.util.CodeBuilder;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class MyLanguageToJava implements MyLanguageListener {

    private final CodeBuilder codeBuilder;
    private final DeclarationProcessor declarationProcessor;
    private final CommandProcessor commandProcessor;

    public MyLanguageToJava() {
        this.codeBuilder = new CodeBuilder();
        Map<String, String> variables = new HashMap<>();
        Map<String, Boolean> variablesInitialized = new HashMap<>();
        this.declarationProcessor = new DeclarationProcessor(variables, variablesInitialized, codeBuilder);
        this.commandProcessor = new CommandProcessor(variables, variablesInitialized, codeBuilder);
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
    public void enterCommands(MyLanguageParser.CommandsContext ctx) {
        commandProcessor.processCommands(ctx);
    }

    @Override public void enterDecls(MyLanguageParser.DeclsContext ctx) {}
    @Override public void exitDecls(MyLanguageParser.DeclsContext ctx) {}
    @Override public void exitDecl(MyLanguageParser.DeclContext ctx) {}
    @Override public void enterDeclList(MyLanguageParser.DeclListContext ctx) {}
    @Override public void exitDeclList(MyLanguageParser.DeclListContext ctx) {}
    @Override public void enterType(MyLanguageParser.TypeContext ctx) {}
    @Override public void exitType(MyLanguageParser.TypeContext ctx) {}
    @Override public void exitCommands(MyLanguageParser.CommandsContext ctx) {}
    @Override public void enterCommand(MyLanguageParser.CommandContext ctx) {}
    @Override public void exitCommand(MyLanguageParser.CommandContext ctx) {}
    @Override public void enterReadInput(MyLanguageParser.ReadInputContext ctx) {}
    @Override public void exitReadInput(MyLanguageParser.ReadInputContext ctx) {}
    @Override public void enterWriteOutput(MyLanguageParser.WriteOutputContext ctx) {}
    @Override public void exitWriteOutput(MyLanguageParser.WriteOutputContext ctx) {}
    @Override public void enterAttribution(MyLanguageParser.AttributionContext ctx) {}
    @Override public void exitAttribution(MyLanguageParser.AttributionContext ctx) {}
    @Override public void enterIfStmt(MyLanguageParser.IfStmtContext ctx) {}
    @Override public void exitIfStmt(MyLanguageParser.IfStmtContext ctx) {}
    @Override public void enterWhileStmt(MyLanguageParser.WhileStmtContext ctx) {}
    @Override public void exitWhileStmt(MyLanguageParser.WhileStmtContext ctx) {}
    @Override public void enterForStmt(MyLanguageParser.ForStmtContext ctx) {}
    @Override public void exitForStmt(MyLanguageParser.ForStmtContext ctx) {}
    @Override public void enterBlock(MyLanguageParser.BlockContext ctx) {}
    @Override public void exitBlock(MyLanguageParser.BlockContext ctx) {}
    @Override public void enterCondition(MyLanguageParser.ConditionContext ctx) {}
    @Override public void exitCondition(MyLanguageParser.ConditionContext ctx) {}
    @Override public void enterBoolExpr(MyLanguageParser.BoolExprContext ctx) {}
    @Override public void exitBoolExpr(MyLanguageParser.BoolExprContext ctx) {}
    @Override public void enterRelExpr(MyLanguageParser.RelExprContext ctx) {}
    @Override public void exitRelExpr(MyLanguageParser.RelExprContext ctx) {}
    @Override public void enterRelOp(MyLanguageParser.RelOpContext ctx) {}
    @Override public void exitRelOp(MyLanguageParser.RelOpContext ctx) {}
    @Override public void enterExpr(MyLanguageParser.ExprContext ctx) {}
    @Override public void exitExpr(MyLanguageParser.ExprContext ctx) {}
    @Override public void enterMathOp(MyLanguageParser.MathOpContext ctx) {}
    @Override public void exitMathOp(MyLanguageParser.MathOpContext ctx) {}
    @Override public void visitTerminal(TerminalNode terminalNode) {}
    @Override public void visitErrorNode(ErrorNode errorNode) {}
    @Override public void enterEveryRule(ParserRuleContext parserRuleContext) {}
    @Override public void exitEveryRule(ParserRuleContext parserRuleContext) {}

    public String generateJavaCode() {
        return codeBuilder.build();
    }
}
