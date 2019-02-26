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

    fun compile(filename: String) {
        instructions.add(Instruction.Flag(".text"))
        instructions.add(Instruction.Flag(".global main"))
        //TODO: Add functions to active scope

        //TODO: function foreach loop
        instructions.add(Instruction.LABEL("main"))
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
            add_pPrintBool(printBoolTrueTag, printBoolFalseTag)
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

        if (intInputFlag) {
            messageTagGenerator("%d\\0", 1)
            intInputTag = messageCounter - 1
            add_intInput(intInputTag)
        }

        if (charInputFlag) {
            messageTagGenerator("%c\\0", 1)
            charInputTag = messageCounter - 1
            add_charInput(charInputTag)
        }

        if (throwOverflowFlag) {
            messageTagGenerator(
                "OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n",
                1
            )
            throwOverflowTag = messageCounter - 1
            add_throwOverflowError(throwOverflowTag)
        }

        if (throwRuntimeFlag) {
            add_throwRuntimeError()
            throwRuntimeFlag = false
        }

        if (divideByZeroFlag) {
            messageTagGenerator("DivideByZeroError: divide or modulo by zero\\n\\0", 2)
            divideByZeroTag = messageCounter - 1
            add_checkDivideByZero(divideByZeroTag)
        }

        if (freeArrayFlag || freePairFlag) {

            if (freeArrayFlag) {
                messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0", 2)
                freeArrayTag = messageCounter - 1
                add_freeArray(freeArrayTag)
                if (throwRuntimeFlag) {
                    add_throwRuntimeError()
                }
            }

            if (freePairFlag) {
                messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0", 2)
                freePairTag = messageCounter - 1
                add_freePair(freePairTag)
                if (throwRuntimeFlag) {
                    add_throwRuntimeError()
                }
            }

            if (!printStringFlag) {
                messageTagGenerator("%.*s\\0", 1)
                printStringTag = messageCounter - 1
                add_pPrintString(printStringTag)
            }

        }

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
                statement.scope.findFullSize()
                //instructions.add(Instruction.LABEL(name))
                if (name == "main") {
                    instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))
                }
                activeScope = activeScope.newSubScope(statement.scope)
                decreaseSP(statement)
                statement.statements.forEach { compileStatement(it) }
                // TODO add later: increment label counter : if name not like ".L<Int>"
                // TODO i don't think label counter should be handles here
                increaseSP(statement)

                if (name == "main") {
                    instructions.add(Instruction.LDRSimple(Operand.Register(0), Operand.Literal.LInt("0")))
                    instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
                }
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
                var type = statement.rhs.exprType
                val name = (statement.lhs as Expression.Identifier).name
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
                        val def = Definition(name, statement.lhs.exprType)
                        arrayAssignInstructions(def, statement.rhs as Expression.Literal.LArray)
                    }
                    is Type.TPair -> {
                        val def = Definition(name, statement.lhs.exprType)
                        pairAssignInstructions(def, statement.rhs as Expression.Literal.LPair)
                    }
                }
            }
            is Statement.ReadInput -> {
                //compileExpression(statement.expression,4)
                instructions.add(Instruction.ADD(Operand.Register(4), Operand.Sp, Operand.Constant(0)))
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
                compileExpression(statement.condition, 4)
                ifInstructions(statement)
            }
            is Statement.While -> {
                // TODO: Create label L<labelCounter + 1> with block commands
                // TODO: Create label L<labelCounter> with git cond

                whileInstructions(statement)
            }
        }

    }

    fun compileExpression(expression: Expression, dest: Int) {
        //TODO: if dest > 14. We have a problem. Add an if here to add regs to stack maybe
        when (expression) {
            is Expression.CallFunction -> {
                // TODO: Add jump to function
                // TODO: MOVE r0 to r4
            }
            is Expression.NewPair -> {
                // TODO: Remove. Unused (Add else -> {})
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

                if (weight(expression.e1) >= weight(expression.e2)) {
                    compileExpression(e1, dest)
                    compileExpression(e2, dest + 1)
                } else {
                    compileExpression(e2, dest + 1)
                    compileExpression(e1, dest)
                }

                binOpInstructions(expression, dest)

                // TODO: remember to generate message of possible error
            }
            is Expression.UnaryOperation -> {
                // TODO: Here we want to calculate "{unop} A"
                // TODO: very similar to above.
                // TODO: compileExpression(A, dest+1)
                //compileExpression(expression.expression, dest)
                //unOpInstructions(expression, dest)

            }

            // TODO: This will need more thinking as it can be both on lhs and rhs.
            // TODO: You might want to ignore this and use your assign/declare functions for lhs.
            // TODO: And only use the functions below for the rhs to lookup values in the scope/stack
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