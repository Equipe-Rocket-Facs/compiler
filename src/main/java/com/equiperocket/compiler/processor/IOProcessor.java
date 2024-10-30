package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.util.TypeMapper;
import com.equiperocket.compiler.validation.VariableValidator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOProcessor {

    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;
    private final ExpressionProcessor expressionProcessor;
    private boolean isScannerInitialized;

    public IOProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
        this.expressionProcessor = new ExpressionProcessor(variables);
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

        codeBuilder.append(varName).append(" = ").append(scannerMethod).appendLine(";");
    }

    private void initializeScanner() {
        if (!isScannerInitialized) {
            codeBuilder.appendScanner();
            isScannerInitialized = true;
        }
    }

    public void processOutput(MyLanguageParser.WriteOutputContext ctx) {
        List<String> outputParts = new ArrayList<>();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);

            if (child instanceof TerminalNode) {
                processTerminalNode((TerminalNode) child, outputParts);
            } else if (child instanceof MyLanguageParser.ExprContext) {
                processExpression((MyLanguageParser.ExprContext) child, outputParts);
            }
        }

        codeBuilder.append("System.out.println(").append(String.join(" + ", outputParts)).appendLine(");");
    }

    private void processTerminalNode(TerminalNode node, List<String> parts) {
        if (node.getSymbol().getType() == MyLanguageParser.TEXT) {
            parts.add(node.getText());
        } else if (node.getSymbol().getType() == MyLanguageParser.BOOL) {
            parts.add(node.getText().equals("VERDADEIRO") ? "true" : "false");
        }
    }

    private void processExpression(MyLanguageParser.ExprContext child, List<String> parts) {
        parts.add(expressionProcessor.processExpression(child));
    }
}
