package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import java.lang.RuntimeException

class FunctionVisitor: WaccParserBaseVisitor<Function>() {
    override fun visitFunc(ctx: WaccParser.FuncContext): Function {
        val name = Expression.Identifier(ctx.IDENT().toString())
        val params = if (ctx.param_list() != null) {
            ctx.param_list().param().map {
                Expression.Variable(Expression.Identifier(it.IDENT().toString()),
                    it.type().accept(TypeVisitor()))
            }
        } else {
            null
        }
        val block = ctx.stat_list().accept(StatementVisitor())
        checkReturn(block as Statement.Block)
        val thisRetType = ctx.type().accept(TypeVisitor())
        return Function(name, params, block,thisRetType)
    }
}


fun checkReturn(block: Statement.Block): Boolean{
    val stat = (block).statements.last()
    return when (stat) {
        is Statement.Return -> true
        is Statement.Exit -> true
        is Statement.While  -> checkReturn((stat.then as Statement.Block))
        is Statement.If     -> {
            checkReturn((stat.ifThen as Statement.Block)) && checkReturn((stat.elseThen as Statement.Block))
        }
        else -> throw RuntimeException("Line "+stat.location.lineNum+": Function missing return statement")
    }
}