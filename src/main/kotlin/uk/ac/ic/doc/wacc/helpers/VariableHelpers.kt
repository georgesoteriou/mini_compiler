package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Definition
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.ast.Type


fun CodeGenerator.byteAssignInstructions(name: String) = instructions.add(
    Instruction.STRBOffset(
        Operand.Register(4),
        Operand.Sp,
        Operand.Offset(activeScope.getPosition(name))
    )
)

fun CodeGenerator.wordAssignInstructions(name: String) = instructions.add(
    Instruction.STROffset(
        Operand.Register(4),
        Operand.Sp,
        Operand.Offset(activeScope.getPosition(name))
    )
)


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
        else -> {
            instructions.add(
                Instruction.LDRSimple(Operand.Register(dest), Operand.Literal.LInt("0"))
            )
        }
    }
}

fun CodeGenerator.pairNullInstructions(lhs: Definition) {
    val offset = activeScope.getPosition(lhs.name)
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(4),
            Operand.Literal.LInt("0")
        )
    )
    instructions.add(
        Instruction.STROffset(
            Operand.Register(4),
            Operand.Sp,
            Operand.Offset(offset)
        )
    )
}

fun CodeGenerator.pairAssignInstructions(definition: Definition, rhs: Expression.NewPair) {
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(0),
            Operand.Literal.LInt("8")
        )
    )
    instructions.add(Instruction.BL("malloc"))
    instructions.add(
        Instruction.MOV(
            Operand.Register(4),
            Operand.Register(0)
        )
    )
    var typeL = (definition.type as Type.TPair).t1
    var typeR = (definition.type as Type.TPair).t2
    var e1 = rhs.e1
    var e2 = rhs.e2

    elemAssignInstructions(typeL, e1)

    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(0),
            Operand.Literal.LInt(Type.size(e1.exprType).toString())
        )
    )

    instructions.add(Instruction.BL("malloc"))

    if (Type.size(typeL) != 1) {
        instructions.add(
            Instruction.STRSimple(
                Operand.Register(5),
                Operand.Register(0)
            )
        )
    } else {
        instructions.add(
            Instruction.STRBOffset(
                Operand.Register(5),
                Operand.Register(0),
                Operand.Offset(0)
            )
        )
    }

    instructions.add(
        Instruction.STRSimple(
            Operand.Register(0),
            Operand.Register(4)
        )
    )

    elemAssignInstructions(typeR, e2)

    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(0),
            Operand.Literal.LInt(Type.size(e1.exprType).toString())
        )
    )

    instructions.add(Instruction.BL("malloc"))

    if (Type.size(typeR) != 1) {
        instructions.add(
            Instruction.STRSimple(
                Operand.Register(5),
                Operand.Register(0)
            )
        )
    } else {
        instructions.add(
            Instruction.STRBOffset(
                Operand.Register(5),
                Operand.Register(0),
                Operand.Offset(0)
            )
        )
    }

    instructions.add(
        Instruction.STROffset(
            Operand.Register(0),
            Operand.Register(4),
            Operand.Offset(4)
        )
    )

    instructions.add(
        Instruction.STROffset(
            Operand.Register(4),
            Operand.Sp,
            Operand.Offset(
                activeScope.getPosition((definition.name))
            )
        )
    )
}

fun CodeGenerator.elemAssignInstructions(type: Type, expr: Expression) {
    when (type) {
        is Type.TArray, is Type.TPair -> {
            addPointerLDR(expr, 5)
        }
        else -> {
            compileExpression(expr, 5)
        }
    }
}

fun CodeGenerator.arrayAssignInstructions(lhs: Definition, rhs: Expression.Literal.LArray) {
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(0),
            Operand.Literal.LInt(
                (rhs.params.size * Type.size((lhs.type as Type.TArray).type) + 4).toString()
            )
        )
    )
    instructions.add(Instruction.BL("malloc"))
    instructions.add(
        Instruction.MOV(
            Operand.Register(4),
            Operand.Register(0)
        )
    )

    var offset = Type.size(lhs.type)
    val type = (rhs.exprType as Type.TArray).type
    rhs.params.forEach {
        elemAssignInstructions(type, it)
        if (Type.size(type) != 1) {
            instructions.add(
                Instruction.STROffset(
                    Operand.Register(5),
                    Operand.Register(4),
                    Operand.Offset(offset)
                )
            )
        } else {
            instructions.add(
                Instruction.STRBOffset(
                    Operand.Register(5),
                    Operand.Register(4),
                    Operand.Offset(offset)
                )
            )
        }
        offset += Type.size(type)
    }

    //Storing no. of array elems
    instructions.add(
        Instruction.LDRSimple(
            Operand.Register(5),
            Operand.Literal.LInt(rhs.params.size.toString())
        )
    )
    instructions.add(
        Instruction.STRSimple(
            Operand.Register(5),
            Operand.Register(4)
        )
    )
    //Store array to sp
    var pos = activeScope.getPosition(lhs.name)
    instructions.add(
        when (pos) {
            0 -> Instruction.STRSimple(
                Operand.Register(4),
                Operand.Sp
            )
            else -> Instruction.STROffset(
                Operand.Register(4),
                Operand.Sp,
                Operand.Offset(pos)
            )
        }
    )
}
