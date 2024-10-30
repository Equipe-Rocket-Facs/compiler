package com.equiperocket.compiler;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CompilerApplication {

    // TODO: posição do caractere na linha em mensagens de erro pode estar errada
    public static void main(String[] args) {

        // Verifica se o arquivo foi passado como argumento
        if (args.length != 1) {
            System.err.println("Erro: Um arquivo deve ser passado como argumento.");
            System.exit(1);
        }

        try {
            // Extrai o código fonte do arquivo passado como argumento
            String sourceCode = new String(Files.readAllBytes(Paths.get(args[0])));

            // Faz a análise léxica
            MyLanguageLexer lexer = new MyLanguageLexer(CharStreams.fromString(sourceCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MyLanguageParser parser = new MyLanguageParser(tokens);

            // Substitui o ouvinte padrão para erros de sintaxe
            parser.removeErrorListeners();
            parser.addErrorListener(new SyntaxErrorListener());

            // Faz a análise sintática
            MyLanguageParser.ProgContext tree = parser.prog();

            // Gera o código Java
            MyLanguageToJava listener = new MyLanguageToJava();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);

            // Obtém o código java gerado
            String javaCode = listener.generateJavaCode();

            // Cria ou sobscreve a saída com o código java gerado
            Files.write(Paths.get("Output.java"), javaCode.getBytes());

            System.out.println("Código Java gerado com sucesso em Output.java");
        } catch (IOException e) {
            System.err.println("Erro ao ler/escrever arquivo: " + e.getMessage());
        } catch (RecognitionException e) {
            System.err.println("Erro de sintaxe: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
