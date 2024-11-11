package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.LexicalException;
import com.equiperocket.compiler.v2.exception.SyntaxException;
import com.equiperocket.compiler.v2.model.Symbol;
import com.equiperocket.compiler.v2.model.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
//            Scanner sc = new Scanner(System.in);
//            System.out.print("Insira o caminho do código fonte: ");
//            String filePath = sc.nextLine();
//
//            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            String sourceCode = new String(Files.readAllBytes(Paths.get("src/main/resources/tests/Case7.txt")));

            Map<String, Symbol> symbolTable = new HashMap<>();
            Lexer lexer = new Lexer(sourceCode, symbolTable);

            List<Token> tokens = lexer.tokenize();
//            tokens.forEach(System.out::println);

            Parser parser = new Parser(tokens, symbolTable);
            parser.parse();

            System.out.println("Deu tudo certo");
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (LexicalException e) {
            System.err.println("Erro léxico: " + e.getMessage());
        } catch (SyntaxException e) {
            System.err.println("Erro sintático: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
