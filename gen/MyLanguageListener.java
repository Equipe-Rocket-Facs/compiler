// Generated from /home/diego/repos/java/compiler/src/main/antlr4/com/equiperocket/compiler/MyLanguage.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyLanguageParser}.
 */
public interface MyLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MyLanguageParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MyLanguageParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#decls}.
	 * @param ctx the parse tree
	 */
	void enterDecls(MyLanguageParser.DeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#decls}.
	 * @param ctx the parse tree
	 */
	void exitDecls(MyLanguageParser.DeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(MyLanguageParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(MyLanguageParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MyLanguageParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MyLanguageParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#declItemList}.
	 * @param ctx the parse tree
	 */
	void enterDeclItemList(MyLanguageParser.DeclItemListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#declItemList}.
	 * @param ctx the parse tree
	 */
	void exitDeclItemList(MyLanguageParser.DeclItemListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#declItem}.
	 * @param ctx the parse tree
	 */
	void enterDeclItem(MyLanguageParser.DeclItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#declItem}.
	 * @param ctx the parse tree
	 */
	void exitDeclItem(MyLanguageParser.DeclItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#commands}.
	 * @param ctx the parse tree
	 */
	void enterCommands(MyLanguageParser.CommandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#commands}.
	 * @param ctx the parse tree
	 */
	void exitCommands(MyLanguageParser.CommandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(MyLanguageParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(MyLanguageParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#readInput}.
	 * @param ctx the parse tree
	 */
	void enterReadInput(MyLanguageParser.ReadInputContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#readInput}.
	 * @param ctx the parse tree
	 */
	void exitReadInput(MyLanguageParser.ReadInputContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#writeOutput}.
	 * @param ctx the parse tree
	 */
	void enterWriteOutput(MyLanguageParser.WriteOutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#writeOutput}.
	 * @param ctx the parse tree
	 */
	void exitWriteOutput(MyLanguageParser.WriteOutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#attribution}.
	 * @param ctx the parse tree
	 */
	void enterAttribution(MyLanguageParser.AttributionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#attribution}.
	 * @param ctx the parse tree
	 */
	void exitAttribution(MyLanguageParser.AttributionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MyLanguageParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MyLanguageParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MyLanguageParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MyLanguageParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MyLanguageParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MyLanguageParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MyLanguageParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MyLanguageParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(MyLanguageParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(MyLanguageParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(MyLanguageParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(MyLanguageParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MyLanguageParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MyLanguageParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLanguageParser#relOp}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(MyLanguageParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLanguageParser#relOp}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(MyLanguageParser.RelOpContext ctx);
}