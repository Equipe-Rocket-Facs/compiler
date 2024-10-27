package com.equiperocket.compiler.validation;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.exception.ExceptionHandler;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;

public abstract class Validation extends ExceptionHandler {

    protected Map<String, String> variables = new HashMap<>();

    abstract protected void validateTypesForDeclaration(String varName, MyLanguageParser.AttributionContext ctx);

    abstract protected void validateVariableAndType(String varName, MyLanguageParser.AttributionContext ctx);

    protected void checkVariableDeclaration(String varName, ParserRuleContext ctx) {
        if (variables.containsKey(varName)) {
            String msg = "Variável já declarada: " + varName;
            throwException(msg, ctx);
        }
    }

    protected void checkForUndeclaredVariable(String varName, ParserRuleContext ctx) {
        if (!variables.containsKey(varName)) {
            String msg = "Variável não declarada: " + varName;
            throwException(msg, ctx);
        }
    }

    protected void checkVariableType(String type, String assignedType, ParserRuleContext ctx) {
        if (!type.equals(assignedType)) {
            String msg = "Tentativa de atribuir " + assignedType + " a uma variável do tipo " + type;
            throwException(msg, ctx);
        }
    }
}
