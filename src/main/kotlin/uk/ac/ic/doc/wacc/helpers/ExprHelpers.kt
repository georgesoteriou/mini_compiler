package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.BYTE
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.FALSE
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.MAX_EXPR_REG
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.TRUE
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.WORD
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Type


fun CodeGenerator.addPointerLDR(e1: Expression, dest: Int) {
    when (e1) {
        is Expression.Identifier -> {
            val pos = activeScope.getPosition(e1.name)
            when (e1.exprType) {
                is Type.TBool, is Type.TChar -> {
                    instructions.add(
                        Instruction.LDRRegCond(
                            Operand.Register(dest),
                            Operand.Sp,
                            Operand.Offset(pos),
                            "SB"
                        )
                    )
                }
                else -> {
                    instructions.add(
                        Instruction.LDRRegister(
                            Operand.Register(dest),
                            Operand.Sp,
                            Operand.Offset(pos)
                        )
                    )
                }
            }
        }
        else -> instructions.add(
            Instruction.LDRSimple(
                Operand.Register(dest),
                Operand.Literal.LInt(0)
            )
        )

    }
}

fun CodeGenerator.arrayElemExprInstructions(expression: Expression.ArrayElem, dst: Int) {
    instructions.add(
        Instruction.ADD(
            Operand.Register(dst),
            Operand.Sp,
            Operand.Constant(activeScope.getPosition(expression.array))
        )
    )

    for (i in 0 until expression.indexes.size) {
        compileExpression(expression.indexes[i], dst + 1)
        instructions.add(
            Instruction.LDRRegister(
                Operand.Register(dst),
                Operand.Register(dst),
                Operand.Offset(0)
            )
        )
        instructions.add(
            Instruction.MOV(
                Operand.Register(0),
                Operand.Register(dst + 1)
            )
        )
        instructions.add(
            Instruction.MOV(
                Operand.Register(1),
                Operand.Register(dst)
            )
        )
        instructions.add(Instruction.BL("p_check_array_bounds"))
        checkArrayFlag = true
        throwRuntimeFlag = true
        printStringFlag = true
        instructions.add(
            Instruction.ADD(
                Operand.Register(dst),
                Operand.Register(dst),
                Operand.Constant(WORD)
            )
        )
        if (Type.size(expression.exprType) != BYTE) {
            instructions.add(
                Instruction.ADDCond(
                    Operand.Register(dst),
                    Operand.Register(dst),
                    Operand.Register(dst + 1),
                    "LSL #2"
                )
            )
        } else {
            instructions.add(
                Instruction.ADD(
                    Operand.Register(dst),
                    Operand.Register(dst),
                    Operand.Register(dst + 1)
                )
            )
        }
    }
    if (Type.size(expression.exprType) != BYTE) {
        instructions.add(
            Instruction.LDRRegister(
                Operand.Register(dst),
                Operand.Register(dst),
                Operand.Offset(0)
            )
        )
    } else {
        instructions.add(
            Instruction.LDRRegCond(
                Operand.Register(dst),
                Operand.Register(dst),
                Operand.Offset(0),
                "SB"
            )
        )
    }
}

fun CodeGenerator.pairElemExprInstructions(name: String, type: Type, pairOffset: Int, dest: Int) {
    val offset = activeScope.getPosition(name)
    instructions.addAll(
        arrayListOf(
            Instruction.LDRRegister(Operand.Register(dest), Operand.Sp, Operand.Offset(offset)),
            Instruction.MOV(Operand.Register(0), Operand.Register(dest)),
            Instruction.BL("p_check_null_pointer"),
            Instruction.LDRRegister(Operand.Register(dest), Operand.Register(dest), Operand.Offset(pairOffset))
        )
    )


    if (Type.size(type) != BYTE) {
        instructions.add(
            Instruction.LDRRegister(
                Operand.Register(dest),
                Operand.Register(dest),
                Operand.Offset(0)
            )
        )
    } else {
        instructions.add(
            Instruction.LDRRegCond(
                Operand.Register(dest),
                Operand.Register(dest),
                Operand.Offset(0),
                "SB"
            )
        )
    }

    checkNullPointerFlag = true
    throwRuntimeFlag = true
    printStringFlag = true
}

fun CodeGenerator.callFunctionExprInstructions(expression: Expression.CallFunction, dest: Int) {
    pushArgsToStack(expression)
    instructions.add(
        Instruction.BL(expression.name)
    )
    instructions.add(
        Instruction.ADD(
            Operand.Sp,
            Operand.Sp,
            Operand.Offset(argsSize(expression))
        )
    )
    instructions.add(
        Instruction.MOV(
            Operand.Register(dest),
            Operand.Register(0)
        )
    )
}

fun CodeGenerator.intExprInstructions(expression: Expression.Literal.LInt, dst: Int, dest: Int) {
    if (dst > MAX_EXPR_REG) {
        instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(MAX_EXPR_REG))))
    }
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(dest),
            Operand.Literal.LInt(expression.int)
        )
    )
}

