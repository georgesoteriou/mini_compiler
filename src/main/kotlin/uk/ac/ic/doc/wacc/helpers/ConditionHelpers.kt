package uk.ac.ic.doc.wacc.helpers


import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Statement


fun CodeGenerator.whileInstructions(statement: Statement.While) {
    val l0 = "L$labelCounter"
    val l1 = "L${labelCounter + 1}"
    instructions.add(
        Instruction.BCond(l0, "")
    )

    instructions.add(
        Instruction.LABEL(l1)
    )

    labelCounter += 2
    compileStatement(statement.then, l1)

    instructions.add(
        Instruction.LABEL(l0)
    )
    compileExpression(statement.condition, 4)
    instructions.add(
        Instruction.CMP(
            Operand.Register(4),
            Operand.Constant(1)
        )
    )
    instructions.add(
        Instruction.BCond(
            l1,
        "EQ"
        )
    )
}

fun CodeGenerator.ifInstructions(statement: Statement.If) {
    instructions.add(
        Instruction.CMP(
            Operand.Register(4),
            Operand.Constant(0)
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
            l1, ""
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