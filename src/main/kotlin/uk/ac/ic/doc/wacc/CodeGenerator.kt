package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*
import java.lang.IllegalArgumentException

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()
    var data: MutableList<Instruction> = arrayListOf()
    var activeScope = ActiveScope(Scope(), null)

    var printString = false
    var printInt = false
    var printBool = false
    var printLn = false
    var printReference = false
    fun compile() {
        instructions.add(Instruction.Flag(".global main"))
        //TODO: Add functions to active scope
        compileStatement(program.block, "main")
        instructions.add(Instruction.Flag(".ltorg"))
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
                when (type) {
                    is Type.TInt -> {
                        instructions.add(
                            Instruction.LDRSimple(
                                Operand.Register(4),
                                Operand.Literal.LInt((statement.rhs as Expression.Literal.LInt).int)
                            )
                        )
                        var pos = activeScope.getPosition(statement.lhs.name)
                        if (pos != 0) {
                            instructions.add(
                                Instruction.STROffset(
                                    Operand.Register(4),
                                    Operand.Sp,
                                    Operand.Offset(pos)
                                )
                            )
                        } else {
                            instructions.add(
                                Instruction.STRSimple(
                                    Operand.Register(4),
                                    Operand.Sp
                                )
                            )
                        }
                        activeScope.declare(statement.lhs.name)
                    }
                    is Type.TBool -> {
                        instructions.add(
                            Instruction.MOV(
                                Operand.Register(4),
                                Operand.Literal.LBool((statement.rhs as Expression.Literal.LBool).bool)
                            )
                        )
                        instructions.add(
                            Instruction.STRB(
                                Operand.Register(4),
                                Operand.Sp,
                                Operand.Offset(activeScope.getPosition(statement.lhs.name))
                            )
                        )
                    }
                    is Type.TChar -> {
                        instructions.add(
                            Instruction.MOV(
                                Operand.Register(4),
                                Operand.Literal.LChar((statement.rhs as Expression.Literal.LChar).char)
                            )
                        )
                        instructions.add(
                            Instruction.STRB(
                                Operand.Register(4),
                                Operand.Sp,
                                Operand.Offset(activeScope.getPosition(statement.lhs.name))
                            )
                        )
                    }
                    is Type.TArray -> {
                        instructions.add(
                            Instruction.LDRSimple(
                                Operand.Register(0),
                                Operand.Literal.LInt(
                                    (((statement.rhs as Expression.Literal.LArray).params.size) *
                                            Type.size(statement.lhs.type) + 4).toString()
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
                        var offset = Type.size(statement.lhs.type)
                        var type = (statement.rhs.exprType as Type.TArray).type
                        (statement.rhs as Expression.Literal.LArray).params.forEach {
                            when (type) {
                                is Type.TArray -> {
                                    var pos = activeScope.getPosition((it as Expression.Identifier).name)
                                    instructions.add(
                                        Instruction.LDRRegister(
                                            Operand.Register(5),
                                            Operand.Sp,
                                            Operand.Offset(pos)
                                        )
                                    )
                                    instructions.add(
                                        Instruction.STROffset(
                                            Operand.Register(5),
                                            Operand.Register(4),
                                            Operand.Offset(offset)
                                        )
                                    )
                                }
                                is Type.TPair -> {
                                    //TODO: Implement This
                                }
                                else -> {
                                    instructions.add(
                                        Instruction.LDRSimple(
                                            Operand.Register(5),
                                            when (type) {
                                                is Type.TInt -> Operand.Literal.LInt((it as Expression.Literal.LInt).int)
                                                is Type.TBool -> Operand.Literal.LBool((it as Expression.Literal.LBool).bool)
                                                is Type.TChar -> Operand.Literal.LChar((it as Expression.Literal.LChar).char)
                                                else -> throw IllegalArgumentException()
                                            }
                                        )
                                    )

                                    instructions.add(
                                        Instruction.STROffset(
                                            Operand.Register(5),
                                            Operand.Register(4),
                                            Operand.Offset(offset)
                                        )
                                    )
                                }
                            }
                            offset += Type.size(statement.lhs.type)
                        }
                    }

                }
                //Storing no. of array elems
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(5),
                        Operand.Literal.LInt((statement.rhs as Expression.Literal.LArray).params.size.toString())
                    )
                )
                instructions.add(
                    Instruction.STRSimple(
                        Operand.Register(5),
                        Operand.Register(4)
                    )
                )
                //Store array to sp
                var pos = activeScope.getPosition(statement.lhs.name)
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

            is Statement.VariableAssignment -> {
            }
            is Statement.ReadInput -> {
            }
            is Statement.FreeVariable -> {
            }
            is Statement.Return -> {
            }
            is Statement.Exit -> {
                compileExpression(statement.expression)
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
                instructions.add(Instruction.BL("exit"))
            }
            is Statement.Print -> {
            }
            is Statement.PrintLn -> {
            }
            is Statement.If -> {
            }
            is Statement.While -> {
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

    fun compileExpression(expression: Expression) {
        when (expression) {
            is Expression.CallFunction -> {
            }
            is Expression.NewPair -> {
            }
            is Expression.Identifier -> {
            }

            is Expression.Literal.LInt -> {
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(4),
                        Operand.Literal.LInt(expression.int)
                    )
                )
            }
            is Expression.Literal.LBool -> {
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(4),
                        Operand.Literal.LBool(expression.bool)
                    )
                )
            }
            is Expression.Literal.LChar -> {
                instructions.add(
                    Instruction.LDRSimple(
                        Operand.Register(4),
                        Operand.Literal.LChar(expression.char)
                    )
                )
            }
            is Expression.Literal.LString -> {
            }
            is Expression.Literal.LArray -> {
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
                                Operand.Register(4),
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
}