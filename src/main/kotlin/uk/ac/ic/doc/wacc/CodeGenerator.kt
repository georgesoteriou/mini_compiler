package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.ast.Statement

class CodeGenerator(var program: Program) {
    var instructions: MutableList<Instruction> = arrayListOf()

    fun generateStatements(statement: Statement) {
        when (statement) {
            is Statement.Block -> {
                statement.statements.forEach { generateStatements(it) }
            }
            is Statement.Skip -> {}
            is Statement.VariableDeclaration -> {}
            is Statement.VariableAssignment -> {}
            is Statement.ReadInput -> {}
            is Statement.FreeVariable -> {}
            is Statement.Return -> {}
            is Statement.Exit -> {}
            is Statement.Print -> {}
            is Statement.PrintLn -> {}
            is Statement.If -> {}
            is Statement.While -> {}
        }

    }
}