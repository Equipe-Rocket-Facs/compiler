package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;

import java.util.*;
import java.util.stream.Collectors;

public class CodeGenerator {
    private final Map<String, Symbol> symbolTable;
    private final StringBuilder generatedCode;
    private final List<Token> tokens;


    public CodeGenerator(Map<String, Symbol> symbolTable, List<Token> tokens) {
        this.symbolTable = symbolTable;
        this.tokens = tokens;
        this.generatedCode = new StringBuilder();
    }

    public String generate() {
        TokenAux tokenAux = new TokenAux(tokens);
        prepareCodeStructure(tokens);
        processTokens(tokenAux);
        closeCodeStructure();
        return generatedCode.toString();
    }

    private void prepareCodeStructure(List<Token> tokens) {
        generatedCode.append("public class Program {\n");
        generatedCode.append("public static void main(String[] args) {\n");
        generateVariableDeclarations();
        addImportsIfNeeded(tokens);
    }

    private void closeCodeStructure() {
        generatedCode.append("}\n");
        generatedCode.append("}\n");
    }

    /**
     * Adiciona imports dinâmicos baseado nos tokens do programa. Por exemplo, se houver leitura de input, adiciona Scanner.
     *
     * @param tokens Lista de tokens para análise
     */
    private void addImportsIfNeeded(List<Token> tokens) {
        if (hasReadInputTokens(tokens)) {
            generatedCode.insert(0, "import java.util.Scanner;\n");
            generatedCode.append("Scanner scanner = new Scanner(System.in);\n");
        }
    }

    private boolean hasReadInputTokens(List<Token> tokens) {
        return tokens.stream()
                .anyMatch(token -> token.getType() == TokenType.LEIA);
    }

    /**
     * Gera declarações de variáveis com seus tipos correspondentes em Java e inicializa todas as variáveis como não inicializadas no símbolo.
     */
    private void generateVariableDeclarations() {
        Map<TokenType, List<String>> groupedVariables = new HashMap<>();

        symbolTable.forEach((varName, symbol) -> {
            groupedVariables.computeIfAbsent(symbol.getType(), k -> new ArrayList<>()).add(varName);
        });

        groupedVariables.forEach((type, varNames) -> {
            String declaration = generateVariableDeclaration(varNames, type);
            generatedCode.append(declaration);
        });
    }

    private String generateVariableDeclaration(List<String> varNames, TokenType type) {
        String javaType = convertToJavaType(type);
        String joinedVarNames = String.join(", ", varNames);
        return String.format("%s %s;\n", javaType, joinedVarNames);
    }

    /**
     * Processa tokens sequencialmente, identificando e traduzindo diferentes tipos de comandos para Java.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     */
    private void processTokens(TokenAux tokenAux) {
        while (!tokenAux.isAtEnd()) {
            Token currentToken = tokenAux.peek();
            processToken(tokenAux, currentToken);
        }
    }

    /**
     * Roteia o processamento baseado no tipo de token e usa switch expression para mapear tokens para métodos específicos.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     * @param currentToken Token atual sendo processado
     */
    private void processToken(TokenAux tokenAux, Token currentToken) {
        switch (currentToken.getType()) {
            case LEIA -> processReadInput(tokenAux);
            case ESCREVA -> processWriteOutput(tokenAux);
            case ASSIGN -> processAssignment(tokenAux);
            case FOR -> processForLoop(tokenAux);
            case IF -> processIfStatement(tokenAux);
            case ELIF -> processElseIfStatement(tokenAux);
            case ELSE -> processElseStatement(tokenAux);
            case WHILE -> processWhileLoop(tokenAux);
            default -> tokenAux.advance();
        }
    }

    /**
     * Processa leitura de input, convertendo para chamada de Scanner e marca a variável como inicializada após leitura.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     */
    private void processReadInput(TokenAux tokenAux) {
        tokenAux.match(TokenType.LEIA);
        tokenAux.match(TokenType.LPAREN);
        Token variableToken = tokenAux.peek();
        tokenAux.match(TokenType.ID);

        Symbol symbol = symbolTable.get(variableToken.getValue());

        generatedCode.append(formatInputRead(variableToken, symbol));
        tokenAux.match(TokenType.RPAREN);
    }

    private String formatInputRead(Token variableToken, Symbol symbol) {
        String scanMethod = getScanMethod(symbol.getType());
        return String.format("%s = scanner.%s();\n",
                variableToken.getValue(),
                scanMethod
        );
    }

