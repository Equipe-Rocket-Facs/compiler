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

    // Combina e avança se o tipo corresponder
    public boolean match(TokenType type) {
        if (isType(type)) {
            advance();
            return true;
        }
        return false;
    }

    // Combina e avança obrigatoriamente
    public void require(TokenType type) {
        if (!match(type)) {
            Token token = peek();
            throw new SyntaxException(
                    "Expecting " + type + " but found " + token.getType(),
                    token.getLine(),
                    token.getColumn());
        }
    }

    // Verifica se o token atual corresponde ao tipo
    public boolean isType(TokenType type) {
        return !isAtEnd() && peek().getType().equals(type);
    }

    // Verifica se o próximo token corresponde ao tipo
    public boolean isNextType(TokenType type) {
        return hasNext() && peekNext().getType().equals(type);
    }

    public void saveCheckpoint() {
        checkpoint = current;
    }

    public void restoreCheckpoint() {
        current = checkpoint;
    }

    public void advance() {
        if (!isAtEnd()) current++;
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

    public Token peekTwoAhead() {
        return tokens.get(current + 2);
    }

    public Token peekPrevious() {
        return tokens.get(current - 1);
    }

    public boolean hasNext() {
        return current + 1 < tokens.size();
    }
}
