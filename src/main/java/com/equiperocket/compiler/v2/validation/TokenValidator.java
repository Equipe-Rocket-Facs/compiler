package com.equiperocket.compiler.v2.validation;

import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;

public class TokenValidator {

    private TokenAux tokenAux;

    public TokenValidator(TokenAux tokenAux) {
        this.tokenAux = tokenAux;
    }

    public boolean checkType() {
        return tokenAux.check(TokenType.INTEIRO) ||
                tokenAux.check(TokenType.DECIMAL) ||
                tokenAux.check(TokenType.TEXTO) ||
                tokenAux.check(TokenType.BOOL);
    }

    public boolean checkCommand() {
        return tokenAux.check(TokenType.LEIA) ||
                tokenAux.check(TokenType.ESCREVA) ||
                tokenAux.check(TokenType.ID) ||
                tokenAux.check(TokenType.IF) ||
                tokenAux.check(TokenType.WHILE) ||
                tokenAux.check(TokenType.FOR);
    }

    public boolean isString() {
        return tokenAux.check(TokenType.STRING);
    }

    public boolean isBoolean() {
        return tokenAux.check(TokenType.VERDADEIRO) || tokenAux.check(TokenType.FALSO);
    }

    public boolean isBoolExpr() {
        String expression = getExprValue("boolExpr");

        if (checkBoolExpr()) return true; // Vindo primeiro economizamos trabalho desnecessario

        return ExprValidator.isBoolExprValid(expression);
    }

    public boolean isRelExpr() {
        if (!checkRelOperator()) return false;

        String expression = getExprValue("relExpr");

        return ExprValidator.isRelExprValid(expression);
    }

    public boolean isExpr() {
        String expression = getExprValue("expr");

        if (checkExpr()) return true;

        return ExprValidator.isExprValid(expression);
    }

    public boolean isNumber() {
        return tokenAux.check(TokenType.NUM_INT) || tokenAux.check(TokenType.NUM_DEC);
    }

    public boolean isID() {
        return tokenAux.check(TokenType.ID);
    }

    public boolean checkBoolExpr() {
        return tokenAux.check(TokenType.NAO) || isBoolean();
    }

    public boolean checkRelExpr() {
        String expression = getExprValue("expr"); // Pega o valor da esquerda da expressao relacional

        return ExprValidator.isNumeric(expression);
    }

    public boolean checkRelOperator() {
        return checkRelExprOperator() || checkRelBoolOperator();
    }

    public boolean checkRelExprOperator() {
        return tokenAux.check(TokenType.LESS) ||
                tokenAux.check(TokenType.GREATER) ||
                tokenAux.check(TokenType.LEQ) ||
                tokenAux.check(TokenType.GEQ);
    }

    public boolean checkRelBoolOperator() {
        return tokenAux.check(TokenType.EQ) || tokenAux.check(TokenType.NEQ);
    }

    public boolean checkExpr() {
        return isNumber() || isID();
    }

    private String getExprValue(String exprType) {
        tokenAux.saveCheckpoint();

        if (checkRelExpr(exprType)) tokenAux.advance(); // Exclui o operador da expressao relacional

        StringBuilder expression = new StringBuilder();
        int openParens = 0;

        while (tokenAux.hasNext() && !isEndOfExpression()) {
            if (isExprComplete(exprType)) break;

            openParens = trackParen(openParens);
            if (openParens < 0) break;

            if (!isParen()) { // Exclui parentesis da expressao para impedir entradas vazias
                String value = tokenAux.peek().getValue();
                expression.append(value);
            }
            tokenAux.advance();
        }

        tokenAux.restoreCheckpoint();
        return expression.toString();
    }

    private boolean checkRelExpr(String exprType) {
        return isRelExprType(exprType) && !checkRelBoolOperator();
    }

    private boolean isExprComplete(String exprType) {
        return (isRelExprType(exprType) || isExprType(exprType)) && isEndOfRelExpr();
    }

    private boolean isRelExprType(String exprType) {
        return exprType.equals("relExpr");
    }

    private boolean isExprType(String exprType) {
        return exprType.equals("expr");
    }

    // Mantem sob controle abertura/fechamento de parentesis
    private int trackParen(int openParens) {
        if (tokenAux.check(TokenType.LPAREN)) {
            openParens++;
        } else if (tokenAux.check(TokenType.RPAREN)) {
            openParens--;
        }
        return openParens;
    }

    private boolean isEndOfExpression() {
        return tokenAux.check(TokenType.LEIA) ||
                tokenAux.check(TokenType.ESCREVA) ||
                isAttribution() ||
                tokenAux.check(TokenType.IF) ||
                tokenAux.check(TokenType.WHILE) ||
                tokenAux.check(TokenType.FOR) ||
                isAppendingText() ||
                tokenAux.check(TokenType.LBRACE) ||
                tokenAux.check(TokenType.RBRACE) ||
                tokenAux.check(TokenType.SEMICOLON);
    }

    private boolean isAttribution() {
        return tokenAux.check(TokenType.ID) && tokenAux.checkNext(TokenType.ASSIGN);
    }

    private boolean isAppendingText() {
        return tokenAux.check(TokenType.PLUS) && tokenAux.checkNext(TokenType.STRING);
    }

    private boolean checkBoolOp() {
        return tokenAux.check(TokenType.OU) || tokenAux.check(TokenType.E);
    }

    private boolean isEndOfRelExpr() {
        return isEndOfExpression() || checkBoolOp() || checkBoolExpr() || checkRelOperator();
    }

    private boolean isParen() {
        return tokenAux.check(TokenType.LPAREN) || tokenAux.check(TokenType.RPAREN);
    }
}
