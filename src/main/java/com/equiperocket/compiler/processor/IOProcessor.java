package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.util.TypeMapper;
import com.equiperocket.compiler.validation.VariableValidator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class IOProcessor {
    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;
    private boolean isScannerInitialized;

    public IOProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
        this.isScannerInitialized = false;
    }

    public void processInput(MyLanguageParser.ReadInputContext ctx) {
        String varName = ctx.ID().getText();
        VariableValidator.checkDeclared(varName, variables, ctx);

        if (!isScannerInitialized) {
            initializeScanner();
        }

        String varType = variables.get(varName);
        String scannerMethod = TypeMapper.getScannerMethod(varType);
        codeBuilder.appendLine(varName + " = " + scannerMethod + ";");
    }

    public void processOutput(MyLanguageParser.WriteOutputContext ctx) {
        List<String> outputParts = new ArrayList<>();
        ExpressionProcessor exprProcessor = new ExpressionProcessor(variables, codeBuilder);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if (child instanceof TerminalNode) {
                processOutputTerminal((TerminalNode) child, outputParts);
            } else if (child instanceof MyLanguageParser.ExprContext) {
                outputParts.add(exprProcessor.processExpression((MyLanguageParser.ExprContext) child));
            }
        }

        codeBuilder.appendLine("System.out.println(" + String.join(" + ", outputParts) + ");");
    }

    private void initializeScanner() {
        if (!isScannerInitialized) {
            codeBuilder.appendLine("java.util.Scanner sc = new java.util.Scanner(System.in);");
            isScannerInitialized = true;
        }
    }

    private void processOutputTerminal(TerminalNode node, List<String> parts) {
        if (node.getSymbol().getType() == MyLanguageParser.TEXT) {
            parts.add(node.getText());
        } else if (node.getSymbol().getType() == MyLanguageParser.BOOL) {
            parts.add(node.getText().equals("VERDADEIRO") ? "true" : "false");
        }
    }
}