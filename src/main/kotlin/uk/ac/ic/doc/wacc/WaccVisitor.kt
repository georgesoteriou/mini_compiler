package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor



class BlockVisitor: WaccParserBaseVisitor<Statement>() {
    override fun visitStat_list(ctx: WaccParser.Stat_listContext?): Statement {
        return Statement.Block(ctx!!.stat().map {stat -> stat.accept(this)})
    }
}

