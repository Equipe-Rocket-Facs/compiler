package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.LexicalException;
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
    private int line = 1;
    private int column = 1;

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

                        tokens.add(new Token(tokenType, tokenValue, line, column));

                        if (isEOF(tokenType)) {
                            return tokens;
                        }
                    }

                    pos += tokenValue.length();
                    updatePosition(tokenValue);
                    matched = true;
                    break;
                }
            }

            // Checagem para tokens nao validos
            if (!matched) {
                throw new LexicalException("Unrecognized token at line", line, column);
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
        return type.equals(TokenType.WS) ||
                type.equals(TokenType.COMMENT) ||
                type.equals(TokenType.COMMENT_MULTILINE);
    }

    private boolean isEOF(TokenType type) {
        return type.equals(TokenType.END_PROG);
    }

    private void updatePosition(String tokenValue) {
        for (char c : tokenValue.toCharArray()) {
            if (c == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
        }
    }
}
