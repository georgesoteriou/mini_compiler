package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.ast.Scope
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor


class ProgramVisitor : WaccParserBaseVisitor<Program>() {
    override fun visitProg(ctx: WaccParser.ProgContext): Program {
        val includesProg = ctx.includes().map { it.FILENAME().toString()}
        val functions = ctx.func().map { it.accept(FunctionVisitor()) }
        val mutableFunctions:MutableList<Function> = arrayListOf()
        mutableFunctions.addAll(functions)
        var prog = Statement.Block(arrayListOf(), Scope())
        if(ctx.stat_list() != null) {
            prog = ctx.stat_list().accept(StatementVisitor()) as Statement.Block
        }
        return Program(includesProg, mutableFunctions, prog)
    }
}

