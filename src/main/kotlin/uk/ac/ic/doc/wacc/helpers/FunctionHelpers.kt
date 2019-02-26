package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.ast.Type

fun CodeGenerator.compileBlock(name: String, block: Statement.Block) {
    instructions.add(Instruction.LABEL(name))
    instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))

    compileStatement(block)

    if(name == "main") {
        instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
    }
    instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
    instructions.add(Instruction.Flag(".ltorg"))
}

fun CodeGenerator.pushArgsToStack(func: Expression.CallFunction) {
    func.params.forEach {
        compileExpression(it, 4)
        instructions.add(
            Instruction.STROffset(
                Operand.Register(4),
                Operand.Sp,
                Operand.Offset(- Type.size(it.exprType))
            )
        )
    }
}

fun CodeGenerator.argsSize(func: Expression.CallFunction) : Int {
    var size = 0
    func.params.forEach {
        size += Type.size(it.exprType)
    }
    return size
}