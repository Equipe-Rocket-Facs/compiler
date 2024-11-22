package com.equiperocket.compiler.v2.validation;

import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;

public class TokenValidator {

    private TokenAux tokenAux;

    public TokenValidator(TokenAux tokenAux) {
        this.tokenAux = tokenAux;
    }

    public boolean checkType() {
        return tokenAux.isType(TokenType.INTEIRO) ||
                tokenAux.isType(TokenType.DECIMAL) ||
                tokenAux.isType(TokenType.TEXTO) ||
                tokenAux.isType(TokenType.BOOL);
    }

    public boolean checkCommand() {
        return tokenAux.isType(TokenType.LEIA) ||
                tokenAux.isType(TokenType.ESCREVA) ||
                tokenAux.isType(TokenType.ID) ||
                tokenAux.isType(TokenType.IF) ||
                tokenAux.isType(TokenType.WHILE) ||
                tokenAux.isType(TokenType.FOR);
    }

    public boolean isString() {
        return tokenAux.isType(TokenType.STRING);
    }

    public boolean isBoolean() {
        return tokenAux.isType(TokenType.VERDADEIRO) || tokenAux.isType(TokenType.FALSO);
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
        return tokenAux.isType(TokenType.NUM_INT) || tokenAux.isType(TokenType.NUM_DEC);
    }

    public boolean isID() {
        return tokenAux.isType(TokenType.ID);
    }

    public boolean checkBoolExpr() {
        return tokenAux.isType(TokenType.NAO) || isBoolean();
    }

    public boolean checkRelExpr() {
        String expression = getExprValue("expr"); // Pega o valor da esquerda da expressao relacional

        return ExprValidator.isNumeric(expression);
    }

    public boolean checkRelOperator() {
        return checkRelExprOperator() || checkRelBoolOperator();
    }

    public boolean checkRelExprOperator() {
        return tokenAux.isType(TokenType.LESS) ||
                tokenAux.isType(TokenType.GREATER) ||
                tokenAux.isType(TokenType.LEQ) ||
                tokenAux.isType(TokenType.GEQ);
    }

    public boolean checkRelBoolOperator() {
        return tokenAux.isType(TokenType.EQ) || tokenAux.isType(TokenType.NEQ);
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
        if (tokenAux.isType(TokenType.LPAREN)) {
            openParens++;
        } else if (tokenAux.isType(TokenType.RPAREN)) {
            openParens--;
        }
        return openParens;
    }

    private boolean isEndOfExpression() {
        return tokenAux.isType(TokenType.LEIA) ||
                tokenAux.isType(TokenType.ESCREVA) ||
                isAttribution() ||
                tokenAux.isType(TokenType.IF) ||
                tokenAux.isType(TokenType.WHILE) ||
                tokenAux.isType(TokenType.FOR) ||
                isAppendingText() ||
                tokenAux.isType(TokenType.LBRACE) ||
                tokenAux.isType(TokenType.RBRACE) ||
                tokenAux.isType(TokenType.SEMICOLON);
    }

    private boolean isAttribution() {
        return tokenAux.isType(TokenType.ID) && tokenAux.isNextType(TokenType.ASSIGN);
    }

    private boolean isAppendingText() {
        return tokenAux.isType(TokenType.PLUS) && tokenAux.isNextType(TokenType.STRING);
    }

    private boolean checkBoolOp() {
        return tokenAux.isType(TokenType.OU) || tokenAux.isType(TokenType.E);
    }

    private boolean isEndOfRelExpr() {
        return isEndOfExpression() || checkBoolOp() || checkBoolExpr() || checkRelOperator();
    }

    private boolean isParen() {
        return tokenAux.isType(TokenType.LPAREN) || tokenAux.isType(TokenType.RPAREN);
    }
}
