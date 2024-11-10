package com.equiperocket.compiler.v1.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.v1.util.CodeBuilder;
import com.equiperocket.compiler.v1.util.TypeMapper;
import com.equiperocket.compiler.v1.validation.VariableValidator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOProcessor {

    private final Map<String, String> variables;
    private final Map<String, Boolean> variablesInitialized;
    private final CodeBuilder codeBuilder;
    private final ExpressionProcessor expressionProcessor;
    private final BoolExpressionProcessor boolExpressionProcessor;
    private boolean isScannerInitialized;

    public IOProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.variablesInitialized = variablesInitialized;
        this.codeBuilder = codeBuilder;
        this.expressionProcessor = new ExpressionProcessor(variables, variablesInitialized);
        this.boolExpressionProcessor = new BoolExpressionProcessor(variables, variablesInitialized);
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

        variablesInitialized.put(varName, true);

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
                outputParts.add(child.getText());
            } else if (child instanceof MyLanguageParser.ExprContext) {
                processExpression((MyLanguageParser.ExprContext) child, outputParts);
            } else if (child instanceof MyLanguageParser.BoolExprContext) {
                processBoolExpression((MyLanguageParser.BoolExprContext) child, outputParts);
            }
        }

        codeBuilder.append("System.out.println(").append(String.join(" + ", outputParts)).appendLine(");");
    }

    private void processExpression(MyLanguageParser.ExprContext child, List<String> parts) {
        parts.add(expressionProcessor.processExpression(child));
    }

    private void processBoolExpression(MyLanguageParser.BoolExprContext child, List<String> parts) {
        parts.add(boolExpressionProcessor.processBoolExpression(child));
    }
}
