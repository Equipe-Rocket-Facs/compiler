package com.equiperocket.compiler.v1.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.v1.util.CodeBuilder;
import com.equiperocket.compiler.v1.util.TypeMapper;
import com.equiperocket.compiler.v1.validation.VariableValidator;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Map;

public class DeclarationProcessor {

    private final Map<String, String> variables;
    private final Map<String, Boolean> variablesInitialized;
    private final CodeBuilder codeBuilder;

    public DeclarationProcessor(Map<String, String> variables, Map<String, Boolean> variablesInitialized, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.variablesInitialized = variablesInitialized;
        this.codeBuilder = codeBuilder;
    }

    public void processDeclaration(MyLanguageParser.DeclContext ctx) {
        String type = TypeMapper.toJavaType(ctx.type().getText());

        codeBuilder.append(type).append(" ");

        List<TerminalNode> ids = ctx.declList().ID();
        int totalVariables = ids.size();

        for (int x = 0; x < totalVariables; x++) {
            String varName = ids.get(x).getText();

            processVariable(varName, type, ctx);

            if (!isLastVariable(x, totalVariables)) {
                codeBuilder.append(varName).append(", ");
            } else {
                codeBuilder.append(varName).appendLine(";");
            }
        }
    }

    private void processVariable(String varName, String type, MyLanguageParser.DeclContext ctx) {
        VariableValidator.checkNotDeclared(varName, variables, ctx);

        variables.put(varName, type);
        variablesInitialized.put(varName, false);
    }

    private boolean isLastVariable(int x, int totalVariables) {
        return x == totalVariables - 1;
    }
}
