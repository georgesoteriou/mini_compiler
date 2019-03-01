package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.helpers.*
import java.io.File

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()
    var data: MutableList<Instruction> = arrayListOf()
    var activeScope = ActiveScope(Scope(), null)


    var currentBlock: Statement.Block? = null

    fun compile(filename: String) {
        instructions.add(Instruction.Flag(".text"))
        instructions.add(Instruction.Flag(".global main"))
        program.functions.forEach {
            currentBlock = it.block
            compileBlock(it.name, it.block, true, it.params)
        }
        currentBlock = program.block
        compileBlock("main", program.block, false)

        dataGenerator()
        val file = File("$filename.s")

        if (!data.isEmpty()) {
            file.writeText(".data\n")
        }

        data.forEach { file.appendText(it.toString() + "\n") }

        instructions.forEach { file.appendText(it.toString() + "\n") }
    }

    fun compileStatement(statement: Statement) {
        when (statement) {
            is Statement.Block -> {
                compileBlock("", statement, false)
            }
            is Statement.Skip -> {
            }
            is Statement.VariableDeclaration -> {
                var type = statement.lhs.type
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

            is Statement.VariableAssignment -> {
                val lhs = statement.lhs
                when (lhs) {
                    is Expression.Identifier -> identifierAssign(statement, lhs)
                    is Expression.ArrayElem -> arrayElemAssign(statement, lhs)
                    is Expression.Fst -> pairElemAssign(statement, lhs.expression.name, 0)
                    is Expression.Snd -> pairElemAssign(statement, lhs.expression.name, 4)
                }
            }
            is Statement.ReadInput -> {
                val expr = statement.expression
                when (expr) {
                    is Expression.Identifier -> {
                        val offset = activeScope.getPosition(expr.name)
                        instructions.add(Instruction.ADD(Operand.Register(4), Operand.Sp, Operand.Constant(offset)))
                    }
                    is Expression.Snd -> {
                        compileExpression(expr, 4)
                        instructions.removeAt(instructions.size - 1)
                    }
                    is Expression.Fst -> {
                        compileExpression(expr, 4)
                        instructions.removeAt(instructions.size - 1)
                    }
                }
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))

                when {
                    Type.compare(statement.expression.exprType, Type.TInt) -> {
                        instructions.add(Instruction.BL("p_read_int"))
                        intInputFlag = true
                    }

                    Type.compare(statement.expression.exprType, Type.TChar) -> {
                        instructions.add(Instruction.BL("p_read_char"))
                        charInputFlag = true
                    }
                }
            }
            is Statement.FreeVariable -> {
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
            is Statement.Return -> {
                compileExpression(statement.expression, 4)
                instructions.add(
                    Instruction.MOV(
                        Operand.Register(0),
                        Operand.Register(4)
                    )
                )

                increaseSP(currentBlock!!)
                instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
            }
            is Statement.Exit -> {
                compileExpression(statement.expression, 4)
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
                instructions.add(Instruction.BL("exit"))
            }
            is Statement.Print -> {
                compileExpression(statement.expression, 4)
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
                printTypeInstructions(statement.expression)
            }
            is Statement.PrintLn -> {
                printLnFlag = true
                compileExpression(statement.expression, 4)
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
                printTypeInstructions(statement.expression)
                instructions.add(Instruction.BL("p_print_ln"))

            }
            is Statement.If -> {
                compileExpression(statement.condition, 4)
                ifInstructions(statement)
            }
            is Statement.While -> {
                whileInstructions(statement)
            }
        }

    }

    fun compileExpression(expression: Expression, dst: Int) {
        var dest = dst
        if (dst > 10) {
            dest = 10
        }
        when (expression) {
            is Expression.CallFunction -> {

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
            is Expression.Identifier -> {
                addPointerLDR(expression, dest)
            }

            is Expression.Literal.LInt -> {
                if (dst > 10) {
                    instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(10))))
                }
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(dest),
                        Operand.Literal.LInt(expression.int)
                    )
                )
            }
            is Expression.Literal.LBool -> {
                if (dst > 10) {
                    instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(10))))
                }
                instructions.add(
                    Instruction.MOV(
                        Operand.Register(dest),
                        Operand.Literal.LBool(expression.bool)
                    )
                )
            }
            is Expression.Literal.LChar -> {
                if (dst > 10) {
                    instructions.add(Instruction.PUSH(arrayListOf(Operand.Register(10))))
                }
                instructions.add(
                    Instruction.MOV(
                        Operand.Register(dest),
                        Operand.Literal.LChar(expression.char)
                    )
                )
            }

            is Expression.Literal.LString -> {
                messageTagGenerator(expression.string)
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(dest),
                        Operand.MessageTag(messageCounter - 1)
                    )
                )
            }
            is Expression.Literal.LPair -> {
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(dest),
                        Operand.Literal.LInt("0")
                    )
                )
            }

            is Expression.BinaryOperation -> {
                val e1 = expression.e1
                val e2 = expression.e2

                compileExpression(e1, dst)
                compileExpression(e2, dst + 1)
                if (dst >= 10) {
                    instructions.add(Instruction.POP(arrayListOf(Operand.Register(11))))
                }
                binOpInstructions(expression, dest)
            }
            is Expression.UnaryOperation -> {
                compileExpression(expression.expression, dest)
                unOpInstructions(expression, dest)

            }
            is Expression.ArrayElem -> {
                instructions.add(
                    Instruction.ADD(
                        Operand.Register(4),
                        Operand.Sp,
                        Operand.Constant(activeScope.getPosition(expression.array))
                    )
                )

                for (i in 0 until expression.indexes.size) {
                    compileExpression(expression.indexes[i], 5)
                    instructions.add(
                        Instruction.LDRRegister(
                            Operand.Register(4),
                            Operand.Register(4),
                            Operand.Offset(0)
                        )
                    )
                    instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(5)))
                    instructions.add(Instruction.MOV(Operand.Register(1), Operand.Register(4)))
                    instructions.add(Instruction.BL("p_check_array_bounds"))
                    checkArrayFlag = true
                    throwRuntimeFlag = true
                    printStringFlag = true
                    instructions.add(Instruction.ADD(Operand.Register(4), Operand.Register(4), Operand.Constant(4)))
                    if (Type.size(expression.exprType) != 1) {
                        instructions.add(
                            Instruction.ADDCond(
                                Operand.Register(4),
                                Operand.Register(4),
                                Operand.Register(5),
                                "LSL #2"
                            )
                        )
                    } else {
                        instructions.add(
                            Instruction.ADD(
                                Operand.Register(4),
                                Operand.Register(4),
                                Operand.Register(5)
                            )
                        )
                    }
                }
                if (Type.size(expression.exprType) != 1) {
                    instructions.add(
                        Instruction.LDRRegister(
                            Operand.Register(4),
                            Operand.Register(4),
                            Operand.Offset(0)
                        )
                    )
                } else {
                    instructions.add(
                        Instruction.LDRRegCond(
                            Operand.Register(4),
                            Operand.Register(4),
                            Operand.Offset(0),
                            "SB"
                        )
                    )
                }
            }
            is Expression.Fst -> {
                val offset = activeScope.getPosition((expression.expression).name)
                instructions.addAll(
                    arrayListOf(
                        Instruction.LDRRegister(Operand.Register(4), Operand.Sp, Operand.Offset(offset)),
                        Instruction.MOV(Operand.Register(0), Operand.Register(4)),
                        Instruction.BL("p_check_null_pointer"),
                        Instruction.LDRRegister(Operand.Register(4), Operand.Register(4), Operand.Offset(0)),
                        Instruction.LDRRegister(Operand.Register(4), Operand.Register(4), Operand.Offset(0))
                    )
                )
                checkNullPointerFlag = true
                throwRuntimeFlag = true
                printStringFlag = true
            }
            is Expression.Snd -> {
                val offset = activeScope.getPosition((expression.expression).name)
                instructions.addAll(
                    arrayListOf(
                        Instruction.LDRRegister(Operand.Register(4), Operand.Sp, Operand.Offset(offset)),
                        Instruction.MOV(Operand.Register(0), Operand.Register(4)),
                        Instruction.BL("p_check_null_pointer"),
                        Instruction.LDRRegister(Operand.Register(4), Operand.Register(4), Operand.Offset(4))
                    )
                )

                if (Type.size(expression.expression.exprType) != 1) {
                    instructions.add(
                        Instruction.LDRRegister(
                            Operand.Register(4),
                            Operand.Register(4),
                            Operand.Offset(0)
                        )
                    )
                } else {
                    instructions.add(
                        Instruction.LDRRegCond(
                            Operand.Register(4),
                            Operand.Register(4),
                            Operand.Offset(0),
                            "SB"
                        )
                    )
                }
                checkNullPointerFlag = true
                throwRuntimeFlag = true
                printStringFlag = true

            }
            else -> {
            }
        }
    }


}