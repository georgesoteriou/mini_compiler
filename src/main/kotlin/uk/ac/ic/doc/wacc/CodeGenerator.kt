package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.helpers.*

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

    var printStringFlag = false
    var printIntFlag = false
    var printBoolFlag = false
    var printLnFlag = false
    var printReferenceFlag = false
    var freeArrayFlag = false
    var freePairFlag = false

    fun compile() {
        instructions.add(Instruction.Flag(".global main"))
        //TODO: Add functions to active scope
        compileStatement(program.block, "main")
        instructions.add(Instruction.Flag(".ltorg"))


        if (printStringFlag) {
            messageTagGenerator("%.*s\\0", 1)
            printStringTag = messageCounter - 1
            add_pPrintString(printStringTag)
        }

        if (printBoolFlag) {
            messageTagGenerator("true\\0", 1)
            printBoolTrueTag = messageCounter - 1
            messageTagGenerator("false\\0", 1)
            printBoolFalseTag = messageCounter - 1
            add_pPrintBool(printBoolTrueTag,printBoolFalseTag)
        }

        if (printIntFlag) {
            messageTagGenerator("%d\\0", 1)
            printIntTag = messageCounter - 1
            add_pPrintInt(printIntTag)
        }

        if (printReferenceFlag) {
            messageTagGenerator("%p\\0", 1)
            printReferenceTag = messageCounter - 1
            add_pPrintReference(printReferenceTag)
        }

        if (printLnFlag) {
            messageTagGenerator("\\0", 1)
            printLnTag = messageCounter - 1
            add_pPrintLn(printLnTag)
        }

        if (freeArrayFlag || freePairFlag) {

            if (freeArrayFlag) {
                messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0",2)
                freeArrayTag = messageCounter - 1
                add_freeArray(freeArrayTag)
                add_throwRuntimeError()
            }

            if (freePairFlag) {
                messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0",2)
                freePairTag = messageCounter - 1
                add_freePair(freePairTag)
                add_throwRuntimeError()
            }

            if (!printStringFlag) {
                messageTagGenerator("%.*s\\0", 1)
                printStringTag = messageCounter - 1
                add_pPrintString(printStringTag)
            }

        }


        data.forEach { println(it.toString()) }
        instructions.forEach { println(it.toString()) }
    }

    fun compileStatement(statement: Statement, name: String = ".L$labelCounter") {
        when (statement) {
            is Statement.Block -> {
                statement.scope.findFullSize()
                instructions.add(Instruction.LABEL(name))
                instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))
                activeScope = activeScope.newSubScope(statement.scope)
                decreaseSP(statement)
                statement.statements.forEach { compileStatement(it) }
                labelCounter++
                // TODO add later: increment label counter : if name not like ".L<Int>"
                increaseSP(statement)
                instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
                instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
            }
            is Statement.Skip -> {
            }
            is Statement.VariableDeclaration -> {
                var type = statement.lhs.type
                val name = statement.lhs.name
                when (type) {
                    is Type.TInt -> {
                        compileExpression(statement.rhs, 4)
                        intAssignInstructions(name)
                    }
                    is Type.TBool -> {
                        compileExpression(statement.rhs, 4)
                        boolAssignInstructions(name)
                    }

                    is Type.TChar -> {
                        compileExpression(statement.rhs, 4)
                        charAssignInstructions(name)
                    }

                    is Type.TArray -> {
                        arrayDeclInstructions(statement.lhs, (statement.rhs as Expression.Literal.LArray))
                    }
                    is Type.TPair -> {
                        compileExpression(statement.rhs, 5)
                        pairDeclInstructions(statement)
                        //TODO: deal with null pairs
                    }
                }
                activeScope.declare(name)
            }

            is Statement.VariableAssignment -> {
                var type = statement.rhs.exprType
                compileExpression(statement.rhs, 4)
                val name = (statement.lhs as Expression.Identifier).name
                when (type) {
                    is Type.TInt -> {
                        compileExpression(statement.rhs, 4)
                        intAssignInstructions(name)
                    }
                    is Type.TBool -> {
                        compileExpression(statement.rhs, 4)
                        boolAssignInstructions(name)
                    }
                    is Type.TChar -> {
                        compileExpression(statement.rhs, 4)
                        charAssignInstructions(name)
                    }
                    is Type.TArray -> {
                        //arrayDeclInstructions(statement)
                    }
                    is Type.TPair -> {
                        // pairDeclInstructions(statement)
                        //TODO: deal with null pairs
                    }
                }
            }
            is Statement.ReadInput -> {
            }
            is Statement.FreeVariable -> {
                compileExpression(statement.expression,4)
                instructions.add(Instruction.MOV(Operand.Register(0),Operand.Register(4)))
                when {
                    Type.compare(statement.expression.exprType,Type.TArray(Type.TAny)) -> {
                        printStringFlag = true
                        freeArrayFlag = true
                        instructions.add(Instruction.BL("p_free_array"))
                    }

                    Type.compare(statement.expression.exprType, Type.TPair(Type.TAny,Type.TAny)) -> {
                        printStringFlag = true
                        freePairFlag = true
                        instructions.add(Instruction.BL("p_free_pair"))
                    }
                }
            }
            is Statement.Return -> {
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
            }
            is Statement.If -> {
            }
            is Statement.While -> {
            }
        }

    }

    fun compileExpression(expression: Expression, dest: Int) {
        when (expression) {
            is Expression.CallFunction -> {
            }
            is Expression.NewPair -> {
            }
            is Expression.Identifier -> {
                addPointerLDR(expression, dest)
            }

            is Expression.Literal.LInt -> {
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(dest),
                        Operand.Literal.LInt(expression.int)
                    )
                )
            }
            is Expression.Literal.LBool -> {
                instructions.add(
                    Instruction.MOV(
                        Operand.Register(dest),
                        Operand.Literal.LBool(expression.bool)
                    )
                )
            }
            is Expression.Literal.LChar -> {
                instructions.add(
                    Instruction.MOV(
                        Operand.Register(dest),
                        Operand.Literal.LChar(expression.char)
                    )
                )
            }

            is Expression.Literal.LString -> {
            }
            is Expression.Literal.LArray -> {
//                arrayDeclInstructions()
//                var type = (expression.exprType as Type.TArray).type
//                var offset = 4
//                expression.params.forEach {
//                    compileExpression(it, dest + 1)
//                }
//                offset += Type.size(type)
            }
            is Expression.Literal.LPair -> {
            }

            is Expression.BinaryOperation -> {
            }
            is Expression.UnaryOperation -> {
                when (expression.operator) {
                    Expression.UnaryOperator.MINUS -> {
                        instructions.add(
                            Instruction.LDRSimple(
                                Operand.Register(dest),
                                Operand.Literal.LInt(
                                    "-${(expression.expression as Expression.Literal.LInt).int}"
                                )
                            )
                        )
                    }
                    Expression.UnaryOperator.CHR,
                    Expression.UnaryOperator.LEN,
                    Expression.UnaryOperator.NOT,
                    Expression.UnaryOperator.ORD -> {
                    }
                }
            }

            is Expression.ArrayElem -> {
            }
            is Expression.Fst -> {
            }
            is Expression.Snd -> {
            }
        }
    }

    private fun increaseSP(statement: Statement.Block) {
        var declarationsSize = statement.scope.fullSize
        for (i in 1..statement.scope.fullSize step 1024) {
            instructions.add(
                Instruction.ADD(
                    Operand.Sp, Operand.Sp, Operand.Offset(
                        if (declarationsSize > 1024) {
                            declarationsSize -= 1024
                            1024
                        } else {
                            declarationsSize
                        }
                    )
                )
            )
        }
    }

    private fun decreaseSP(statement: Statement.Block): Int {
        var declarationsSize = statement.scope.fullSize
        for (i in 1..statement.scope.fullSize step 1024) {
            instructions.add(
                Instruction.SUB(
                    Operand.Sp, Operand.Sp, Operand.Offset(
                        if (declarationsSize > 1024) {
                            declarationsSize -= 1024
                            1024
                        } else {
                            declarationsSize
                        }
                    )
                )
            )
        }
        return declarationsSize
    }


}