package com.equiperocket.compiler.util;

import static com.equiperocket.compiler.constants.JavaConstants.*;

public class CodeBuilder {

    private final StringBuilder code;
    private int indentationLevel;
    private boolean isIndented;

    public CodeBuilder() {
        this.code = new StringBuilder();
        this.indentationLevel = 0;
        this.isIndented = true;
    }

    public void appendClassHeader() {
        appendLine(CLASS_HEADER);
        increaseIndentation();

        appendLine(MAIN_METHOD_HEADER);
        increaseIndentation();
    }

    public void appendClassFooter() {
        if (indentationLevel != 0) {
            decreaseIndentation();

            appendLine(CLOSE_BLOCK);

            appendClassFooter();
        }
    }

    public void appendScanner() {
        appendLine(SCANNER_INIT);
    }

    public CodeBuilder append(String text) {
        if (!isIndented) {
            code.append(getIndentation());
            isIndented = true;
        }

        code.append(text);
        return this;
    }

    public void appendLine(String text) {
        if (!isIndented) {
            code.append(getIndentation());
        }

        code.append(text.trim()).append("\n");
        isIndented = false;
    }

    private String getIndentation() {
        return TAB.repeat(Math.max(0, indentationLevel));
    }

    public void increaseIndentation() {
        indentationLevel++;
    }

    public void decreaseIndentation() {
        indentationLevel--;
    }

    public String build() {
        return code.toString();
    }
}
