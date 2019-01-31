package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import uk.ac.ic.doc.wacc.grammar.BasicParser
import uk.ac.ic.doc.wacc.grammar.BasicParserVisitor

class TestVisitor : BasicParserVisitor<Void> {
    override fun visitBinaryOper(ctx: BasicParser.BinaryOperContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitExpr(ctx: BasicParser.ExprContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitChildren(node: RuleNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitErrorNode(node: ErrorNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitProg(ctx: BasicParser.ProgContext?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(tree: ParseTree?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitTerminal(node: TerminalNode?): Void {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}