package uk.ac.ic.doc.wacc.helpers


import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.FALSE
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.MIN_EXPR_REG
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.TRUE
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Statement


fun CodeGenerator.whileInstructions(statement: Statement.While) {
    val l0 = "L$labelCounter"
    val l1 = "L${labelCounter + 1}"
    instructions.add(
        Instruction.BCond(l0)
    )

    instructions.add(
        Instruction.LABEL(l1)
    )

    labelCounter += 2
    compileStatement(statement.then)

    instructions.add(
        Instruction.LABEL(l0)
    )
    compileExpression(statement.condition, MIN_EXPR_REG)
    instructions.add(
        Instruction.CMP(
            Operand.Register(MIN_EXPR_REG),
            Operand.Constant(TRUE)
        )
    )
    instructions.add(
        Instruction.BCond(
            l1, "EQ"
        )
    )
}

fun CodeGenerator.ifInstructions(statement: Statement.If) {
    compileExpression(statement.condition, 4)
    instructions.add(
        Instruction.CMP(
            Operand.Register(MIN_EXPR_REG),
            Operand.Constant(FALSE)
        )
    )
    val l0 = "L$labelCounter"
    val l1 = "L${labelCounter + 1}"

    instructions.add(
        Instruction.BCond(
            l0,
            "EQ"
        )
    )
    labelCounter+=2

    compileStatement(statement.ifThen)

    instructions.add(
        Instruction.BCond(
            l1
        )
    )
    instructions.add(
        Instruction.LABEL(l0)
    )

    compileStatement(statement.elseThen)


    instructions.add(
        Instruction.LABEL(l1)
    )

}