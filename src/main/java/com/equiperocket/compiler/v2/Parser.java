package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.LexicalException;
import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;
import com.equiperocket.compiler.v2.model.TokenType;
import com.equiperocket.compiler.v2.util.ParserAux;
import com.equiperocket.compiler.v2.validation.ParserValidator;

import java.util.List;
import java.util.Map;

public class Parser extends ParserAux {

    private Map<String, Symbol> symbolTable;
    private ParserValidator validator;

    public Parser(List<Token> tokens, Map<String, Symbol> symbolTable) {
        super(tokens);
        this.symbolTable = symbolTable;
        validator = new ParserValidator(this);
    }

    public void parse() {
        program();
    }

    private void program() {
        matchReq(TokenType.PROG);
        declarations();
        commands();
        matchReq(TokenType.END_PROG);
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
            String idName = peek().getValue();
            matchReq(TokenType.ID);
            Symbol symbol = symbolTable.get(idName);
            symbol.setType(type);
            symbolTable.put(idName, symbol);
        } while (match(TokenType.COMMA));
    }

    private TokenType type() {
        TokenType type = peek().getType();

        if (!match(TokenType.INTEIRO) &&
                !match(TokenType.DECIMAL) &&
                !match(TokenType.TEXTO) &&
                !match(TokenType.BOOL)
        ) {
            throw new LexicalException("Invalid type");
        }
        return type;
    }

    private void commands() {
        while (validator.checkCommand()) {
            command();
        }
    }

    private void command() {
        if (match(TokenType.LEIA)) {
            readInput();
        } else if (match(TokenType.ESCREVA)) {
            writeOutput();
        } else if (check(TokenType.ID)) {
            attribution();
        } else if (match(TokenType.IF)) {
            ifStmt();
        } else if (match(TokenType.WHILE)) {
            whileStmt();
        } else if (match(TokenType.FOR)) {
            forStmt();
        } else {
            throw new LexicalException("Invalid command");
        }
    }

    private void readInput() {
        matchReq(TokenType.LPAREN);
        matchReq(TokenType.ID);
        matchReq(TokenType.RPAREN);
    }

    private void writeOutput() {
        matchReq(TokenType.LPAREN);

        do {
            if (validator.checkBoolExpr()) {
                boolExpr();
            } else if (!match(TokenType.STRING)) {
                expr();
            }
        } while (match(TokenType.PLUS));

        matchReq(TokenType.RPAREN);
    }

    private void attribution() {
        matchReq(TokenType.ID);
        matchReq(TokenType.ASSIGN);

        if (validator.checkBoolExpr()) {
            boolExpr();
        } else if (!match(TokenType.STRING)) {
            expr();
        }
    }

    private void ifStmt() {
        do {
            matchReq(TokenType.LPAREN);
            condition();
            matchReq(TokenType.RPAREN);
            block();
        } while (match(TokenType.ELIF));

        if (match(TokenType.ELSE)) {
            block();
        }
    }

    private void whileStmt() {
        matchReq(TokenType.LPAREN);
        condition();
        matchReq(TokenType.RPAREN);
        block();
    }

    private void forStmt() {
        matchReq(TokenType.LPAREN);
        attribution();
        matchReq(TokenType.SEMICOLON);
        condition();

        if (match(TokenType.SEMICOLON)) {
            attribution();
        }

        matchReq(TokenType.RPAREN);
        block();
    }

    private void block() {
        matchReq(TokenType.LBRACE);
        commands();
        matchReq(TokenType.RBRACE);
    }

    private void condition() {
        boolExpr();
    }

    private void boolExpr() {
        do {
            boolTerm();
        } while (match(TokenType.OU));
    }

    private void boolTerm() {
        do {
            boolFactor();
        } while (match(TokenType.E));
    }

    private void boolFactor() {
        do {
            boolExprBase();
        } while (match(TokenType.EQ) || match(TokenType.NEQ));
    }

    private void boolExprBase() {
        if (!validator.checkBoolExpr()) {
            throw new LexicalException("Invalid bool expression");
        }

        if (match(TokenType.NAO)) {
            boolExpr();
        } else if (match(TokenType.LPAREN)) {
            boolExpr();
            matchReq(TokenType.RPAREN);
        } else if (!match(TokenType.BOOL) && validator.checkNextRelOp()) {
            relExpr();
        }
    }

    private void relExpr() {
        expr();
        relOp();
        expr();
    }

    private void relOp() {
        if (!match(TokenType.LESS) &&
                !match(TokenType.GREATER) &&
                !match(TokenType.LEQ) &&
                !match(TokenType.GEQ) &&
                !match(TokenType.EQ) &&
                !match(TokenType.NEQ)
        ) {
            throw new LexicalException("Invalid relational operator");
        }
    }

    private void expr() {
        do {
            term();
        } while (match(TokenType.PLUS) || match(TokenType.MINUS));
    }

    private void term() {
        do {
            factor();
        } while (match(TokenType.MULT) || match(TokenType.DIV));
    }

    private void factor() {
        if (!validator.checkExpr()) {
            throw new LexicalException("Invalid expression");
        }

        if (match(TokenType.LPAREN) ||
                !match(TokenType.NUM_INT) &&
                        !match(TokenType.NUM_DEC) &&
                        !match(TokenType.ID)) {
            expr();
            matchReq(TokenType.RPAREN);
        }
    }
}
