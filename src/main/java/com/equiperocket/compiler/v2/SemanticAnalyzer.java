package com.equiperocket.compiler.v2;


import com.equiperocket.compiler.v2.exception.SemanticException;
import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;

import java.util.*;

public class SemanticAnalyzer {
    private final Map<String, Symbol> symbolTable;
    private final List<Token> tokens;

    public SemanticAnalyzer(Map<String, Symbol> symbolTable, List<Token> tokens) {
        this.symbolTable = symbolTable;
        this.tokens = tokens;
    }


    public void analyze() {
        analyzeVariableDeclarations();
        analyzeTokens(new TokenAux(tokens));
    }

    private void analyzeVariableDeclarations() {
        symbolTable.forEach((varName, symbol) -> {
            checkVariableType(varName, symbol);
            symbol.setInitialized(false);
        });
    }

    private void checkVariableType(String varName, Symbol symbol) {
        if (symbol.getType() == null) {
            throw new SemanticException("Uninitialized variable: " + varName);
        }
    }

    private void analyzeTokens(TokenAux tokenAux) {
        while (!tokenAux.isAtEnd()) {
            Token currentToken = tokenAux.peek();
            analyzeToken(tokenAux, currentToken);
        }
    }

    private void analyzeToken(TokenAux tokenAux, Token currentToken) {
        switch (currentToken.getType()) {
            case LEIA -> analyzeReadInput(tokenAux);
            case ESCREVA -> analyzeWriteOutput(tokenAux);
            case ASSIGN -> analyzeAssignment(tokenAux);
            case FOR -> analyzeForLoop(tokenAux);
            case IF -> analyzeIfStatement(tokenAux);
            case ELIF -> analyzeElseIfStatement(tokenAux);
            case ELSE -> analyzeElseStatement(tokenAux);
            case WHILE -> analyzeWhileLoop(tokenAux);
            default -> tokenAux.advance();
        }
    }


    private void analyzeReadInput(TokenAux tokenAux) {
        tokenAux.match(TokenType.LEIA);
        tokenAux.match(TokenType.LPAREN);
        Token variableToken = tokenAux.peek();
        tokenAux.match(TokenType.ID);

        markVariableAsInitialized(variableToken);

        tokenAux.match(TokenType.RPAREN);
    }

    private void analyzeWriteOutput(TokenAux tokenAux) {
        tokenAux.match(TokenType.ESCREVA);
        tokenAux.match(TokenType.LPAREN);

        tokenAux.match(TokenType.RPAREN);
    }

    private void analyzeAssignment(TokenAux tokenAux) {
        Token variableToken = tokenAux.peekAfter();
        tokenAux.match(TokenType.ASSIGN);

        Symbol targetSymbol = getTargetSymbol(tokenAux, variableToken);

        while (!isAssignmentComplete(tokenAux)) {
            Token currentToken = tokenAux.peek();
            validateTokenType(tokenAux, currentToken, targetSymbol);

            tokenAux.advance();
        }

        markVariableAsInitialized(variableToken);
    }

