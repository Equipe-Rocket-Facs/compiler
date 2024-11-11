package com.equiperocket.compiler.v2.validation;

import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.ParserAux;

public class ParserValidator {

    private ParserAux parserAux;

    public ParserValidator(ParserAux parserAux) {
        this.parserAux = parserAux;
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
        if (checkParen()) {
            parserAux.saveCheckpoint();
            parserAux.advance();
            boolean isBoolExpr = checkBoolExpr();
            parserAux.restoreCheckpoint();
            return isBoolExpr;
        }

        return parserAux.check(TokenType.NAO) ||
                checkNextRelOp() ||
                parserAux.check(TokenType.VERDADEIRO) ||
                parserAux.check(TokenType.FALSO);
    }

    private boolean checkNextRelOp() {
        return parserAux.checkNext(TokenType.LESS) ||
                parserAux.checkNext(TokenType.GREATER) ||
                parserAux.checkNext(TokenType.LEQ) ||
                parserAux.checkNext(TokenType.GEQ) ||
                parserAux.checkNext(TokenType.EQ) ||
                parserAux.checkNext(TokenType.NEQ);
    }

    public boolean checkRelExpr() {
        return checkRelOp() && checkNextExpr();
    }

    private boolean checkRelOp() {
        return parserAux.check(TokenType.LESS) ||
                parserAux.check(TokenType.GREATER) ||
                parserAux.check(TokenType.LEQ) ||
                parserAux.check(TokenType.GEQ) ||
                parserAux.check(TokenType.EQ) ||
                parserAux.check(TokenType.NEQ);
    }

    private boolean checkNextExpr() {
        parserAux.saveCheckpoint();
        parserAux.advance();
        boolean isExpr = checkExpr();
        parserAux.restoreCheckpoint();
        return isExpr;
    }

    public boolean checkExpr() {
        if (checkParen()) {
            parserAux.saveCheckpoint();
            parserAux.advance();
            boolean isExpr = checkExpr();
            parserAux.restoreCheckpoint();
            return isExpr;
        }

        return parserAux.check(TokenType.NUM_INT) ||
                parserAux.check(TokenType.NUM_DEC) ||
                parserAux.check(TokenType.ID);
    }

    private boolean checkParen() {
        return parserAux.check(TokenType.LPAREN);
    }
}
