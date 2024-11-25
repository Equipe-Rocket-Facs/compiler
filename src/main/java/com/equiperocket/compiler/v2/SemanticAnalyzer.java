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
        for (Map.Entry<String, Symbol> entry : symbolTable.entrySet()) {
            String varName = entry.getKey();
            Symbol symbol = entry.getValue();

            if (symbol.getCount() >= 2) {
                throw new SemanticException("Duplicate variable declaration: " + varName);
            }

            symbol.incrementCount();

            checkVariableType(varName, symbol);
            symbol.setInitialized(false);
        }
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

        extractOutputExpression(tokenAux);

        tokenAux.match(TokenType.RPAREN);
    }

    private void extractOutputExpression(TokenAux tokenAux) {
        List<Token> outputTokens = collectOutputTokens(tokenAux);

        for (Token token : outputTokens) {
            if (token.getType() == TokenType.ID) {
                Symbol symbol = symbolTable.get(token.getValue());
                if (symbol == null) {
                    throw new SemanticException("Undefined variable: " + token.getValue(), token.getLine(), token.getColumn());
                }
                if (!symbol.isInitialized()) {
                    throw new SemanticException("Uninitialized variable: " + token.getValue(), token.getLine(), token.getColumn());
                }
            }
        }

        formatOutputExpression(outputTokens);
    }

    private List<Token> collectOutputTokens(TokenAux tokenAux) {
        List<Token> tokens = new ArrayList<>();
        while (!tokenAux.isAtEnd() && !tokenAux.isType(TokenType.RPAREN)) {
            Token token = tokenAux.peek();
            if (isOutputToken(token)) {
                tokens.add(token);
            }
            tokenAux.advance();
        }
        return tokens;
    }

    private void formatOutputExpression(List<Token> tokens) {
        List<Token> typedTokens = new ArrayList<>();

        for (Token token : tokens) {
            if (token.getType() == TokenType.ID) {
                Symbol symbol = symbolTable.get(token.getValue());
                if (symbol == null) {
                    throw new SemanticException("Undefined variable: " + token.getValue());
                }
                typedTokens.add(token);
            } else {
                typedTokens.add(token);
            }
        }

        validateOutputExpressionTypes(typedTokens);
    }

    private void validateOutputExpressionTypes(List<Token> tokens) {
        for (int i = 0; i < tokens.size() - 2; i++) {
            Token leftToken = tokens.get(i);
            Token operatorToken = tokens.get(i + 1);
            Token rightToken = tokens.get(i + 2);

            if (isComparisonOperator(operatorToken)) {
                TokenType leftType = getTokenType(leftToken);
                TokenType rightType = getTokenType(rightToken);

                if (isTypeIncompatible(leftType, rightType)) {
                    throw new SemanticException(String.format("Type mismatch: Cannot compare %s with %s", leftType, rightType));
                }
            }
        }
    }

    private boolean isComparisonOperator(Token token) {
        return token.getValue().equals("==") ||
                token.getValue().equals("!=") ||
                token.getValue().equals(">") ||
                token.getValue().equals("<") ||
                token.getValue().equals(">=") ||
                token.getValue().equals("<=");
    }

    private TokenType getTokenType(Token token) {
        if (token.getType() == TokenType.ID) {
            Symbol symbol = symbolTable.get(token.getValue());
            return symbol != null ? symbol.getType() : null;
        }
        return convertTokenToType(token.getType());
    }

    private TokenType convertTokenToType(TokenType tokenType) {
        return switch (tokenType) {
            case NUM_INT -> TokenType.INTEIRO;
            case NUM_DEC -> TokenType.DECIMAL;
            case VERDADEIRO, FALSO -> TokenType.BOOL;
            default -> tokenType;
        };
    }

    private boolean isTypeIncompatible(TokenType targetType, TokenType sourceType) {
        if (targetType == null || sourceType == null) {
            return true;
        }

        if (targetType == TokenType.DECIMAL && sourceType == TokenType.INTEIRO) {
            return false;
        }

        if(targetType == TokenType.BOOL && sourceType != TokenType.TEXTO) {
            return false;
        }

        return targetType != sourceType;
    }

    private boolean isOutputToken(Token token) {
        return switch (token.getType()) {
            case STRING, ID, NUM_INT, NUM_DEC, VERDADEIRO, FALSO, E, OU, NAO, EQ, NEQ -> true;
            default -> false;
        };
    }

    private void analyzeAssignment(TokenAux tokenAux) {
        Token variableToken = tokenAux.peekPrevious();
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
        TokenType tokenType = determineValueType(token.getValue());
        switch (tokenType) {
            case ID -> validateIdentifierToken(tokenAux, token, targetSymbol);
            case STRING -> validateStringToken(tokenAux, targetSymbol);
            case INTEIRO -> validateIntegerToken(tokenAux, targetSymbol);
            case DECIMAL -> validateDecimalToken(tokenAux, targetSymbol);
            case BOOL -> validateBooleanToken(tokenAux, targetSymbol);
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

    private void validateBooleanToken(TokenAux tokenAux, Symbol targetSymbol) {
        if (targetSymbol.getType() != TokenType.BOOL) {
            throw new SemanticException("Invalid assignment: Cannot assign boolean to " + targetSymbol.getType(), tokenAux.peek().getLine(), tokenAux.peek().getColumn());
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

    private boolean isNextAssignment(TokenAux tokenAux) {
        if(tokenAux.peekNext().getType() == TokenType.ID) {
            return tokenAux.peekTwoAhead().getType() == TokenType.ASSIGN;
        }
        return false;
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

        int lineInitialization = tokenAux.peek().getLine();
        int columnInitialization = tokenAux.peek().getColumn();
        String initialization = extractForLoopSegment(tokenAux, TokenType.SEMICOLON);
        validateVariable(initialization, true, lineInitialization, columnInitialization);

        int lineCondition = tokenAux.peek().getLine();
        int columnCondition = tokenAux.peek().getColumn();
        String condition = extractForLoopSegment(tokenAux, TokenType.SEMICOLON);
        validateVariable(condition,false, lineCondition, columnCondition);

        int lineIncrement = tokenAux.peek().getLine();
        int columnIncrement = tokenAux.peek().getColumn();
        String increment = extractForLoopSegment(tokenAux, TokenType.RPAREN);
        validateVariable(increment,true, lineIncrement, columnIncrement);

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

    private void validateVariable(String segment, boolean isInitialization, int line, int column) {
        String[] parts = segment.split(isInitialization ? "=" : " ");

        String variableName = parts[0].trim();
        String value = isInitialization ? parts[1].trim() : parts[2].trim();

        Symbol symbol = symbolTable.get(variableName);
        if (symbol == null) {
            throw new SemanticException("Undefined variable: " + variableName);
        }

        TokenType expectedType = symbol.getType();
        TokenType actualType = determineValueType(value);

        if (isInitialization) {
            if (isTypeIncompatible(expectedType, actualType)) {
                throw new SemanticException("Type mismatch: Cannot assign " + actualType + " to " + expectedType + " at line:", line, column);
            }
            symbol.setInitialized(true);
        } else {
            if (isTypeIncompatible(expectedType, actualType)) {
                throw new SemanticException(String.format("Type mismatch: Cannot compare %s with %s at line: %d, column: %d", expectedType, actualType, line, column));
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

            if (token.getType() == TokenType.ID) {
                Symbol symbol = symbolTable.get(token.getValue());
                if (symbol == null) {
                    throw new SemanticException("Undefined variable: " + token.getValue());
                }

                if (!symbol.isInitialized()) {
                    throw new SemanticException("Uninitialized variable used in condition: " + token.getValue(), token.getLine(), token.getColumn());
                }
            } else if (isComparisonOperator(token)) {
                TokenType leftType = getTokenType(tokenAux.peekPrevious());
                TokenType rightType = getTokenType(tokenAux.peekNext());

                if (isTypeIncompatible(leftType, rightType)) {
                    throw new SemanticException(String.format("Type mismatch: Cannot compare %s with %s at line: %d, column: %d", leftType, rightType, token.getLine(), token.getColumn()));
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

    private TokenType determineValueType(String value) {
        if (symbolTable.containsKey(value)) {
            return symbolTable.get(value).getType();
        }

        if (value.startsWith("\"") && value.endsWith("\"")) {
            return TokenType.STRING;
        }

        if (value.equals("VERDADEIRO") || value.equals("FALSO") || value.contains(">") || value.contains("<")
                || value.contains("<=") || value.contains("=>") || value.contains("=") || value.contains("!=")) {
            return TokenType.BOOL;
        }

        if(value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/") || value.contains("(") || value.contains(")")) {
            if(value.contains(".")){
                return TokenType.DECIMAL;
            }
            return TokenType.INTEIRO;
        }

        if (isDecimal(value)) {
            return TokenType.DECIMAL;
        }

        if (isInteger(value)) {
            return TokenType.INTEIRO;
        }

        return TokenType.STRING;
    }

    private boolean isInteger(String value) {
        return value.matches("[-+]?\\d+");
    }

    private boolean isDecimal(String value) {
        return value.matches("[-+]?\\d+\\.\\d+");
    }
}