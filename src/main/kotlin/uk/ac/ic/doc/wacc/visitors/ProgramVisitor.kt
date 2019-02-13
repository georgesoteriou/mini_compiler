package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import kotlin.system.exitProcess

class ProgramVisitor: WaccParserBaseVisitor<Program>() {
    override fun visitProg(ctx: WaccParser.ProgContext): Program {
        val functions = ctx.func().map {it.accept(FunctionVisitor())}
        //
        val prog = ctx.stat_list().accept(StatementVisitor())
        return Program(functions, prog)
    }
}

