package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import java.lang.RuntimeException
import java.util.*

class FunctionVisitor : WaccParserBaseVisitor<Function>() {
    override fun visitFunc(ctx: WaccParser.FuncContext): Function {
        val name = ctx.IDENT().toString()
        val paramNames = if (ctx.param_list() != null) {
            ctx.param_list().param().map {
                it.IDENT().toString()
            }
        } else {
            Collections.emptyList()
        }
        val paramTypes = if (ctx.param_list() != null) {
            ctx.param_list().param().map {
                it.type().accept(TypeVisitor())
            }
        } else {
            Collections.emptyList()
        }
        val block = ctx.stat_list().accept(StatementVisitor())
        checkReturn(block as Statement.Block)
        val funType = Type.TFunction(ctx.type().accept(TypeVisitor()), paramTypes)
        return Function(name, paramNames, block, funType)
    }
}


fun checkReturn(block: Statement.Block): Boolean {
    val stat = (block).statements.last()
    return when (stat) {
        is Statement.Return -> true
        is Statement.Exit -> true
        is Statement.While -> checkReturn((stat.then as Statement.Block))
        is Statement.If -> {
            checkReturn((stat.ifThen as Statement.Block)) && checkReturn((stat.elseThen as Statement.Block))
        }
        else -> throw RuntimeException("Line " + stat.location.lineNum + ": Function missing return statement")
    }
}