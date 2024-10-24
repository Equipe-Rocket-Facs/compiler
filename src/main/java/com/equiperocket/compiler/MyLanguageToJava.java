package com.equiperocket.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyLanguageToJava implements MyLanguageListener {

    private StringBuilder javaCode = new StringBuilder();
    private Map<String, String> variables = new HashMap<>();
    private boolean isScannerPresent = false;

    @Override
    public void enterProg(MyLanguageParser.ProgContext ctx) {
        javaCode.append("public class Main {\n");
        javaCode.append("public static void main(String[] args) {\n");
    }

    @Override
    public void exitProg(MyLanguageParser.ProgContext ctx) {
        javaCode.append("}\n");
        javaCode.append("}\n");
    }

    @Override
    public void enterDecls(MyLanguageParser.DeclsContext ctx) {
    }

    @Override
    public void exitDecls(MyLanguageParser.DeclsContext ctx) {
    }

    @Override
    public void enterDecl(MyLanguageParser.DeclContext ctx) {
        String type = mapToJavaType(ctx.type().getText());
        StringBuilder declaration = new StringBuilder(type + " ");

        boolean isFirst = true;

        for (MyLanguageParser.DeclItemContext item : ctx.declItemList().declItem()) {
            String varName = item.ID() != null ? item.ID().getText() : item.attribution().ID().getText();

            checkVariableDeclaration(varName, ctx);

            variables.put(varName, type);

            if (!isFirst) {
                declaration.append(", ");
            }

            if (item.attribution() != null) {
                String assignedValue = item.attribution().expr().getText();
                declaration.append(varName).append(" = ").append(assignedValue);
            } else {
                declaration.append(varName);
            }

            isFirst = false;
        }

        javaCode.append(declaration).append(";\n");
    }

    @Override
    public void exitDecl(MyLanguageParser.DeclContext ctx) {
    }

    @Override
    public void enterType(MyLanguageParser.TypeContext ctx) {
    }

    @Override
    public void exitType(MyLanguageParser.TypeContext ctx) {
    }

    @Override
    public void enterDeclItemList(MyLanguageParser.DeclItemListContext ctx) {
    }

    @Override
    public void exitDeclItemList(MyLanguageParser.DeclItemListContext ctx) {
    }

    @Override
    public void enterDeclItem(MyLanguageParser.DeclItemContext ctx) {
    }

    @Override
    public void exitDeclItem(MyLanguageParser.DeclItemContext ctx) {
    }

    @Override
    public void enterCommands(MyLanguageParser.CommandsContext ctx) {
    }

    @Override
    public void exitCommands(MyLanguageParser.CommandsContext ctx) {
    }

    @Override
    public void enterCommand(MyLanguageParser.CommandContext ctx) {
    }

    @Override
    public void exitCommand(MyLanguageParser.CommandContext ctx) {
    }

    @Override
    public void enterReadInput(MyLanguageParser.ReadInputContext ctx) {
        String varName = ctx.ID().getText();

        checkForUndeclaredVariable(varName, ctx);

        if (!isScannerPresent) {
            javaCode.append("java.util.Scanner sc = new java.util.Scanner(System.in);\n");

            isScannerPresent = true;
        }

        javaCode.append(varName).append(" = ");

        String varType = variables.get(varName);

        javaCode.append(retrieveScannerOfType(varType));
    }

    @Override
    public void exitReadInput(MyLanguageParser.ReadInputContext ctx) {
    }

    @Override
    public void enterWriteOutput(MyLanguageParser.WriteOutputContext ctx) {
        javaCode.append("System.out.println(");

        List<String> parts = new ArrayList<>();

        // TODO: A lógica ainda precisa ser implementada

        javaCode.append(String.join(" + ", parts));
        javaCode.append(");\n");
    }

    @Override
    public void exitWriteOutput(MyLanguageParser.WriteOutputContext ctx) {
    }

    @Override
    public void enterAttribution(MyLanguageParser.AttributionContext ctx) {
        String varName = ctx.ID().getText();

        // Confere se a atribuição é feita dentro de uma declaração
        if (ctx.getParent() instanceof MyLanguageParser.DeclItemContext) {
            validateTypesForDeclaration(varName, ctx);
            return;
        }

        validateVariableAndType(varName, ctx);

        javaCode.append(varName).append(" = ").append(ctx.expr().getText()).append(";\n");
    }

    @Override
    public void exitAttribution(MyLanguageParser.AttributionContext ctx) {
    }

    @Override
    public void enterIfStmt(MyLanguageParser.IfStmtContext ctx) {
        javaCode.append("if (").append(ctx.condition().get(0).getText()).append(") ");
        enterBlock(ctx.block(0));

        for (int i = 1; i < ctx.condition().size(); i++) {
            javaCode.append(" else if (").append(ctx.condition(i).getText()).append(") ");
            enterBlock(ctx.block(i));
        }

        if (ctx.block().size() > ctx.condition().size()) {
            javaCode.append(" else ");
            enterBlock(ctx.block(ctx.block().size() - 1));
        }
    }

    @Override
    public void exitIfStmt(MyLanguageParser.IfStmtContext ctx) {
    }

    @Override
    public void enterWhileStmt(MyLanguageParser.WhileStmtContext ctx) {
        javaCode.append("while (").append(ctx.condition().getText()).append(") ");
    }

    @Override
    public void exitWhileStmt(MyLanguageParser.WhileStmtContext ctx) {
    }

    @Override
    public void enterForStmt(MyLanguageParser.ForStmtContext ctx) {
        String initialization = ctx.decl() != null ? ctx.decl().getText() : ctx.attribution().get(0).getText();

        javaCode.append("for (").append(initialization).append("; ").append(ctx.condition().getText()).append("; ");

        if (ctx.attribution().size() > 1) {
            javaCode.append(ctx.attribution().get(1).getText());
        }

        javaCode.append(") ");
    }

    @Override
    public void exitForStmt(MyLanguageParser.ForStmtContext ctx) {
    }

    @Override
    public void enterBlock(MyLanguageParser.BlockContext ctx) {
        javaCode.append("{\n");
    }

    @Override
    public void exitBlock(MyLanguageParser.BlockContext ctx) {
        javaCode.append("}\n");
    }

    @Override
    public void enterCondition(MyLanguageParser.ConditionContext ctx) {
    }

    @Override
    public void exitCondition(MyLanguageParser.ConditionContext ctx) {
    }

    @Override
    public void enterBoolExpr(MyLanguageParser.BoolExprContext ctx) {
        // TODO: Será preciso realizar a conversão adequada
    }

    @Override
    public void exitBoolExpr(MyLanguageParser.BoolExprContext ctx) {
    }

    @Override
    public void enterExpr(MyLanguageParser.ExprContext ctx) {
        // TODO: Os operadores matemáticos devem ser adicionados
    }

    @Override
    public void exitExpr(MyLanguageParser.ExprContext ctx) {
    }

    @Override
    public void enterRelOp(MyLanguageParser.RelOpContext ctx) {
        javaCode.append(" ").append(ctx.getText()).append(" ");
    }

    @Override
    public void exitRelOp(MyLanguageParser.RelOpContext ctx) {
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {
    }

    public String getJavaCode() {
        return javaCode.toString();
    }

    private String mapToJavaType(String type) {
        return switch (type) {
            case "numero" -> "int";
            case "decimal" -> "double";
            case "bool" -> "boolean";
            case "texto" -> "String";
            default -> null;
        };
    }

    private String retrieveScannerOfType(String varType) {
        return switch (varType) {
            case "int" -> "Integer.parseInt(sc.nextLine());\n";
            case "double" -> "Double.parseDouble(sc.nextLine());\n";
            case "boolean" -> "Boolean.parseBoolean(sc.nextLine());\n";
            case "String" -> "sc.nextLine();\n";
            default -> null;
        };
    }

    private String getTypeFromAssignedValue(MyLanguageParser.AttributionContext ctx) {
        if (ctx.expr() != null) {
            if (ctx.expr().NUM_INT() != null) {
                return "int";
            } else if (ctx.expr().NUM_DEC() != null) {
                return "double";
            } else if (ctx.expr().ID() != null) {
                String varName = ctx.expr().ID().getText();

                checkForUndeclaredVariable(varName, ctx);

                return variables.get(varName);
            }
        } else if (ctx.boolExpr() != null) {
            if (ctx.boolExpr().BOOL() != null) {
                return "boolean";
            }
        } else if (ctx.TEXT() != null) {
            return "String";
        }

        return null;
    }

    private void validateVariableAndType(String varName, MyLanguageParser.AttributionContext ctx) {
        checkForUndeclaredVariable(varName, ctx);

        String type = variables.get(varName);
        String assignedType = getTypeFromAssignedValue(ctx);

        checkVariableType(type, assignedType, ctx);
    }

    private void checkVariableDeclaration(String varName, ParserRuleContext ctx) {
        if (variables.containsKey(varName)) {
            String msg = "Variável já declarada: " + varName;
            String errorMsg = obtainErrorMessageWithLocation(ctx, msg);
            throw new RuntimeException(errorMsg);
        }
    }

    private void checkForUndeclaredVariable(String varName, ParserRuleContext ctx) {
        if (!variables.containsKey(varName)) {
            String msg = "Variável não declarada: " + varName;
            String errorMsg = obtainErrorMessageWithLocation(ctx, msg);
            throw new RuntimeException(errorMsg);
        }
    }

    private void checkVariableType(String type, String assignedType, ParserRuleContext ctx) {
        if (!type.equals(assignedType)) {
            String msg = "Tentativa de atribuir " + assignedType + " a uma variável do tipo " + type;
            String errorMsg = obtainErrorMessageWithLocation(ctx, msg);
            throw new RuntimeException(errorMsg);
        }
    }

    private void validateTypesForDeclaration(String varName, MyLanguageParser.AttributionContext ctx) {
        String declaredType = variables.get(varName);
        String assignedType = getTypeFromAssignedValue(ctx);

        checkVariableType(declaredType, assignedType, ctx);
    }

    private String obtainErrorMessageWithLocation(ParserRuleContext ctx, String msg) {
        int line = ctx.getStart().getLine();
        int charPosition = ctx.getStart().getCharPositionInLine();
        return "Linha " + line + ", Posição " + charPosition + " - " + msg;
    }
}
