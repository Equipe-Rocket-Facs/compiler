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
        return parserAux.check(TokenType.NAO) ||
                checkNextRelOp() ||
                parserAux.check(TokenType.BOOL) ||
                parserAux.check(TokenType.LPAREN);
    }

    public boolean checkNextRelOp() {
        return parserAux.checkNext(TokenType.LESS) ||
                parserAux.checkNext(TokenType.GREATER) ||
                parserAux.checkNext(TokenType.LEQ) ||
                parserAux.checkNext(TokenType.GEQ) ||
                parserAux.checkNext(TokenType.EQ) ||
                parserAux.checkNext(TokenType.NEQ);
    }

    public boolean checkNextMathOp() {
        return parserAux.checkNext(TokenType.PLUS) ||
                parserAux.checkNext(TokenType.MINUS) ||
                parserAux.checkNext(TokenType.MULT) ||
                parserAux.checkNext(TokenType.DIV);
    }

    public boolean checkExpr() {
        return parserAux.check(TokenType.NUM_INT) ||
                parserAux.check(TokenType.NUM_DEC) ||
                parserAux.check(TokenType.ID) ||
                parserAux.check(TokenType.LPAREN);
    }
}
