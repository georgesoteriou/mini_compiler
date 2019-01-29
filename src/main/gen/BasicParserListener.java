// Generated from /home/gs2617/Documents/imperial/wacc/wacc_11/src/main/antlr/BasicParser.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BasicParser}.
 */
public interface BasicParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BasicParser#binaryOper}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOper(BasicParser.BinaryOperContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#binaryOper}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOper(BasicParser.BinaryOperContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(BasicParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(BasicParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(BasicParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(BasicParser.ProgContext ctx);
}