    /**
     * Processa um comando de saída, convertendo-o em uma chamada para System.out.println em Java.
     * Este método consome os tokens relacionados ao comando ESCREVA, extrai a expressão a ser impressa e gera o código correspondente.
     *
     * @param tokenAux Auxiliar para navegação e gerenciamento de tokens.
     */
    private void processWriteOutput(TokenAux tokenAux) {
        tokenAux.match(TokenType.ESCREVA);
        tokenAux.match(TokenType.LPAREN);

        String outputExpression = extractOutputExpression(tokenAux);
        generatedCode.append(formatOutputStatement(outputExpression));

        tokenAux.match(TokenType.RPAREN);
    }

    private String extractOutputExpression(TokenAux tokenAux) {
        StringBuilder expressionBuilder = new StringBuilder();
        while (!tokenAux.isAtEnd() && !tokenAux.isType(TokenType.RPAREN)) {
            Token token = tokenAux.peek();
            expressionBuilder.append(formatTokenValue(token)).append(" ");
            tokenAux.advance();
        }
        return expressionBuilder.toString().trim();
    }

    private String formatOutputStatement(String expression) {
        return String.format("System.out.println(%s);\n", expression.isEmpty() ? "\"\"" : expression);
    }

    /**
     * Processa atribuições com lógica complexa de verificação. Verifica inicialização de variáveis, converte tokens booleanos
     * e constrói expressão de atribuição.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     */
    private void processAssignment(TokenAux tokenAux) {
        Token variableToken = tokenAux.peekPrevious();
        tokenAux.match(TokenType.ASSIGN);

        String expression = buildExpression(tokenAux);

        generateAssignment(variableToken, expression);
    }

    private String buildExpression(TokenAux tokenAux) {
        StringBuilder expressionBuilder = new StringBuilder();

        while (!isAssignmentComplete(tokenAux)) {
            Token currentToken = tokenAux.peek();

            expressionBuilder.append(formatTokenValue(currentToken)).append(" ");
            tokenAux.advance();
        }

        return expressionBuilder.toString().trim();
    }

    private boolean isAssignmentComplete(TokenAux tokenAux) {
        return tokenAux.isAtEnd() ||
                isBlockTerminatingToken(tokenAux.peek()) ||
                isNextAssignment(tokenAux);
    }

    private void generateAssignment(Token variableToken, String expression) {
        if (!expression.isEmpty()) {
            generatedCode.append(variableToken.getValue())
                    .append(" = ")
                    .append(expression)
                    .append(";\n");
            markVariableAsInitialized(variableToken);
        }
    }

    private String formatTokenValue(Token token) {
        return (token.getType() == TokenType.VERDADEIRO || token.getType() == TokenType.FALSO)
                ? convertBooleanToken(token.getValue())
                : token.getValue();
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

    /**
     * Processa loops FOR com lógica de conversão de condições, converte tokens booleanos na condição do loop.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     */
    private void processForLoop(TokenAux tokenAux) {
        tokenAux.match(TokenType.FOR);
        tokenAux.match(TokenType.LPAREN);

        ForLoopParts parts = extractForLoopParts(tokenAux);

        generatedCode.append(formatForLoop(parts));
        processBlock(tokenAux);
        generatedCode.append("}\n");
    }

    private record ForLoopParts(String initialization, String condition, String increment) {}

    private ForLoopParts extractForLoopParts(TokenAux tokenAux) {
        return new ForLoopParts(
                processForSegment(tokenAux, TokenType.SEMICOLON),
                processForSegment(tokenAux, TokenType.SEMICOLON),
                processForSegment(tokenAux, TokenType.RPAREN)
        );
    }

    private String formatForLoop(ForLoopParts parts) {
        return String.format("for (%s; %s; %s) {\n",
                parts.initialization(),
                transformBooleanTokens(parts.condition()),
                parts.increment()
        );
    }

    private String transformBooleanTokens(String condition) {
        return Arrays.stream(condition.split("\\s+"))
                .map(this::convertBooleanToken)
                .collect(Collectors.joining(" "));
    }

    /**
     * Processa um segmento de um loop FOR (inicialização, condição ou incremento). Extrai tokens até encontrar um terminador específico
     * e permite processamento flexível de diferentes partes do loop.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     * @param terminator Token que indica o fim do segmento
     * @return Segmento processado como string
     */
    private String processForSegment(TokenAux tokenAux, TokenType terminator) {
        StringBuilder segmentBuilder = new StringBuilder();
        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != terminator) {
            segmentBuilder.append(tokenAux.peek().getValue()).append(" ");
            tokenAux.advance();
        }
        tokenAux.match(terminator);
        return segmentBuilder.toString().trim();
    }


