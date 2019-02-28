package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.helpers.*
import java.io.File
import java.lang.Exception
import kotlin.math.exp

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()
    var data: MutableList<Instruction> = arrayListOf()
    var wholeProgram: MutableList<Instruction> = arrayListOf()
    var activeScope = ActiveScope(Scope(), null)
    var messageCounter = 0

    var printStringTag = -1
    var printBoolTrueTag = -1
    var printBoolFalseTag = -1
    var printIntTag = -1
    var printReferenceTag = -1
    var printLnTag = -1
    var freeArrayTag = -1
    var freePairTag = -1
    var intInputTag = -1
    var charInputTag = -1
    var throwOverflowTag = -1
    var divideByZeroTag = -1
    var checkArrayOutOfBoundsTag = -1
    var checkArrayNegativeBoundsTag = -1


    // TODO: consider refactoring so as to avoid use of so many flags and corresponding tags


    var printStringFlag = false
    var printIntFlag = false
    var printBoolFlag = false
    var printLnFlag = false
    var printReferenceFlag = false
    var freeArrayFlag = false
    var freePairFlag = false
    var intInputFlag = false
    var charInputFlag = false
    var throwOverflowFlag = false
    var throwRuntimeFlag = false
    var divideByZeroFlag = false
    var checkArrayFlag = false

    var currentBlock : Statement.Block? = null

    fun compile(filename: String) {
        instructions.add(Instruction.Flag(".text"))
        instructions.add(Instruction.Flag(".global main"))
        program.functions.forEach{
            currentBlock = it.block
            compileBlock(it.name, it.block, it.params)
        }
        currentBlock = program.block
        compileBlock("main", program.block)

        dataGenerator()
        val file = File("$filename.s")

        if (!data.isEmpty()) {
            file.writeText(".data\n")
        }

        data.forEach { file.appendText(it.toString() + "\n") }

        instructions.forEach { file.appendText(it.toString() + "\n") }
    }

    fun compileStatement(statement: Statement, name: String = ".L$labelCounter") {
        when (statement) {
            is Statement.Block -> {
                compileBlock("", statement)
            }
            is Statement.Skip -> {
            }
            is Statement.VariableDeclaration -> {
                var type = statement.lhs.type
                val name = statement.lhs.name
                if (statement.rhs is Expression.Literal.LPair) {
                    pairNullInstructions()
                } else {
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
                            arrayAssignInstructions(statement.lhs, (statement.rhs as Expression.Literal.LArray))
                        }
                        is Type.TPair -> {
                            pairAssignInstructions(statement.lhs, (statement.rhs as Expression.Literal.LPair))
                        }
                    }
                }
                activeScope.declare(name)
            }

            is Statement.VariableAssignment -> {
                var lhs = statement.lhs
                when (lhs) {
                    is Expression.Identifier -> {
                        var type = statement.rhs.exprType
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
                                pairAssignInstructions(def, statement.rhs as Expression.Literal.LPair)
                            }
                        }
                    }

                    is Expression.ArrayElem -> {
                        compileExpression(statement.rhs, 4)
                        instructions.add(Instruction.ADD(Operand.Register(5),Operand.Sp,Operand.Constant(activeScope.getPosition(lhs.array))))

                        for ( i in 0 until lhs.indexes.size) {
                            if ( i > 0) {
                                instructions.add(Instruction.ADDCond(Operand.Register(5),Operand.Register(5),Operand.Register(6),"LSL #2"))
                            }
                            compileExpression(lhs.indexes[i],6)
                            instructions.add(Instruction.LDRRegister(Operand.Register(5),Operand.Register(5),Operand.Offset(0)))
                            instructions.add(Instruction.MOV(Operand.Register(0),Operand.Register(6)))
                            instructions.add(Instruction.MOV(Operand.Register(1),Operand.Register(5)))
                            instructions.add(Instruction.BL("p_check_array_bounds"))
                            checkArrayFlag = true
                            throwRuntimeFlag = true
                            instructions.add(Instruction.ADD(Operand.Register(5),Operand.Register(5),Operand.Constant(4)))

                        }
                        instructions.add(Instruction.ADD(Operand.Register(5),Operand.Register(5),Operand.Register(6)))
                        instructions.add(Instruction.STRBOffset(Operand.Register(4),Operand.Register(5),Operand.Offset(0)))
                    }
                }


            }
            is Statement.ReadInput -> {
                instructions.add(Instruction.ADD(Operand.Register(4), Operand.Sp, Operand.Constant(activeScope.getPosition((statement.expression as Expression.Identifier).name))))
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

                //increaseSP(currentBlock!!)
               // instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
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
        //TODO: if dest > 14. We have a problem. Add an if here to add regs to stack maybe
        var dest = dst
        if(dst > 10) {
            dest = 10
        }
        when (expression) {
            is Expression.CallFunction -> {
                // TODO: Add jump to function
                // TODO: MOVE r0 to r4

                pushArgsToStack(expression)
                instructions.add(
                    Instruction.BL(expression.name))
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
            is Expression.NewPair -> {
                // TODO: Remove. Unused (Add else -> {})
            }
            is Expression.Identifier -> {
                addPointerLDR(expression, dest)
            }

            is Expression.Literal.LInt -> {
                if(dst > 10) {
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
                if(dst > 10) {
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
                if(dst > 10) {
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
            is Expression.Literal.LArray -> {
                // TODO: Remove. Unused (Add else -> {})
            }
            is Expression.Literal.LPair -> {
                // TODO: Remove. Unused (Add else -> {})
            }

            is Expression.BinaryOperation -> {
                val e1 = expression.e1
                val e2 = expression.e2


                compileExpression(e1, dst)
                compileExpression(e2, dst + 1)
                if(dst >= 10) {
                    instructions.add(Instruction.POP(arrayListOf(Operand.Register(11))))
                }
                binOpInstructions(expression, dest)
            }
            is Expression.UnaryOperation -> {
                // TODO: Here we want to calculate "{unop} A"
                // TODO: very similar to above.
                // TODO: compileExpression(A, dest+1)
                compileExpression(expression.expression, dest)
                unOpInstructions(expression, dest)

            }

            // TODO: This will need more thinking as it can be both on lhs and rhs.
            // TODO: You might want to ignore this and use your assign/declare functions for lhs.
            // TODO: And only use the functions below for the rhs to lookup values in the scope/stack
            is Expression.ArrayElem -> {
                instructions.add(Instruction.ADD(Operand.Register(4),Operand.Sp,Operand.Constant(activeScope.getPosition(expression.array))))

                for ( i in 0 until expression.indexes.size) {
                    compileExpression(expression.indexes[i],5)
                    instructions.add(Instruction.LDRRegister(Operand.Register(4),Operand.Register(4),Operand.Offset(0)))
                    instructions.add(Instruction.MOV(Operand.Register(0),Operand.Register(5)))
                    instructions.add(Instruction.MOV(Operand.Register(1),Operand.Register(4)))
                    instructions.add(Instruction.BL("p_check_array_bounds"))
                    checkArrayFlag = true
                    throwRuntimeFlag = true
                    printStringFlag = true
                    instructions.add(Instruction.ADD(Operand.Register(4),Operand.Register(4),Operand.Constant(4)))
                    instructions.add(Instruction.ADDCond(Operand.Register(4),Operand.Register(4),Operand.Register(6),"LSL #2"))

                }
                instructions.add(Instruction.LDRRegister(Operand.Register(4),Operand.Register(4),Operand.Offset(0)))



            }
            is Expression.Fst -> {
            }
            is Expression.Snd -> {
            }
        }
    }


}