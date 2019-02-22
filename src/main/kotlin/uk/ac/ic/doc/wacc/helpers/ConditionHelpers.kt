package uk.ac.ic.doc.wacc.helpers


import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand

fun CodeGenerator.whileInstructions(label: Int) {
    instructions.add(
        Instruction.CMP(
            Operand.Register(4),
            Operand.Constant(1)
        )
    )
    instructions.add(
        Instruction.BCond(
            "L${label + 1}",
        "EQ"
        )
    )
}