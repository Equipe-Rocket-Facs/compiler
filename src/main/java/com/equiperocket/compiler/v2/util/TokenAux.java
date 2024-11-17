package com.equiperocket.compiler.v2.util;

import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;

import java.util.List;

public class TokenAux {

    private List<Token> tokens;
    private int current = 0;
    private int checkpoint = 0;

    public TokenAux(List<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    public void matchReq(TokenType type) {
        if (!match(type)) {
            Token token = peek();
            throw new SyntaxException(
                    "Expecting " + type + " but found " + token.getType(),
                    token.getLine(),
                    token.getColumn());
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

    public void saveCheckpoint() {
        checkpoint = current;
    }

    public void restoreCheckpoint() {
        current = checkpoint;
    }

    public void advance() {
        current++;
    }

    public boolean isAtEnd() {
        return current >= tokens.size();
    }

    public Token peek() {
        return tokens.get(current);
    }

    public Token peekNext() {
        return tokens.get(current + 1);
    }

    public Token peekAfter() {
        return tokens.get(current - 1);
    }

    public boolean hasNext() {
        return current + 1 < tokens.size();
    }
}