    /**
     * Processa uma instrução IF, gerando o código correspondente em Java.
     * Este método consome os tokens relacionados à instrução IF,
     * extrai a expressão de condição e gera o bloco de código associado.
     *
     * @param tokenAux Auxiliar para navegação e gerenciamento de tokens.
     */
    private void processIfStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.IF);
        tokenAux.match(TokenType.LPAREN);

        String conditionExpression = generateConditionExpression(tokenAux);
        generatedCode.append("if (").append(conditionExpression).append(") {\n");
        processBlock(tokenAux);
        generatedCode.append("}\n");
    }

    /**
     * Processa uma instrução ELSE IF, gerando o código correspondente em Java.
     *
     * @param tokenAux Auxiliar para navegação e gerenciamento de tokens.
     */
    private void processElseIfStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.ELIF);
        tokenAux.match(TokenType.LPAREN);

        String conditionExpression = generateConditionExpression(tokenAux);
        generatedCode.append("else if (").append(conditionExpression).append(") {\n");
        processBlock(tokenAux);
        generatedCode.append("}\n");
    }

    /**
     * Processa uma instrução ELSE, gerando o código correspondente em Java.
     *
     * @param tokenAux Auxiliar para navegação e gerenciamento de tokens.
     */
    private void processElseStatement(TokenAux tokenAux) {
        tokenAux.match(TokenType.ELSE);
        generatedCode.append("else {\n");
        processBlock(tokenAux);
        generatedCode.append("}\n");
    }

    /**
     * Processa um loop WHILE, gerando o código correspondente em Java.
     *
     * @param tokenAux Auxiliar para navegação e gerenciamento de tokens.
     */
    private void processWhileLoop(TokenAux tokenAux) {
        tokenAux.match(TokenType.WHILE);
        tokenAux.match(TokenType.LPAREN);

        String conditionExpression = generateConditionExpression(tokenAux);
        generatedCode.append("while (").append(conditionExpression).append(") {\n");
        processBlock(tokenAux);
        generatedCode.append("}\n");
    }

    /**
     * Gera expressões de condição para estruturas de controle como IF e WHILE.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     * @return Expressão de condição como string
     */
    private String generateConditionExpression(TokenAux tokenAux) {
        StringBuilder conditionBuilder = new StringBuilder();
        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != TokenType.RPAREN) {
            Token token = tokenAux.peek();
            String tokenValue = (isToken(token))
                    ? convertBooleanToken(token.getValue())
                    : token.getValue();

            conditionBuilder.append(tokenValue).append(" ");
            tokenAux.advance();
        }
        tokenAux.match(TokenType.RPAREN);
        return conditionBuilder.toString().trim();
    }

    private boolean isToken(Token token) {
        return token.getType() == TokenType.VERDADEIRO ||
                token.getType() == TokenType.FALSO ||
                token.getType() == TokenType.E ||
                token.getType() == TokenType.NAO ||
                token.getType() == TokenType.OU;
    }

    /**
     * Processa um bloco de código, permitindo a execução de múltiplas instruções.
     *
     * @param tokenAux Auxiliar de navegação de tokens
     */
    private void processBlock(TokenAux tokenAux) {
        while (!tokenAux.isAtEnd() && tokenAux.peek().getType() != TokenType.RBRACE) {
            processToken(tokenAux, tokenAux.peek());
        }
        tokenAux.match(TokenType.RBRACE);
    }

    private String convertBooleanToken(String token) {
        return switch (token) {
            case "VERDADEIRO" -> "true";
            case "FALSO" -> "false";
            case "E" -> "&&";
            case "OU" -> "||";
            case "NAO" -> "!";
            default -> token;
        };
    }

    private String convertToJavaType(TokenType type) {
        return switch (type) {
            case INTEIRO -> "int";
            case DECIMAL -> "double";
            case BOOL -> "boolean";
            default -> "String";
        };
    }

    private String getScanMethod(TokenType type) {
        return switch (type) {
            case INTEIRO -> "nextInt";
            case DECIMAL -> "nextDouble";
            case BOOL -> "nextBoolean";
            default -> "nextLine";
        };
    }
}