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
    is Expression.UnaryOperation -> 1 //TODO: not sure, 1?
    else -> 1
}

fun CodeGenerator.binOpInstructions(expr: Expression.BinaryOperation, dest: Int) {
    when (expr.operator) {
        Expression.BinaryOperator.PLUS -> {
            instructions.add(
                Instruction.ADDS(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                )
            )
        }
        Expression.BinaryOperator.MINUS -> {
            instructions.add(
                Instruction.SUBS(
                    Operand.Register(dest),
                    Operand.Register(dest),
                    Operand.Register(dest + 1)
                )
            )
        }
        //TODO: Go through all types of expr, try not to die
        else -> {
        }
    }
}

fun CodeGenerator.unOpInstructions(expr: Expression.UnaryOperation, dest: Int) {
    when (expr.operator) {
        Expression.UnaryOperator.MINUS -> {
            instructions.add(
                Instruction.LDRSimple(
                    Operand.Register(dest),
                    Operand.Literal.LInt(
                        // TODO: We cannot assume this is an int here.... do the same as above
                        // TODO: eg. ----5 is valid too
                        "-${(expr.expression as Expression.Literal.LInt).int}"
                    )
                )
                // TODO: consider doing SUB 0, dest+1, dest (dest = 0 - dest) to make it negative
                // TODO: or MUL -1, dest+1, dest (dest = -1 * dest+1)
            )
        }
        Expression.UnaryOperator.CHR,
        Expression.UnaryOperator.LEN,
        Expression.UnaryOperator.NOT,
        Expression.UnaryOperator.ORD -> {
        }
    }

}