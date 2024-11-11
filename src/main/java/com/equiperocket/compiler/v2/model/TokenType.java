package com.equiperocket.compiler.v2.model;

public enum TokenType {

    // A ordem influencia em qual tipo de token sera identificado
    WS("[ \t\r\n]+"),
    COMMENT("//.*"),
    COMMENT_MULTILINE("/\\*.*?\\*/"),

    PROG("programa"),
    END_PROG("fimprog"),
    INTEIRO("inteiro"),
    DECIMAL("decimal"),
    TEXTO("texto"),
    BOOL("bool"),
    LEIA("leia"),
    ESCREVA("escreva"),
    IF("if"),
    ELIF("else\\s+if"),
    ELSE("else"),
    WHILE("while"),
    FOR("for"),

    OU("OU"),
    E("E"),
    NAO("NAO"),

    VERDADEIRO("VERDADEIRO"),
    FALSO("FALSO"),
    ID("[a-zA-Z_][a-zA-Z_0-9]*"),
    NUM_DEC("[-+]?[0-9]+\\.[0-9]+"),
    NUM_INT("[-+]?[0-9]+"),
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
