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
        validator = new TokenValidator(tokenAux, symbolTable);
    }

    public void parse() {
        program();
    }

    private void program() {
        tokenAux.matchReq(TokenType.PROG);
        declarations();
        commands();
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
            symbolTable.put(idName, symbol);
        } while (tokenAux.match(TokenType.COMMA));
    }

    private TokenType type() {
        TokenType type = tokenAux.peek().getType();
        tokenAux.match(type);
        return type;
    }

    private void commands() {
        while (validator.checkCommand()) {
            command();
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
            if (validator.checkBoolExpr()) {
                boolExpr();
            } else if (validator.checkExpr()) {
                expr(true);
            } else if (tokenAux.check(TokenType.STRING)) {
                tokenAux.match(TokenType.STRING);
            }
        } while (tokenAux.match(TokenType.PLUS));

        tokenAux.matchReq(TokenType.RPAREN);
    }

    private void attribution() {
        tokenAux.matchReq(TokenType.ID);
        tokenAux.matchReq(TokenType.ASSIGN);

        if (validator.checkBoolExpr()) {
            boolExpr();
        } else if (validator.checkExpr()) {
            expr(false);
        } else if (tokenAux.check(TokenType.STRING)) {
            tokenAux.match(TokenType.STRING);
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
        commands();
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

    private void boolExprBase() {
        if (!validator.checkBoolExpr()) {
            error("Invalid bool expression");
        }

        if (tokenAux.match(TokenType.NAO)) {
            boolExpr();
        } else if (tokenAux.match(TokenType.LPAREN)) {
            boolExpr();
            tokenAux.matchReq(TokenType.RPAREN);
        } else if (!tokenAux.match(TokenType.VERDADEIRO) && !tokenAux.match(TokenType.FALSO)) {
            // O boolExpr pode ser somente um ID ou entao uma relExpr
            expr(false);

            if (validator.checkRelExpr()) {
                relOp();
                expr(false);
            }
        }
    }

    private void relOp() {
        TokenType operator = tokenAux.peek().getType();
        tokenAux.match(operator);
    }

    private void expr(boolean writeCalling) {
        do {
            term();
            if (writeCalling) return;
        } while (tokenAux.match(TokenType.PLUS) || tokenAux.match(TokenType.MINUS));
    }

    private void term() {
        do {
            factor();
        } while (tokenAux.match(TokenType.MULT) || tokenAux.match(TokenType.DIV));
    }

    private void factor() {
        if (!validator.checkExpr()) {
            error("Invalid expression");
        }

        // Tenta consumir o '(' primeiro, depois tenta com numeros e id
        if (tokenAux.match(TokenType.LPAREN) ||
                !tokenAux.match(TokenType.NUM_INT) &&
                        !tokenAux.match(TokenType.NUM_DEC) &&
                        !tokenAux.match(TokenType.ID)) {
            expr(false);
            tokenAux.matchReq(TokenType.RPAREN);
        }
    }

    private void error(String msg) {
        throw new SyntaxException(msg, tokenAux.peek().getLine(), tokenAux.peek().getColumn());
    }
}
