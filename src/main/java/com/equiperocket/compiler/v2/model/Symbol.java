package com.equiperocket.compiler.v2.model;

public class Symbol {

    private TokenType type;
    private boolean initialized = false;


    public Symbol() {
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }


    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
