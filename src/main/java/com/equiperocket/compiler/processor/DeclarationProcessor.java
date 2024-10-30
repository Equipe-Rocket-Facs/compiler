package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.util.TypeMapper;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class DeclarationProcessor {

    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;

    public DeclarationProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
    }

    public void processDeclaration(MyLanguageParser.DeclContext ctx) {
        String type = TypeMapper.toJavaType(ctx.type().getText());

        codeBuilder.append(type).append(" ");

        int totalVariables = ctx.declList().ID().size();

        for (int x = 0; x < totalVariables; x++) {
            String varName = ctx.declList().ID().get(x).getText();

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
    }

    private boolean isLastVariable(int x, int totalVariables) {
        return x == totalVariables - 1;
    }
}
