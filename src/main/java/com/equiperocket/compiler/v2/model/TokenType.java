package com.equiperocket.compiler.v2.model;

public enum TokenType {

    // A ordem influencia em qual tipo de token sera identificado
    WS("[ \t\r\n]+"),
    COMMENT("//.*"),
    COMMENT_MULTILINE("/\\*.*?\\*/"),

    PROG("\\bprograma\\b"),
    END_PROG("\\bfimprog\\b"),
    INTEIRO("\\binteiro\\b"),
    DECIMAL("\\bdecimal\\b"),
    TEXTO("\\btexto\\b"),
    BOOL("\\bbool\\b"),
    LEIA("\\bleia\\b"),
    ESCREVA("\\bescreva\\b"),
    IF("\\bif\\b"),
    ELIF("\\belse\\s+if\\b"),
    ELSE("\\belse\\b"),
    WHILE("\\bwhile\\b"),
    FOR("\\bfor\\b"),

    OU("\\bOU\\b"),
    E("\\bE\\b"),
    NAO("\\bNAO\\b"),

    VERDADEIRO("\\bVERDADEIRO\\b"),
    FALSO("\\bFALSO\\b"),
    ID("\\b[a-zA-Z_][a-zA-Z_0-9]*\\b"),
    NUM_DEC("\\b[-+]?[0-9]+\\.[0-9]+\\b"),
    NUM_INT("\\b[-+]?[0-9]+\\b"),
    STRING("\"(\\\\.|[^\"])*\""),

    PLUS("\\+"),
    MINUS("-"),
    MULT("\\*"),
    DIV("/"),
    LEQ("<="),
    GEQ(">="),
    LESS("<"),
    GREATER(">"),
    EQ("=="),
    ASSIGN("="),
    NEQ("!="),
    LPAREN("\\("),
    RPAREN("\\)"),
    LBRACE("\\{"),
    RBRACE("\\}"),
    COMMA(","),
    SEMICOLON(";");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
