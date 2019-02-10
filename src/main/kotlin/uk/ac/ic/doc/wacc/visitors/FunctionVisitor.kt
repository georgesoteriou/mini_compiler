package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

class FunctionVisitor: WaccParserBaseVisitor<Function>() {
    override fun visitFunc(ctx: WaccParser.FuncContext): Function {
        val params = if (ctx.param_list() != null) {
            Expression.ExpressionList(ctx.param_list().param().map { it.accept(ExprVisitor()) })
        } else {
            null
        }
        val block = ctx.stat_list().accept(StatementVisitor())
        return Function(params, block)
    }
}