package com.equiperocket.compiler.v2.validation;

import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;

import java.util.Map;

public class TokenValidator {

    private TokenAux parserAux;
    private Map<String, Symbol> symbolTable;

    public TokenValidator(TokenAux parserAux, Map<String, Symbol> symbolTable) {
        this.parserAux = parserAux;
        this.symbolTable = symbolTable;
    }

    public boolean checkType() {
        return parserAux.check(TokenType.INTEIRO) ||
                parserAux.check(TokenType.DECIMAL) ||
                parserAux.check(TokenType.TEXTO) ||
                parserAux.check(TokenType.BOOL);
    }

    public boolean checkCommand() {
        return parserAux.check(TokenType.LEIA) ||
                parserAux.check(TokenType.ESCREVA) ||
                parserAux.check(TokenType.ID) ||
                parserAux.check(TokenType.IF) ||
                parserAux.check(TokenType.WHILE) ||
                parserAux.check(TokenType.FOR);
    }

    public boolean checkBoolExpr() {
        if (checkParen()) return checkNextExpr("boolExpr");

        return parserAux.check(TokenType.NAO) ||
                checkNextRelOp() ||
                parserAux.check(TokenType.VERDADEIRO) ||
                parserAux.check(TokenType.FALSO) ||
                // Resultaria em erros, pois iria permitir uma expr entre ID e um tipo invalido como String
//                parserAux.check(TokenType.ID);
                checkBoolVar(); // Faz parte do semantico, mas faz-se necessario
    }

    private boolean checkNextRelOp() {
        return parserAux.checkNext(TokenType.LESS) ||
                parserAux.checkNext(TokenType.GREATER) ||
                parserAux.checkNext(TokenType.LEQ) ||
                parserAux.checkNext(TokenType.GEQ) ||
                parserAux.checkNext(TokenType.EQ) ||
                parserAux.checkNext(TokenType.NEQ);
    }

    // Permite IDs como condicao em estruturas if, while e for, alem de imprimir no escreva
    private boolean checkBoolVar() {
        if (parserAux.check(TokenType.ID)) {
            String idName = parserAux.peek().getValue();
            Symbol symbol = symbolTable.get(idName);
            return symbol != null && symbol.getType().equals(TokenType.BOOL);
        }
        return false;
    }

    public boolean checkRelExpr() {
        return checkRelOp() && checkNextExpr("expr");
    }

    private boolean checkRelOp() {
        return parserAux.check(TokenType.LESS) ||
                parserAux.check(TokenType.GREATER) ||
                parserAux.check(TokenType.LEQ) ||
                parserAux.check(TokenType.GEQ) ||
                parserAux.check(TokenType.EQ) ||
                parserAux.check(TokenType.NEQ);
    }

    public boolean checkExpr() {
        if (checkParen()) return checkNextExpr("expr");

        return parserAux.check(TokenType.NUM_INT) ||
                parserAux.check(TokenType.NUM_DEC) ||
                parserAux.check(TokenType.ID);
    }

    private boolean checkParen() {
        return parserAux.check(TokenType.LPAREN);
    }

    private boolean checkNextExpr(String whoIsCalling) {
        parserAux.saveCheckpoint();
        parserAux.advance();

        boolean isExpr;
        if (whoIsCalling.equals("boolExpr")) {
            isExpr = checkBoolExpr();
        } else {
            isExpr = checkExpr();
        }

        parserAux.restoreCheckpoint();
        return isExpr;
    }
}
