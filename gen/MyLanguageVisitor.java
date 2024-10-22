// Generated from /home/diego/repos/java/compiler/src/main/antlr4/com/equiperocket/compiler/MyLanguage.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MyLanguageParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#decls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls(MyLanguageParser.DeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MyLanguageParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MyLanguageParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#declItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclItemList(MyLanguageParser.DeclItemListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#declItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclItem(MyLanguageParser.DeclItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#commands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommands(MyLanguageParser.CommandsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(MyLanguageParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#readInput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadInput(MyLanguageParser.ReadInputContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#writeOutput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteOutput(MyLanguageParser.WriteOutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#attribution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribution(MyLanguageParser.AttributionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MyLanguageParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MyLanguageParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MyLanguageParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MyLanguageParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(MyLanguageParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(MyLanguageParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MyLanguageParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLanguageParser#relOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelOp(MyLanguageParser.RelOpContext ctx);
}