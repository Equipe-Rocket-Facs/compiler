package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.TokenAux;
import com.equiperocket.compiler.v2.validation.TokenValidator;

import java.util.List;
import java.util.Map;

public class Parser {

    private Map<String, Symbol> symbolTable;
    private TokenAux tokenAux;
    private TokenValidator validator;

    public Parser(List<Token> tokens, Map<String, Symbol> symbolTable) {
        this.symbolTable = symbolTable;
        tokenAux = new TokenAux(tokens);
        validator = new TokenValidator(tokenAux);
    }

    public void parse() {
        program();
    }

    private void program() {
        tokenAux.matchReq(TokenType.PROG);
        declarations();
        commands(false);
        tokenAux.matchReq(TokenType.END_PROG);
    }

    private void declarations() {
        while (validator.checkType()) {
            declaration();
        }
    }

    private void declaration() {
        TokenType type = type();
        declarationList(type);
    }

    private void declarationList(TokenType type) {
        do {
            String idName = tokenAux.peek().getValue();
            tokenAux.matchReq(TokenType.ID);
            Symbol symbol = symbolTable.get(idName);
            symbol.setType(type);
            symbol.incrementCount();
            symbolTable.put(idName, symbol);
        } while (tokenAux.match(TokenType.COMMA));
    }

    private TokenType type() {
        TokenType type = tokenAux.peek().getType();
        consumeToken();
        return type;
    }

    private void commands(boolean isBlockCalling) {
        while (validator.checkCommand()) {
            command();
        }

        if (!isBlockCalling && !tokenAux.check(TokenType.END_PROG)) {
            error("Invalid token");
        }
    }

    private void command() {
        if (tokenAux.match(TokenType.LEIA)) {
            readInput();
        } else if (tokenAux.match(TokenType.ESCREVA)) {
            writeOutput();
        } else if (tokenAux.check(TokenType.ID)) {
            attribution();
        } else if (tokenAux.match(TokenType.IF)) {
            ifStmt();
        } else if (tokenAux.match(TokenType.WHILE)) {
            whileStmt();
        } else if (tokenAux.match(TokenType.FOR)) {
            forStmt();
        }
    }

    private void readInput() {
        tokenAux.matchReq(TokenType.LPAREN);
        tokenAux.matchReq(TokenType.ID);
        tokenAux.matchReq(TokenType.RPAREN);
    }

    private void writeOutput() {
        tokenAux.matchReq(TokenType.LPAREN);

        do {
            if (tokenAux.check(TokenType.STRING)) {
                consumeToken();
            } else if (validator.isBoolExpr()) {
                boolExpr();
            } else if (validator.isExpr()) {
                expr(true);
            }
        } while (tokenAux.match(TokenType.PLUS));

        tokenAux.matchReq(TokenType.RPAREN);
    }

    private void attribution() {
        tokenAux.matchReq(TokenType.ID);
        tokenAux.matchReq(TokenType.ASSIGN);

        if (validator.isString()) { // Vindo primeiro economizamos trabalho desnecessario
            consumeToken();
        } else if (validator.isBoolExpr()) {
            boolExpr();
        } else if (validator.isExpr()) {
            expr(false);
        } else {
            // Nao aceitar nada alem de expr, boolExpr ou String
            error("Invalid attribution value");
        }
    }

    private void ifStmt() {
        do {
            tokenAux.matchReq(TokenType.LPAREN);
            condition();
            tokenAux.matchReq(TokenType.RPAREN);
            block();
        } while (tokenAux.match(TokenType.ELIF));

        if (tokenAux.match(TokenType.ELSE)) {
            block();
        }
    }

    private void whileStmt() {
        tokenAux.matchReq(TokenType.LPAREN);
        condition();
        tokenAux.matchReq(TokenType.RPAREN);
        block();
    }

    private void forStmt() {
        tokenAux.matchReq(TokenType.LPAREN);
        attribution();
        tokenAux.matchReq(TokenType.SEMICOLON);
        condition();

        if (tokenAux.match(TokenType.SEMICOLON)) {
            attribution();
        }

        tokenAux.matchReq(TokenType.RPAREN);
        block();
    }

    private void block() {
        tokenAux.matchReq(TokenType.LBRACE);
        commands(true);
        tokenAux.matchReq(TokenType.RBRACE);
    }

    private void condition() {
        boolExpr();
    }

    private void boolExpr() {
        do {
            boolTerm();
        } while (tokenAux.match(TokenType.OU));
    }

    private void boolTerm() {
        do {
            boolFactor();
        } while (tokenAux.match(TokenType.E));
    }

    private void boolFactor() {
        do {
            boolExprBase();
        } while (tokenAux.match(TokenType.EQ) || tokenAux.match(TokenType.NEQ));
    }

    // A checagem para '(' deve vir depois de verificar se temos uma expressao matematica
    // Pois podemos ter um calculo que utilize parentesis mas foi consumido no boolExpr
    private void boolExprBase() {
        if (tokenAux.match(TokenType.NAO)) {
            boolExpr();
        } else if (validator.isExpr()) {
            relExpr();
        } else if (tokenAux.match(TokenType.LPAREN)) {
            boolExpr();
            tokenAux.matchReq(TokenType.RPAREN);
        } else if (validator.isBoolean()) {
            consumeToken();
        } else {
            error("Invalid bool expression");
        }
    }

    // TODO: Esta permitindo relacoes entre booleans e numeros
    private void relExpr() {
        // O boolExpr pode ser somente um ID ou entao uma relExpr
        expr(false);

        if (validator.isRelExpr()) {
            consumeToken(); // Consome o operador

            expr(false);
        }
//        error("Invalid bool expression");
    }

    private void expr(boolean isWriteCalling) {
        do {
            term();
            // Importante para o '+' dentro do escreva nao ser lido como operador matematico
            if (isWriteCalling) return;
        } while (tokenAux.match(TokenType.PLUS) || tokenAux.match(TokenType.MINUS));
    }

    private void term() {
        do {
            factor();
        } while (tokenAux.match(TokenType.MULT) || tokenAux.match(TokenType.DIV));
    }

    private void factor() {
        if (validator.checkExpr()) {
            consumeToken();
        } else if (tokenAux.match(TokenType.LPAREN)) {
            boolExpr(); // O segundo if do boolExpr consome nossa expr (Numero ou ID)

            // O boolExpr pode chamar o expr ao detectar operadores matematicos
            // Isso pode resultar em calculos envolvendo booleans - Explode erro
//            if (validator.checkRelOp()) return;

            tokenAux.matchReq(TokenType.RPAREN);
        } else {
            error("Invalid expression");
        }
    }

    private void consumeToken() {
        TokenType token = tokenAux.peek().getType();
        tokenAux.match(token);
    }

    private void error(String msg) {
        throw new SyntaxException(msg, tokenAux.peek().getLine(), tokenAux.peek().getColumn());
    }
}
