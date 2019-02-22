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
    var printString = false
    var printInt = false
    var printBool = false
    var printLnTag = false
    var printReference = false
    var freeArray = false
    var freePair = false
    fun compile() {
        instructions.add(Instruction.Flag(".global main"))
        //TODO: Add functions to active scope
        compileStatement(program.block, "main")
        instructions.add(Instruction.Flag(".ltorg"))


        if (printString) {
            messageTagGenerator("%.*s\\0", true)
            add_pPrintString(messageCounter - 1)
        }

        if (printBool) {
            messageTagGenerator("true\\0", true)
            messageTagGenerator("false\\0", true)
            add_pPrintBool(messageCounter - 1)
        }

        if (printInt) {
            messageTagGenerator("%d\\0", true)
            add_pPrintInt(messageCounter - 1)
        }

        if (printReference) {
            messageTagGenerator("%p\\0", true)
            add_pPrintReference(messageCounter - 1)
        }

        if (printLnTag) {
            messageTagGenerator("\\0", true)

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
                printLnTag = true
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