package com.equiperocket.compiler;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SyntaxErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new RecognitionException(
                String.format("Linha %d, posição %d - %s", line, charPositionInLine, msg),
                recognizer,
                null,
                null
        );
    }
}
