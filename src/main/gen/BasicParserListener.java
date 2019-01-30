// Generated from /homes/bc3717/wacc_11/src/main/antlr/BasicParser.g4 by ANTLR 4.7.2
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
	 * Enter a parse tree produced by {@link BasicParser#unaryOper}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOper(BasicParser.UnaryOperContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#unaryOper}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOper(BasicParser.UnaryOperContext ctx);
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
	 * Enter a parse tree produced by {@link BasicParser#assign_lhs}.
	 * @param ctx the parse tree
	 */
	void enterAssign_lhs(BasicParser.Assign_lhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#assign_lhs}.
	 * @param ctx the parse tree
	 */
	void exitAssign_lhs(BasicParser.Assign_lhsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#assign_rhs}.
	 * @param ctx the parse tree
	 */
	void enterAssign_rhs(BasicParser.Assign_rhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#assign_rhs}.
	 * @param ctx the parse tree
	 */
	void exitAssign_rhs(BasicParser.Assign_rhsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void enterArg_list(BasicParser.Arg_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void exitArg_list(BasicParser.Arg_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(BasicParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(BasicParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(BasicParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(BasicParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(BasicParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(BasicParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#base_type}.
	 * @param ctx the parse tree
	 */
	void enterBase_type(BasicParser.Base_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#base_type}.
	 * @param ctx the parse tree
	 */
	void exitBase_type(BasicParser.Base_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(BasicParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(BasicParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#array_elem}.
	 * @param ctx the parse tree
	 */
	void enterArray_elem(BasicParser.Array_elemContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#array_elem}.
	 * @param ctx the parse tree
	 */
	void exitArray_elem(BasicParser.Array_elemContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#array_type}.
	 * @param ctx the parse tree
	 */
	void enterArray_type(BasicParser.Array_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#array_type}.
	 * @param ctx the parse tree
	 */
	void exitArray_type(BasicParser.Array_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#array_liter}.
	 * @param ctx the parse tree
	 */
	void enterArray_liter(BasicParser.Array_literContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#array_liter}.
	 * @param ctx the parse tree
	 */
	void exitArray_liter(BasicParser.Array_literContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#pair_type}.
	 * @param ctx the parse tree
	 */
	void enterPair_type(BasicParser.Pair_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pair_type}.
	 * @param ctx the parse tree
	 */
	void exitPair_type(BasicParser.Pair_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#pair_elem}.
	 * @param ctx the parse tree
	 */
	void enterPair_elem(BasicParser.Pair_elemContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pair_elem}.
	 * @param ctx the parse tree
	 */
	void exitPair_elem(BasicParser.Pair_elemContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#pair_elem_type}.
	 * @param ctx the parse tree
	 */
	void enterPair_elem_type(BasicParser.Pair_elem_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pair_elem_type}.
	 * @param ctx the parse tree
	 */
	void exitPair_elem_type(BasicParser.Pair_elem_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(BasicParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(BasicParser.FuncContext ctx);
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