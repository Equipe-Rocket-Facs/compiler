package com.equiperocket.compiler;

import com.equiperocket.compiler.exception.LexicalException;
import com.equiperocket.compiler.exception.SyntaxException;
import com.equiperocket.compiler.listener.LexerErrorListener;
import com.equiperocket.compiler.listener.ParserErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CompilerApplication {

    public static void main(String[] args) {

        // Verifica se o arquivo foi passado como argumento
        if (args.length != 1) {
            System.err.println("Erro: Um arquivo deve ser passado como argumento.");
            System.exit(1);
        }

        try {
            // Extrai o código fonte do arquivo passado como argumento
            String sourceCode = new String(Files.readAllBytes(Paths.get(args[0])));

            // Cria um lexer para analisar o código fonte e substitui os listeners de erro padrão
            MyLanguageLexer lexer = new MyLanguageLexer(CharStreams.fromString(sourceCode));
            BaseErrorListener errorListener = new LexerErrorListener();
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);

            // Cria um fluxo de tokens a partir do lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Cria um parser para interpretar os tokens e substitui os listeners de erro padrão
            MyLanguageParser parser = new MyLanguageParser(tokens);
            errorListener = new ParserErrorListener();
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            // Analisa o código fonte e gera uma árvore de parse
            MyLanguageParser.ProgContext tree = parser.prog();

            // Gera o código Java enquanto percorre a árvore de parse com o listener
            MyLanguageToJava listener = new MyLanguageToJava();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);

            // Obtém o código java gerado
            String javaCode = listener.generateJavaCode();

            // Cria ou sobscreve a saída com o código java gerado
            Files.write(Paths.get("Main.java"), javaCode.getBytes());

            System.out.println("Código Java gerado com sucesso em Main.java");
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (LexicalException e) {
            System.err.println("Erro léxico: " + e.getMessage());
        } catch (SyntaxException e) {
            System.err.println("Erro sintático: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro semântico: " + e.getMessage());
        }
    }
}
