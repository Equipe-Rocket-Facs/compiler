package com.equiperocket.compiler.v2.model;

public class Symbol {

    private TokenType type;
    private Object value;

    public Symbol() {
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
