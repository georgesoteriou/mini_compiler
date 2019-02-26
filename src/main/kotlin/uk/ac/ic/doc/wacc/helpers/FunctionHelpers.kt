package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Statement

fun CodeGenerator.compileBlock(name: String, block: Statement.Block) {
    instructions.add(Instruction.LABEL(name))
    instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))

    compileStatement(block)

    instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
    instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
    instructions.add(Instruction.Flag(".ltorg"))
}