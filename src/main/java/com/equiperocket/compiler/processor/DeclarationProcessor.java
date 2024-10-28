package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.util.TypeMapper;
import com.equiperocket.compiler.validation.TypeValidator;
import com.equiperocket.compiler.validation.VariableValidator;

import java.util.Map;

public class DeclarationProcessor {
    private final Map<String, String> variables;
    private final CodeBuilder codeBuilder;
    private final ExpressionProcessor expressionProcessor;

    public DeclarationProcessor(Map<String, String> variables, CodeBuilder codeBuilder) {
        this.variables = variables;
        this.codeBuilder = codeBuilder;
        this.expressionProcessor = new ExpressionProcessor(variables, codeBuilder);
    }

    public void processDeclaration(MyLanguageParser.DeclContext ctx) {
        String javaType = TypeMapper.toJavaType(ctx.type().getText());

        for (MyLanguageParser.DeclItemContext item : ctx.declItemList().declItem()) {
            processVariableDeclaration(javaType, item);
        }
    }

    private void processVariableDeclaration(String type, MyLanguageParser.DeclItemContext item) {
        String varName = getVariableName(item);
        VariableValidator.checkNotDeclared(varName, variables, item);
        variables.put(varName, type);

        codeBuilder.append(type + " " + varName);

        if (item.attribution() != null && item.attribution().expr() != null) {
            String value = expressionProcessor.processExpression(item.attribution().expr());
            String assignedType = expressionProcessor.inferExpressionType(item.attribution().expr());
            TypeValidator.validateTypes(type, assignedType, item);
            codeBuilder.append(" = " + value);
        } else {
            codeBuilder.append(" = " + TypeMapper.getDefaultValue(type));
        }

        codeBuilder.appendLine(";");
    }

    private String getVariableName(MyLanguageParser.DeclItemContext item) {
        return item.ID() != null ? item.ID().getText() : item.attribution().ID().getText();
    }
}