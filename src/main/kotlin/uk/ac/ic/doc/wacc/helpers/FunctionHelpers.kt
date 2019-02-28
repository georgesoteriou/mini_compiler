package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function

fun CodeGenerator.compileBlock(name: String, block: Statement.Block, params: List<String> = listOf()) {
    if(name != "") {
        instructions.add(Instruction.LABEL(name))
        instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))
    }

    block.scope.findFullSize()
    block.scope.blockSize = block.scope.fullSize
    if (!params.isEmpty()) {
            block.scope.blockSize -= 4
    }
    params.forEach {
        block.scope.blockSize -= Type.size(block.scope.definitions[it]!!.type)
    }

    activeScope = activeScope.newSubScope(block.scope)
    decreaseSP(block)
    block.statements.forEach { compileStatement(it) }
    increaseSP(block)

    if(name == "main") {
        instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
    }

    activeScope = activeScope.parentScope!!

    if(name != "") {
        instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
        instructions.add(Instruction.Flag(".ltorg"))
    }
}

fun CodeGenerator.pushArgsToStack(func: Expression.CallFunction) {
    var stackOffset = 0
    func.params.forEach {
        compileExpression(it, 4)
        val size = Type.size(it.exprType)
        activeScope.currentScope.fullSize += size
        stackOffset += size
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
    activeScope.currentScope.fullSize -= stackOffset
}

fun CodeGenerator.argsSize(func: Expression.CallFunction) : Int {
    var size = 0
    func.params.forEach {
        size += Type.size(it.exprType)
    }
    return size
}

fun CodeGenerator.increaseSP(statement: Statement.Block) {
    var declarationsSize = statement.scope.blockSize
    for (i in 1..statement.scope.blockSize step 1024) {
        instructions.add(
            Instruction.ADD(
                Operand.Sp, Operand.Sp, Operand.Offset(
                    if (declarationsSize > 1024) {
                        declarationsSize -= 1024
                        1024
                    } else {
                        declarationsSize
                    }
                )
            )
        )
    }
}

fun CodeGenerator.decreaseSP(statement: Statement.Block): Int {
    var declarationsSize = statement.scope.blockSize
    for (i in 1..statement.scope.blockSize step 1024) {
        instructions.add(
            Instruction.SUB(
                Operand.Sp, Operand.Sp, Operand.Offset(
                    if (declarationsSize > 1024) {
                        declarationsSize -= 1024
                        1024
                    } else {
                        declarationsSize
                    }
                )
            )
        )
    }
    return declarationsSize
}

