package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import uk.ac.ic.doc.wacc.grammar.BasicParser
import uk.ac.ic.doc.wacc.grammar.BasicParserVisitor

class TestVisitor : BasicParserVisitor<Void> {
    override fun visitUnaryOper(ctx: BasicParser.UnaryOperContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitExpr(ctx: BasicParser.ExprContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitAssign_lhs(ctx: BasicParser.Assign_lhsContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitAssign_rhs(ctx: BasicParser.Assign_rhsContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArg_list(ctx: BasicParser.Arg_listContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitParam(ctx: BasicParser.ParamContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitParam_list(ctx: BasicParser.Param_listContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitStat(ctx: BasicParser.StatContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitBase_type(ctx: BasicParser.Base_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitType(ctx: BasicParser.TypeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_elem(ctx: BasicParser.Array_elemContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_type(ctx: BasicParser.Array_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_liter(ctx: BasicParser.Array_literContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitPair_elem(ctx: BasicParser.Pair_elemContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitPair_elem_type(ctx: BasicParser.Pair_elem_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitProg(ctx: BasicParser.ProgContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(tree: ParseTree?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitErrorNode(node: ErrorNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitTerminal(node: TerminalNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitPair_type(ctx: BasicParser.Pair_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitFunc(ctx: BasicParser.FuncContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitChildren(node: RuleNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitBinaryOper(ctx: BasicParser.BinaryOperContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}