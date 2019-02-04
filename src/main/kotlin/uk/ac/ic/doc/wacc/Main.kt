package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserVisitor

fun main(args : Array<String>) {

    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String) = WaccParser(tokenStream(resourceName)).prog()


    val visitor = WaccVisitor()
    parseResource(args[0]).accept(visitor)

}

class WaccVisitor: WaccParserVisitor<Void> {
    override fun visitExpr(ctx: WaccParser.ExprContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitBinaryOper(ctx: WaccParser.BinaryOperContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitUnaryOper(ctx: WaccParser.UnaryOperContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitAssign_lhs(ctx: WaccParser.Assign_lhsContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitAssign_rhs(ctx: WaccParser.Assign_rhsContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArg_list(ctx: WaccParser.Arg_listContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitParam(ctx: WaccParser.ParamContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitParam_list(ctx: WaccParser.Param_listContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitStat(ctx: WaccParser.StatContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitBase_type(ctx: WaccParser.Base_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitType(ctx: WaccParser.TypeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_elem(ctx: WaccParser.Array_elemContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_type(ctx: WaccParser.Array_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitArray_liter(ctx: WaccParser.Array_literContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitPair_elem_type(ctx: WaccParser.Pair_elem_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitPair_elem(ctx: WaccParser.Pair_elemContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitFunc(ctx: WaccParser.FuncContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitProg(ctx: WaccParser.ProgContext?): Void {
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

    override fun visitPair_type(ctx: WaccParser.Pair_typeContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitChildren(node: RuleNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}