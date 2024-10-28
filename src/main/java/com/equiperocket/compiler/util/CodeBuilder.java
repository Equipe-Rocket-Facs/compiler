package com.equiperocket.compiler.util;


import com.equiperocket.compiler.constants.JavaConstants;

public class CodeBuilder {
    private final StringBuilder code;
    private int indentationLevel;
    private boolean needsIndentation;

    public CodeBuilder() {
        this.code = new StringBuilder();
        this.indentationLevel = 0;
        this.needsIndentation = true;
    }

    public void appendClassHeader() {
        code.append(JavaConstants.CLASS_HEADER);
        code.append(JavaConstants.MAIN_METHOD_HEADER);
        indentationLevel += 2;
    }

    public void appendClassFooter() {
        indentationLevel -= 2;
        code.append(JavaConstants.CLOSE_BLOCK);
        code.append(JavaConstants.CLOSE_BLOCK);
    }

    public void append(String text) {
        if (needsIndentation) {
            code.append(getIndentation());
            needsIndentation = false;
        }
        code.append(text);
    }

    public void appendLine(String text) {
        if (needsIndentation) {
            code.append(getIndentation());
        }
        code.append(text.trim()).append("\n");
        needsIndentation = true;
    }

    public void increaseIndentation() {
        indentationLevel++;
    }

    public void decreaseIndentation() {
        indentationLevel--;
    }

    private String getIndentation() {
        return "    ".repeat(Math.max(0, indentationLevel));
    }

    public String build() {
        return code.toString();
    }
}