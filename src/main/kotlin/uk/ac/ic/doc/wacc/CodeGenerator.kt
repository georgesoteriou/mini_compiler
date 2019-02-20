package uk.ac.ic.doc.wacc


import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.*

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()
    var data: MutableList<Instruction> = arrayListOf()
    var activeScope = ActiveScope(Scope(), null)

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
                instructions.add(Instruction.LDR(Operand.Register(0), Operand.Literal.LInt("0")))
                instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
            }
            is Statement.Skip -> {
            }
            is Statement.VariableDeclaration -> {
                when (statement.lhs.type) {
                    is Type.TInt -> {
                        instructions.add(Instruction.LDR(
                            Operand.Register(4),
                            Operand.Literal.LInt((statement.rhs as Expression.Literal.LInt).int)
                        ))
                        instructions.add(Instruction.STR(
                            Operand.Register(4),
                            Operand.Sp,
                            Operand.Offset(activeScope.getPosition(statement.lhs.name))
                        ))
                        activeScope.declare(statement.lhs.name)
                    }
                    is Type.TBool -> {
                        instructions.add(Instruction.MOV(
                            Operand.Register(4),
                            Operand.Literal.LBool((statement.rhs as Expression.Literal.LBool).bool)
                        ))
                        instructions.add(Instruction.STRB(
                            Operand.Register(4),
                            Operand.Sp,
                            Operand.Offset(activeScope.getPosition(statement.lhs.name))
                        ))
                    }
                    is Type.TChar -> {
                        instructions.add(Instruction.MOV(
                            Operand.Register(4),
                            Operand.Literal.LChar((statement.rhs as Expression.Literal.LChar).char)
                        ))
                        instructions.add(Instruction.STRB(
                            Operand.Register(4),
                            Operand.Sp,
                            Operand.Offset(activeScope.getPosition(statement.lhs.name))
                        ))
                    }
                }
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
                    Instruction.LDR(
                        Operand.Register(4),
                        Operand.Literal.LInt(expression.int)
                    )
                )
            }
            is Expression.Literal.LBool -> {
                instructions.add(
                    Instruction.LDR(
                        Operand.Register(4),
                        Operand.Literal.LBool(expression.bool)
                    )
                )
            }
            is Expression.Literal.LChar -> {
                instructions.add(
                    Instruction.LDR(
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
                            Instruction.LDR(
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