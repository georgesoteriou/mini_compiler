package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import java.lang.Integer.max
import java.lang.Integer.min

fun CodeGenerator.weight(expr: Expression): Int
// TODO: Make this work properly
        = when (expr) {
            is Expression.BinaryOperation -> {
                val e1 = expr.e1
                val e2 = expr.e2
                val max1 = max(weight(e1) + 1, weight(e2))
                val max2 = max(weight(e1), weight(e2) + 1)
                min(max1, max2)
            }
            is Expression.CallFunction -> 1 // TODO: idk, go through whole list of args?
            is Expression.UnaryOperation -> weight(expr.expression) //TODO: not sure, 1?
            else -> 1
}

fun CodeGenerator.binOpInstructions(expr: Expression.BinaryOperation, dest: Int) {
    // TODO: check for overflow errors
    when (expr.operator) {
        Expression.BinaryOperator.PLUS -> {
            instructions.addAll(arrayListOf(
                Instruction.ADDS(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                ),
                Instruction.BCond("p_throw_overflow_error","LVS")
            ))
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.MINUS -> {
            instructions.addAll(arrayListOf(
                Instruction.SUBS(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                ),
                Instruction.BCond("p_throw_overflow_error","LVS")
            ))
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.MULT -> {

            instructions.addAll(arrayListOf(
                Instruction.SMULL(
                    Operand.Register(dest),
                    Operand.Register(dest + 1),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                ),
                Instruction.CMPCond(Operand.Register(5),Operand.Register(4),"ASR #31"),
                Instruction.BCond("p_throw_overflow_error","LNE")

            ))
            throwOverflowFlag = true
            throwRuntimeFlag = true
            printStringFlag = true
        }
        Expression.BinaryOperator.DIV -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.MOV(Operand.Register(0), Operand.Register(dest)),
                    Instruction.MOV(Operand.Register(1), Operand.Register(dest + 1)),
                    Instruction.BCond("p_check_divide_by_zero","L"),
                    Instruction.BL("__aeabi_idiv"),
                    Instruction.MOV(Operand.Register(dest),Operand.Register(0))
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
            printStringFlag  = true
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
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "EQ"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "NE")
                )
            )
        }
        Expression.BinaryOperator.NOTEQ -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "NE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "EQ")
                )
            )
        }
        Expression.BinaryOperator.GT -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "GT"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "LE")
                )
            )
        }
        Expression.BinaryOperator.GTE -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "GE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "LT")
                )
            )
        }
        Expression.BinaryOperator.LT -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "LT"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "GE")
                )
            )
        }
        Expression.BinaryOperator.LTE -> {
            instructions.addAll(
                arrayListOf(
                    Instruction.CMP(Operand.Register(dest), Operand.Register(dest + 1)),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(1), "LE"),
                    Instruction.MOVCond(Operand.Register(dest), Operand.Constant(0), "GT")
                )
            )
        }
    }
}

fun CodeGenerator.unOpInstructions(expr: Expression.UnaryOperation, dest: Int) {

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
        Expression.UnaryOperator.CHR -> {}
        Expression.UnaryOperator.ORD -> {}
    }

}