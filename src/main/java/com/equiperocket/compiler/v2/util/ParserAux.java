package com.equiperocket.compiler.v2.util;

import com.equiperocket.compiler.v2.exception.LexicalException;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;

import java.util.List;

public class ParserAux {

    private List<Token> tokens;
    private int current = 0;

    public ParserAux(List<Token> tokens) {
        this.tokens = tokens;
    }

    protected boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    protected void matchReq(TokenType type) {
        if (!match(type)) {
            throw new LexicalException("Expecting: " + type);
        }
    }

    public boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType().equals(type);
    }

    public boolean checkNext(TokenType type) {
        if (isAtEnd()) return false;
        return peekNext().getType().equals(type);
    }

    protected void advance() {
        current++;
    }

    protected boolean isAtEnd() {
        return current >= tokens.size();
    }

    protected Token peek() {
        return tokens.get(current);
    }

    protected Token peekNext() {
        return tokens.get(current + 1);
    }
}