    private Symbol getTargetSymbol(TokenAux tokenAux, Token variableToken) {
        Symbol symbol = symbolTable.get(variableToken.getValue());
        if (symbol == null) {
            throw new SyntaxException("Undefined variable: " + variableToken.getValue(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
        }
        return symbol;
    }


    private boolean isAssignmentComplete(TokenAux tokenAux) {
        return tokenAux.isAtEnd() ||
                isBlockTerminatingToken(tokenAux.peek()) ||
                isNextAssignment(tokenAux);
    }

    private void validateTokenType(TokenAux tokenAux, Token token, Symbol targetSymbol) {
        switch (token.getType()) {
            case ID -> validateIdentifierToken(tokenAux, token, targetSymbol);
            case STRING -> validateStringToken(tokenAux, targetSymbol);
            case NUM_INT -> validateIntegerToken(tokenAux, targetSymbol);
            case NUM_DEC -> validateDecimalToken(tokenAux, targetSymbol);
        }
    }

    private void validateIdentifierToken(TokenAux tokenAux, Token token, Symbol targetSymbol) {
        Symbol sourceSymbol = symbolTable.get(token.getValue());

        if (!sourceSymbol.isInitialized()) {
                throw new SemanticException("Uninitialized variable: " + token.getValue(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
            }

        if (isTypeIncompatible(targetSymbol.getType(), sourceSymbol.getType())) {
            throw new SemanticException("Type mismatch: Cannot assign " +
                    sourceSymbol.getType() + " to " + targetSymbol.getType(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
        }
    }

    private void validateStringToken(TokenAux tokenAux, Symbol targetSymbol) {
        if (targetSymbol.getType() != TokenType.TEXTO) {
            throw new SemanticException("Invalid assignment: Cannot assign String to " + targetSymbol.getType(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
        }
    }

    private void validateIntegerToken(TokenAux tokenAux, Symbol targetSymbol) {
        if (isTypeIncompatible(targetSymbol.getType(), TokenType.INTEIRO)) {
            throw new SemanticException("Invalid assignment: Cannot assign int to " + targetSymbol.getType(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
        }
    }

    private void validateDecimalToken(TokenAux tokenAux, Symbol targetSymbol) {
        if (isTypeIncompatible(targetSymbol.getType(), TokenType.DECIMAL)) {
            throw new SemanticException("Invalid assignment: Cannot assign decimal to " + targetSymbol.getType(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
        }
    }

    private boolean isTypeIncompatible(TokenType targetType, TokenType sourceType) {
        return targetType != sourceType &&
                (targetType != TokenType.DECIMAL || sourceType != TokenType.INTEIRO);
    }

    private boolean isNextAssignment(TokenAux tokenAux) {
        return tokenAux.hasNext() && tokenAux.peekNext().getType() == TokenType.ASSIGN;
    }

    private void markVariableAsInitialized(Token variableToken) {
        Symbol targetSymbol = symbolTable.get(variableToken.getValue());
        if (targetSymbol != null) {
            targetSymbol.setInitialized(true);
        }
    }

    private boolean isBlockTerminatingToken(Token token) {
        return switch (token.getType()) {
            case IF, ELIF, ELSE, LEIA, ESCREVA, END_PROG, RBRACE, LBRACE, FOR, WHILE -> true;
            default -> false;
        };
    }

    private void analyzeForLoop(TokenAux tokenAux) {
        tokenAux.match(TokenType.FOR);
        tokenAux.match(TokenType.LPAREN);

        String initialization = extractForLoopSegment(tokenAux, TokenType.SEMICOLON);
        validateInitialization(initialization, tokenAux);

        extractForLoopSegment(tokenAux, TokenType.SEMICOLON);

        extractForLoopSegment(tokenAux, TokenType.RPAREN);

        analyzeBlock(tokenAux);
    }

    private String extractForLoopSegment(TokenAux tokenAux, TokenType terminator) {
        StringBuilder segmentBuilder = new StringBuilder();
        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != terminator) {
            segmentBuilder.append(tokenAux.peek().getValue()).append(" ");
            tokenAux.advance();
        }
        tokenAux.match(terminator);
        return segmentBuilder.toString().trim();
    }

    private void validateInitialization(String initialization, TokenAux tokenAux) {
        String[] parts = initialization.split("=");
        if (parts.length != 2) {
            Token token = tokenAux.peek();
            throw new SemanticException("Invalid initialization format: " + initialization + " at line:", token.getLine(), token.getColumn());
        }

        String variableName = parts[0].trim();
        String value = parts[1].trim();

        Symbol symbol = symbolTable.get(variableName);
        if (symbol == null) {
            throw new SemanticException("Undefined variable: " + variableName);
        }

        TokenType expectedType = symbol.getType();
        TokenType actualType = determineValueType(value);

        if (isTypeIncompatible(expectedType, actualType)) {
            Token token = tokenAux.peek();
            throw new SemanticException("Type mismatch: Cannot assign " + actualType + " to " + expectedType + " at line:", token.getLine(), token.getColumn());
        }

        symbol.setInitialized(true);
    }

    private TokenType determineValueType(String value) {
        if (value.equals("VERDADEIRO") || value.equals("FALSO")) {
            return TokenType.BOOL;
        } else if (value.contains(".")) {
            return TokenType.DECIMAL;
        } else {
            try {
                Integer.parseInt(value);
                return TokenType.INTEIRO;
            } catch (NumberFormatException e) {
                return TokenType.STRING;
            }
        }
    }

    private void analyzeIfStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.IF);
        tokenAux.match(TokenType.LPAREN);

        generateConditionExpression(tokenAux);
        analyzeBlock(tokenAux);
    }

    private void analyzeElseIfStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.ELIF);
        tokenAux.match(TokenType.LPAREN);

        generateConditionExpression(tokenAux);
        analyzeBlock(tokenAux);
    }

    private void analyzeElseStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.ELSE);
        analyzeBlock(tokenAux);
    }

    private void analyzeWhileLoop(TokenAux tokenAux) {
        tokenAux.match(TokenType.WHILE);
        tokenAux.match(TokenType.LPAREN);

        generateConditionExpression(tokenAux);
        analyzeBlock(tokenAux);
    }
    private void generateConditionExpression(TokenAux tokenAux) {

        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != TokenType.RPAREN) {
            Token token = tokenAux.peek();

            if (Objects.requireNonNull(token.getType()) == TokenType.ID) {// Handle variable tokens
                Symbol symbol = symbolTable.get(token.getValue());
                if (symbol == null) {
                    throw new SemanticException("Undefined variable: " + token.getValue());
                }

                if (!symbol.isInitialized()) {
                    throw new SemanticException("Uninitialized variable used in condition: " + token.getValue(), token.getLine(), token.getColumn());
                }
            }
            tokenAux.advance();
        }

        tokenAux.match(TokenType.RPAREN);
    }

    private void analyzeBlock(TokenAux tokenAux) {
        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != TokenType.RBRACE) {
            analyzeToken(tokenAux, tokenAux.peek());
        }
        tokenAux.match(TokenType.RBRACE);
    }
}