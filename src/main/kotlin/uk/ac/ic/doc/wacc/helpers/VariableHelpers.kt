package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Definition
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.ast.Type

fun CodeGenerator.declare(statement: Statement.VariableDeclaration) {
    val type = statement.lhs.type
    val name = statement.lhs.name
    activeScope.declare(name)
    when (type) {
        is Type.TInt, is Type.TString -> {
            compileExpression(statement.rhs, 4)
            wordAssignInstructions(name)
        }
        is Type.TBool, is Type.TChar -> {
            compileExpression(statement.rhs, 4)
            byteAssignInstructions(name)
        }
        is Type.TArray -> arrayDeclare(statement)
        is Type.TPair -> pairDeclare(statement)
    }
}

fun CodeGenerator.assign(statement: Statement.VariableAssignment) {
    val lhs = statement.lhs
    when (lhs) {
        is Expression.Identifier -> identifierAssign(statement, lhs)
        is Expression.ArrayElem -> arrayElemAssign(statement, lhs)
        is Expression.Fst -> pairElemAssign(statement, lhs.expression.name, 0)
        is Expression.Snd -> pairElemAssign(statement, lhs.expression.name, 4)
    }
}

fun CodeGenerator.pairDeclare(statement: Statement.VariableDeclaration) {
    val rhs = statement.rhs
    when (rhs) {
        is Expression.Literal.LPair -> {
            pairNullInstructions(statement.lhs)
        }
        is Expression.NewPair -> {
            pairAssignInstructions(statement.lhs, rhs)
        }
        else -> {
            compileExpression(rhs, 4)
            instructions.add(
                Instruction.STROffset(
                    Operand.Register(4),
                    Operand.Sp,
                    Operand.Offset(activeScope.getPosition(statement.lhs.name))
                )
            )
        }
    }
}

fun CodeGenerator.arrayDeclare(statement: Statement.VariableDeclaration) {
    val rhs = statement.rhs
    if (rhs is Expression.Literal.LArray) {
        arrayAssignInstructions(statement.lhs, rhs)
    } else {
        compileExpression(rhs, 4)
        instructions.add(
            Instruction.STROffset(
                Operand.Register(4),
                Operand.Sp,
                Operand.Offset(activeScope.getPosition(statement.lhs.name))
            )
        )
    }
}

fun CodeGenerator.identifierAssign(statement: Statement.VariableAssignment, lhs: Expression.Identifier) {
    val type = statement.rhs.exprType
    val name = lhs.name
    when (type) {
        is Type.TInt, is Type.TString -> {
            compileExpression(statement.rhs, 4)
            wordAssignInstructions(name)
        }
        is Type.TBool, is Type.TChar -> {
            compileExpression(statement.rhs, 4)
            byteAssignInstructions(name)
        }
        is Type.TArray -> {
            val def = Definition(name, lhs.exprType)
            arrayAssignInstructions(def, statement.rhs as Expression.Literal.LArray)
        }
        is Type.TPair -> {
            val def = Definition(name, lhs.exprType)
            val rhs = statement.rhs
            when (rhs) {
                is Expression.Literal.LPair -> {
                    pairNullInstructions(def)
                }
                is Expression.NewPair -> {
                    pairAssignInstructions(def, rhs)
                }
                else -> {
                    compileExpression(rhs, 4)
                    instructions.add(
                        Instruction.STROffset(
                            Operand.Register(4),
                            Operand.Sp,
                            Operand.Offset(activeScope.getPosition(name))
                        )
                    )
                }
            }
        }
    }
}

fun CodeGenerator.arrayElemAssign(statement: Statement.VariableAssignment, lhs: Expression.ArrayElem) {
    compileExpression(statement.rhs, 4)
    instructions.add(
        Instruction.ADD(
            Operand.Register(5),
            Operand.Sp,
            Operand.Constant(activeScope.getPosition(lhs.array))
        )
    )

    for (i in 0 until lhs.indexes.size) {
        if (i > 0) {
            instructions.add(
                Instruction.ADDCond(
                    Operand.Register(5),
                    Operand.Register(5),
                    Operand.Register(6),
                    "LSL #2"
                )
            )
        }
        compileExpression(lhs.indexes[i], 6)
        instructions.add(
            Instruction.LDRRegister(
                Operand.Register(5),
                Operand.Register(5),
                Operand.Offset(0)
            )
        )
        instructions.add(
            Instruction.MOV(
                Operand.Register(0),
                Operand.Register(6)
            )
        )
        instructions.add(
            Instruction.MOV(
                Operand.Register(1),
                Operand.Register(5)
            )
        )
        instructions.add(Instruction.BL("p_check_array_bounds"))
        checkArrayFlag = true
        throwRuntimeFlag = true
        instructions.add(
            Instruction.ADD(
                Operand.Register(5),
                Operand.Register(5),
                Operand.Constant(4)
            )
        )

    }
    instructions.add(
        Instruction.ADDCond(
            Operand.Register(5),
            Operand.Register(5),
            Operand.Register(6),
            "LSL #2"
        )
    )
    instructions.add(
        Instruction.STRBOffset(
            Operand.Register(4),
            Operand.Register(5),
            Operand.Offset(0)
        )
    )
}

fun CodeGenerator.pairElemAssign(statement: Statement.VariableAssignment, name: String, pairOffset: Int) {
    compileExpression(statement.rhs, 4)
    val offset = activeScope.getPosition(name)
    instructions.addAll(
        arrayListOf(
            Instruction.LDRRegister(Operand.Register(5), Operand.Sp, Operand.Offset(offset)),
            Instruction.MOV(Operand.Register(0), Operand.Register(5)),
            Instruction.BL("p_check_null_pointer"),
            Instruction.LDRRegister(Operand.Register(5), Operand.Register(5), Operand.Offset(pairOffset)),
            Instruction.STROffset(Operand.Register(4), Operand.Register(5), Operand.Offset(0))
        )
    )
    checkNullPointerFlag = true
    throwRuntimeFlag = true
    printStringFlag = true
}


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
    val typeL = (definition.type as Type.TPair).t1
    val typeR = (definition.type as Type.TPair).t2
    val e1 = rhs.e1
    val e2 = rhs.e2

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

    // Storing no. of array elements
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
    // Store array to sp
    val pos = activeScope.getPosition(lhs.name)
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

fun CodeGenerator.freeVariable(statement: Statement.FreeVariable) {
    compileExpression(statement.expression, 4)
    instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
    when {
        Type.compare(statement.expression.exprType, Type.TArray(Type.TAny)) -> {
            printStringFlag = true
            freeArrayFlag = true
            throwRuntimeFlag = true
            instructions.add(Instruction.BL("p_free_array"))
        }

        Type.compare(statement.expression.exprType, Type.TPair(Type.TAny, Type.TAny)) -> {
            printStringFlag = true
            freePairFlag = true
            throwRuntimeFlag = true
            instructions.add(Instruction.BL("p_free_pair"))
        }
    }
}

