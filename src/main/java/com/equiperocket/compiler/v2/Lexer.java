package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private List<Token> tokens = new ArrayList<>();
    private String input;
    private Map<String, Symbol> symbolTable;

    public Lexer(String input, Map<String, Symbol> symbolTable) {
        this.input = input;
        this.symbolTable = symbolTable;
    }

    public List<Token> tokenize() {
        int pos = 0;

        while (pos < input.length()) {
            boolean matched = false;

            for (TokenType tokenType : TokenType.values()) {
                Pattern pattern = Pattern.compile("^" + tokenType.pattern);
                Matcher matcher = pattern.matcher(input.substring(pos));

                if (matcher.find()) {
                    String tokenValue = matcher.group();

                    // Ignora espacos em branco e comentarios
                    if (!isIgnored(tokenType)) {
                        // Adiciona variaveis nao contidas na tabela de simbolos
                        if (isVariable(tokenType) && !isPresent(tokenValue)) {
                            symbolTable.put(tokenValue, new Symbol());
                        }

                        tokens.add(new Token(tokenType, tokenValue));
                    }

                    pos += tokenValue.length();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new SyntaxException("Unrecognized token at: " + pos);
            }
        }
        return tokens;
    }

    private boolean isVariable(TokenType type) {
        return type.equals(TokenType.ID);
    }

    private boolean isPresent(String value) {
        return symbolTable.containsKey(value);
    }

    private boolean isIgnored(TokenType type) {
        return type.equals(TokenType.WS) || type.equals(TokenType.COMMENT) || type.equals(TokenType.COMMENT_MULTILINE);
    }
}
