package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function

fun CodeGenerator.compileBlock(name: String, block: Statement.Block, params: List<String> = listOf()) {
    instructions.add(Instruction.LABEL(name))
    instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))

    block.scope.findFullSize()
    block.scope.blockSize = block.scope.fullSize
    if (!params.isEmpty()) {
            block.scope.blockSize -= 4
    }
    params.forEach {
        block.scope.blockSize -= Type.size(block.scope.definitions[it]!!.type)
    }

    activeScope = activeScope.newSubScope(block.scope)

    compileStatement(block)

    activeScope = activeScope.parentScope!!


    if(name == "main") {
        instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
    }
    instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
    instructions.add(Instruction.Flag(".ltorg"))
}

fun CodeGenerator.pushArgsToStack(func: Expression.CallFunction) {
    func.params.forEach {
        compileExpression(it, 4)
        val size = Type.size(it.exprType)
        if(size == 1) {
            instructions.add(
                Instruction.STRBOffset(
                    Operand.Register(4), Operand.Sp, Operand.Offset(-size)
                )
            )
        } else {
            instructions.add(
                Instruction.STROffset(
                    Operand.Register(4), Operand.Sp, Operand.Offset(-size)
                )
            )
        }
    }
}

fun CodeGenerator.argsSize(func: Expression.CallFunction) : Int {
    var size = 0
    func.params.forEach {
        size += Type.size(it.exprType)
    }
    return size
}