fun CodeGenerator.boolExprInstructions(expression: Expression.Literal.LBool, dst: Int, dest: Int) {
    if (dst > MAX_EXPR_REG) {
        instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(MAX_EXPR_REG))))
    }
    instructions.add(
        Instruction.MOV(
            Operand.Register(dest),
            Operand.Literal.LBool(expression.bool)
        )
    )
}

fun CodeGenerator.charExprInstructions(expression: Expression.Literal.LChar, dst: Int, dest: Int) {
    if (dst > MAX_EXPR_REG) {
        instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(MAX_EXPR_REG))))
    }
    instructions.add(
        Instruction.MOV(
            Operand.Register(dest),
            Operand.Literal.LChar(expression.char)
        )
    )
}

fun CodeGenerator.stringExprInstructions(expression: Expression.Literal.LString, dest: Int) {
    messageTagGenerator(expression.string)
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(dest),
            Operand.MessageTag(messageCounter - 1)
        )
    )
}

fun CodeGenerator.nullExprInstructions(dest: Int) {
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(dest),
            Operand.Literal.LInt(0)
        )
    )
}


fun CodeGenerator.unOpExprInstructions(expr: Expression.UnaryOperation, dest: Int) {
    compileExpression(expr.expression, dest)
    when (expr.operator) {
        Expression.UnaryOperator.MINUS -> {
            instructions.add(
                Instruction.RSBS(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Constant(0)
                )
            )
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
            instructions.add(
                Instruction.BCond(
                    "p_throw_overflow_error",
                    "LVS"
                )
            )
        }

        Expression.UnaryOperator.LEN -> {
            instructions.add(
                Instruction.LDRRegister(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Offset(0)
                )
            )
        }
        Expression.UnaryOperator.NOT -> {
            instructions.add(
                Instruction.EOR(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Constant(1)
                )
            )
        }
        else -> {
        }
    }

}


fun CodeGenerator.binOpExprInstructions(expression: Expression.BinaryOperation, dst: Int, dest: Int) {
    val e1 = expression.e1
    val e2 = expression.e2

    compileExpression(e1, dst)
    compileExpression(e2, dst + 1)
    if (dst >= 10) {
        instructions.add(Instruction.POP(arrayListOf(Operand.Register(11))))
    }
    when (expression.operator) {
        Expression.BinaryOperator.PLUS -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.ADDS(
                        Operand.Register(dest),
                        Operand.Register(dest),
                        Operand.Register(dest + 1)
                    ),
                    Instruction.BCond("p_throw_overflow_error", "LVS")
                )
            )
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.MINUS -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.SUBS(
                        Operand.Register(dest),
                        Operand.Register(dest),
                        Operand.Register(dest + 1)
                    ),
                    Instruction.BCond("p_throw_overflow_error", "LVS")
                )
            )
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.MULT -> {

            instructions.addAll(
                arrayListOf(
                    Instruction.SMULL(
                        Operand.Register(dest),
                        Operand.Register(dest + 1),
                        Operand.Register(dest),
                        Operand.Register(dest + 1)
                    ),
                    Instruction.CMPCond(
                        Operand.Register(dest + 1),
                        Operand.Register(dest),
                        "ASR #31"
                    ),
                    Instruction.BCond("p_throw_overflow_error", "LNE")

                )
            )
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.DIV -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.MOV(Operand.Register(0), Operand.Register(dest)),
                    Instruction.MOV(Operand.Register(1), Operand.Register(dest + 1)),
                    Instruction.BCond("p_check_divide_by_zero", "L"),
                    Instruction.BL("__aeabi_idiv"),
                    Instruction.MOV(Operand.Register(dest), Operand.Register(0))
                )
            )
            divideByZeroFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.MOD -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.MOV(Operand.Register(0), Operand.Register(dest)),
                    Instruction.MOV(Operand.Register(1), Operand.Register(dest + 1)),
                    Instruction.BL("p_check_divide_by_zero"),
                    Instruction.BL("__aeabi_idivmod"),
                    Instruction.MOV(Operand.Register(dest), Operand.Register(1))
                )
            )
            divideByZeroFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.AND -> {
            instructions.add(
                Instruction.AND(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                )
            )
        }
        Expression.BinaryOperator.OR -> {
            instructions.add(
                Instruction.ORR(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                )
            )
        }
        Expression.BinaryOperator.EQ -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "EQ"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "NE")
                )
            )
        }
        Expression.BinaryOperator.NOTEQ -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "NE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "EQ")
                )
            )
        }
        Expression.BinaryOperator.GT -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "GT"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "LE")
                )
            )
        }
        Expression.BinaryOperator.GTE -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "GE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "LT")
                )
            )
        }
        Expression.BinaryOperator.LT -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "LT"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "GE")
                )
            )
        }
        Expression.BinaryOperator.LTE -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(TRUE), "LE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(FALSE), "GT")
                )
            )
        }
    }
}