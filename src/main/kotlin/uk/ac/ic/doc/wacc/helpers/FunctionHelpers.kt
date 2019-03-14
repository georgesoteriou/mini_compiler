package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.MIN_EXPR_REG
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.WORD
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*

const val MAX_SUB_SIZE = 1024

fun CodeGenerator.compileBlock(
    name: String,
    block: Statement.Block,
    isFunction: Boolean = false,
    params: List<String> = listOf()
) {
    if (name != "") {
        instructions.add(Instruction.LABEL(name))
        instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))
    }

    block.scope.findFullSize()
    block.scope.blockSize = block.scope.fullSize
    if (!params.isEmpty()) {
        block.scope.blockSize -= WORD
    }
    params.forEach {
        block.scope.blockSize -= Type.size(block.scope.definitions[it]!!.type)
    }

    activeScope = activeScope.newSubScope(block.scope)
    decreaseSP(block)
    block.statements.forEach { compileStatement(it) }
    if (!isFunction) {
        increaseSP(block)
    }

    if (name == "main") {
        instructions.add(
            Instruction.LDRSimple(
                Operand.Register(0),
                Operand.Literal.LInt(0)
            )
        )
    }

    activeScope = activeScope.parentScope!!

    if (name != "" && !isFunction) {
        instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
        instructions.add(Instruction.Flag(".ltorg"))
    }
}

fun CodeGenerator.pushArgsToStack(func: Expression.CallFunction) {
    var stackOffset = 0
    func.params.forEach {
        compileExpression(it, MIN_EXPR_REG)
        val size = Type.size(it.exprType)
        activeScope.currentScope.fullSize += size
        stackOffset += size
        if (size == 1) {
            instructions.add(
                Instruction.STRBOffset(
                    Operand.Register(MIN_EXPR_REG), Operand.Sp, Operand.Offset(-size)
                )
            )
        } else {
            instructions.add(
                Instruction.STROffset(
                    Operand.Register(MIN_EXPR_REG), Operand.Sp, Operand.Offset(-size)
                )
            )
        }
    }
    activeScope.currentScope.fullSize -= stackOffset
}

fun argsSize(func: Expression.CallFunction): Int {
    var size = 0
    func.params.forEach {
        size += Type.size(it.exprType)
    }
    return size
}

fun CodeGenerator.increaseSP(statement: Statement.Block) {
    var declarationsSize = statement.scope.blockSize
    for (i in 1..statement.scope.blockSize step MAX_SUB_SIZE) {
        instructions.add(
            Instruction.ADD(
                Operand.Sp, Operand.Sp, Operand.Offset(
                    if (declarationsSize > MAX_SUB_SIZE) {
                        declarationsSize -= MAX_SUB_SIZE
                        MAX_SUB_SIZE
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
    for (i in 1..statement.scope.blockSize step MAX_SUB_SIZE) {
        instructions.add(
            Instruction.SUB(
                Operand.Sp, Operand.Sp, Operand.Offset(
                    if (declarationsSize > MAX_SUB_SIZE) {
                        declarationsSize -= MAX_SUB_SIZE
                        MAX_SUB_SIZE
                    } else {
                        declarationsSize
                    }
                )
            )
        )
    }
    return declarationsSize
}

fun CodeGenerator.returnStatementInstructions(statement: Statement.Return) {
    compileExpression(statement.expression, MIN_EXPR_REG)
    instructions.add(
        Instruction.MOV(
            Operand.Register(0),
            Operand.Register(MIN_EXPR_REG)
        )
    )
    var tempScope: ActiveScope? = activeScope
    var size = 0
    while (tempScope != null) {
        size += tempScope.currentScope.blockSize
        tempScope = tempScope.parentScope
    }
    for (i in 1..size step MAX_SUB_SIZE) {
        instructions.add(
            Instruction.ADD(
                Operand.Sp, Operand.Sp, Operand.Offset(
                    if (size > MAX_SUB_SIZE) {
                        size -= MAX_SUB_SIZE
                        MAX_SUB_SIZE
                    } else {
                        size
                    }
                )
            )
        )
    }
    instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
}

fun CodeGenerator.exitStatementInstructions(statement: Statement.Exit) {
    compileExpression(statement.expression, MIN_EXPR_REG)
    instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(MIN_EXPR_REG)))
    instructions.add(Instruction.BL("exit"))
}
