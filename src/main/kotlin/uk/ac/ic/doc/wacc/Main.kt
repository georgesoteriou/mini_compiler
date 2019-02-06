package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

fun main(args : Array<String>) {

    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String) = WaccParser(tokenStream(resourceName)).prog()


    val visitor = WaccVisitor()
    parseResource(args[0]).accept(visitor)

}


class WaccVisitor : WaccParserBaseVisitor<Void?>() {


    override fun visitExpr(ctx: WaccParser.ExprContext?): Void? {
        return super.visitExpr(ctx)
    }

    override fun visitBinaryOper(ctx: WaccParser.BinaryOperContext?): Void? {
        return super.visitBinaryOper(ctx)
    }

    override fun visitUnaryOper(ctx: WaccParser.UnaryOperContext?): Void? {
        return super.visitUnaryOper(ctx)
    }

    override fun visitAssign_lhs(ctx: WaccParser.Assign_lhsContext?): Void? {
        return super.visitAssign_lhs(ctx)
    }

    override fun visitAssign_rhs(ctx: WaccParser.Assign_rhsContext?): Void? {
        return super.visitAssign_rhs(ctx)
    }

    override fun visitArg_list(ctx: WaccParser.Arg_listContext?): Void? {
        return super.visitArg_list(ctx)
    }

    override fun visitParam(ctx: WaccParser.ParamContext?): Void? {
        return super.visitParam(ctx)
    }

    override fun visitParam_list(ctx: WaccParser.Param_listContext?): Void? {
        return super.visitParam_list(ctx)
    }

    override fun visitStat(ctx: WaccParser.StatContext?): Void? {
        return super.visitStat(ctx)
    }

    override fun visitBase_type(ctx: WaccParser.Base_typeContext?): Void? {
        return super.visitBase_type(ctx)
    }

    override fun visitType(ctx: WaccParser.TypeContext?): Void? {
        return super.visitType(ctx)
    }

    override fun visitArray_elem(ctx: WaccParser.Array_elemContext?): Void? {
        return super.visitArray_elem(ctx)
    }

    override fun visitArray_type(ctx: WaccParser.Array_typeContext?): Void? {
        return super.visitArray_type(ctx)
    }

    override fun visitArray_liter(ctx: WaccParser.Array_literContext?): Void? {
        return super.visitArray_liter(ctx)
    }

    override fun visitPair_elem_type(ctx: WaccParser.Pair_elem_typeContext?): Void? {
        return super.visitPair_elem_type(ctx)
    }

    override fun visitPair_type(ctx: WaccParser.Pair_typeContext?): Void? {
        return super.visitPair_type(ctx)
    }

    override fun visitPair_elem(ctx: WaccParser.Pair_elemContext?): Void? {
        return super.visitPair_elem(ctx)
    }

    override fun visitFunc(ctx: WaccParser.FuncContext?): Void? {
        return super.visitFunc(ctx)
    }

    override fun visitProg(ctx: WaccParser.ProgContext?): Void? {
        return super.visitProg(ctx)
    }


}