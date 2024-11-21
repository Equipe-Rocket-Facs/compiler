package com.equiperocket.compiler.v2;

import com.equiperocket.compiler.v2.exception.LexicalException;
import com.equiperocket.compiler.v2.exception.SemanticException;
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
            String sourceCode = new String(Files.readAllBytes(Paths.get("src/main/resources/tests/Case1.txt")));

            Map<String, Symbol> symbolTable = new HashMap<>();
            Lexer lexer = new Lexer(sourceCode, symbolTable);

            List<Token> tokens = lexer.tokenize();
//            tokens.forEach(System.out::println);

            Parser parser = new Parser(tokens, symbolTable);
            parser.parse();

            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(symbolTable, tokens);
            semanticAnalyzer.analyze();

            CodeGenerator codeGenerator = new CodeGenerator(symbolTable, tokens);
            String javaCode = codeGenerator.generate();

            Files.write(Paths.get("Main.java"), javaCode.getBytes());

            System.out.println("Java code successfully generated in Main.java");
        } catch (IOException e) {
            System.err.println("I/O error:\n" + e.getMessage());
        } catch (LexicalException e) {
            System.err.println("Lexical error:\n" + e.getMessage());
        } catch (SyntaxException e) {
            System.err.println("Syntactic error:\n" + e.getMessage());
        } catch (SemanticException e) {
            System.err.println("Semantic error:\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error:\n" + e.getMessage());
        }
    }
}
