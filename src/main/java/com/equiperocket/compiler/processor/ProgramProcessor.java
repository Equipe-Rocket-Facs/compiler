package com.equiperocket.compiler.processor;

import com.equiperocket.compiler.MyLanguageParser;
import com.equiperocket.compiler.util.CodeBuilder;
import com.equiperocket.compiler.validation.ProgramValidator;

public class ProgramProcessor {

    private final CodeBuilder codeBuilder;

    public ProgramProcessor(CodeBuilder codeBuilder) {
        this.codeBuilder = codeBuilder;
    }

    public void processBeginProgram(MyLanguageParser.ProgContext ctx) {
        String beginProgramDeclaration = getNodeText(ctx, 0);

        ProgramValidator.checkBegin(beginProgramDeclaration, ctx);

        codeBuilder.appendClassHeader();
    }

    public void processEndProgram(MyLanguageParser.ProgContext ctx) {
        String endProgramDeclaration = getNodeText(ctx, 3);

        ProgramValidator.checkEnd(endProgramDeclaration, ctx);

        codeBuilder.appendClassFooter();
    }

    private String getNodeText(MyLanguageParser.ProgContext ctx, int position) {
        return ctx.getChild(position) != null ? ctx.getChild(position).getText() : null;
    }
}
