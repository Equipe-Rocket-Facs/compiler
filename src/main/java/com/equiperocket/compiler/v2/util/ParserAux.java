package com.equiperocket.compiler.v2.util;

import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;

import java.util.List;

public class ParserAux {

    private List<Token> tokens;
    private int current = 0;
    private int checkpoint = 0;

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
            Token token = peek();
            throw new SyntaxException("Expecting " + type + " but found " + token.getType() +
                    " at line " + token.getLine() + ", column " + token.getColumn());
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
