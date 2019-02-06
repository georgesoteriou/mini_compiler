package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor


class WaccVisitor : WaccParserBaseVisitor<Void?>() {

    var currentScope : Scope = Scope(null)
    var functions: ArrayList<Function> = ArrayList()

    override fun visitExpr(ctx: WaccParser.ExprContext?): Void? {
        return super.visitExpr(ctx)
    }

    override fun visitBinaryOperPres1(ctx: WaccParser.BinaryOperPres1Context?): Void? {
        return super.visitBinaryOperPres1(ctx)
    }

    override fun visitBinaryOperPres2(ctx: WaccParser.BinaryOperPres2Context?): Void? {
        return super.visitBinaryOperPres2(ctx)
    }

    override fun visitBinaryOperPres3(ctx: WaccParser.BinaryOperPres3Context?): Void? {
        return super.visitBinaryOperPres3(ctx)
    }

    override fun visitBinaryOperPres4(ctx: WaccParser.BinaryOperPres4Context?): Void? {
        return super.visitBinaryOperPres4(ctx)
    }

    override fun visitBinaryOperPres5(ctx: WaccParser.BinaryOperPres5Context?): Void? {
        return super.visitBinaryOperPres5(ctx)
    }

    override fun visitBinaryOperPres6(ctx: WaccParser.BinaryOperPres6Context?): Void? {
        return super.visitBinaryOperPres6(ctx)
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