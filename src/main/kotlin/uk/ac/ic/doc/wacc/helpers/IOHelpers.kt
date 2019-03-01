package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.ast.Type

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
var checkNullPointerTag = -1

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
var checkNullPointerFlag = false

fun CodeGenerator.messageTagGenerator(content: String, numEscChars: Int = 0) {
    // Generates msgs to be put in the data section of the assembly code
    var length: Int = content.length
    var escInString = 0
    if (numEscChars == 0) {
        for (i in 0..content.length - 2) {
            if (content[i] == '\\') {
                if (i > 0) {
                    if (content[i - 1] != '\\' || content[i + 1] != '\\') {
                        escInString++
                    }
                } else if (i == 0) {
                    if (content[i + 1] != '\\') {
                        escInString++
                    }
                }
            }
        }
    }
    length -= escInString
    length -= numEscChars
    data.add(Instruction.Flag("msg_$messageCounter:"))
    data.add(Instruction.WORD(length))
    data.add(Instruction.ASCII(content))
    messageCounter += 1
}

fun CodeGenerator.addThrowOverflowError(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_throw_overflow_error"),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.BL("p_throw_runtime_error")
        )
    )
}

fun CodeGenerator.addThrowRuntimeError() {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_throw_runtime_error"),
            Instruction.BL("p_print_string"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(-1)),
            Instruction.BL("exit")
        )
    )
}

fun CodeGenerator.addCheckDivideByZero(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_check_divide_by_zero"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(1), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(tagValue), "EQ"),
            Instruction.BCond("p_throw_runtime_error", "LEQ"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}


fun CodeGenerator.addPrintString(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_string"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.LDRRegister(
                Operand.Register(1),
                Operand.Register(0),
                Operand.Offset(0)
            ),
            Instruction.ADD(
                Operand.Register(2),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.LDRSimple(
                Operand.Register(0),
                Operand.MessageTag(tagValue)
            ),
            Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.BL("printf"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(0)),
            Instruction.BL("fflush"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addPrintBool(trueTagValue: Int, falseTagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_bool"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(trueTagValue), "NE"),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(falseTagValue), "EQ"),
            Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.BL("printf"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(0)),
            Instruction.BL("fflush"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addPrintInt(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_int"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1), Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.BL("printf"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(0)),
            Instruction.BL("fflush"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addPrintReference(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_reference"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1), Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.BL("printf"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(0)),
            Instruction.BL("fflush"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addPrintLn(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_ln"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            ),
            Instruction.BL("puts"),
            Instruction.MOV(Operand.Register(0), Operand.Constant(0)),
            Instruction.BL("fflush"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addFreeArray(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_free_array"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(tagValue), "EQ"),
            Instruction.BCond("p_throw_runtime_error", "EQ"),
            Instruction.BL("free"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addFreePair(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_free_pair"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(tagValue), "EQ"),
            Instruction.BCond("p_throw_runtime_error", "EQ"),
            Instruction.PUSH(arrayListOf(Operand.Register(0))),
            Instruction.LDRRegister(Operand.Register(0), Operand.Register(0), Operand.Offset(0)),
            Instruction.BL("free"),
            Instruction.LDRRegister(Operand.Register(0), Operand.Sp, Operand.Offset(0)),
            Instruction.LDRRegister(Operand.Register(0), Operand.Register(0), Operand.Offset(4)),
            Instruction.BL("free"),
            Instruction.POP(arrayListOf(Operand.Register(0))),
            Instruction.BL("free"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}


fun CodeGenerator.addCharInput(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_read_char"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1), Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.ADD(Operand.Register(0), Operand.Register(0), Operand.Constant(4)),
            Instruction.BL("scanf"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addIntInput(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_read_int"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1), Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue)),
            Instruction.ADD(Operand.Register(0), Operand.Register(0), Operand.Constant(4)),
            Instruction.BL("scanf"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addCheckNullPointer(tagValue: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_check_null_pointer"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(tagValue), "EQ"),
            Instruction.BCond("p_throw_runtime_error", "LEQ"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.addCheckArrayOutOfBounds(indexTooLarge: Int, negativeIndex: Int) {
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_check_array_bounds"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(negativeIndex), "LT"),
            Instruction.BCond("p_throw_runtime_error", "LLT"),
            Instruction.LDRRegister(Operand.Register(1), Operand.Register(1), Operand.Offset(0)),
            Instruction.CMP(Operand.Register(0), Operand.Register(1)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(indexTooLarge), "CS"),
            Instruction.BCond("p_throw_runtime_error", "CS"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )

}

fun CodeGenerator.printInstructions(expression: Expression) {
    compileExpression(expression, 4)
    instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
    when {
        Type.compare(expression.exprType, Type.TArray(Type.TChar)) -> {
            printStringFlag = true
            instructions.add(Instruction.BL("p_print_string"))
        }

        Type.compare(expression.exprType, Type.TArray(Type.TAny)) ||
                Type.compare(expression.exprType, Type.TPair(Type.TAny, Type.TAny)) -> {
            printReferenceFlag = true
            instructions.add(Instruction.BL("p_print_reference"))
        }

        Type.compare(expression.exprType, Type.TChar) -> {
            instructions.add(Instruction.BL("putchar"))

        }

        Type.compare(expression.exprType, Type.TString) -> {
            printStringFlag = true
            instructions.add(Instruction.BL("p_print_string"))
        }

        Type.compare(expression.exprType, Type.TInt) -> {
            printIntFlag = true
            instructions.add(Instruction.BL("p_print_int"))
        }

        Type.compare(expression.exprType, Type.TBool) -> {
            printBoolFlag = true
            instructions.add(Instruction.BL("p_print_bool"))
        }
    }
}

fun CodeGenerator.printlnInstructions(expression: Expression) {
    printLnFlag = true
    printInstructions(expression)
    instructions.add(Instruction.BL("p_print_ln"))
}


fun CodeGenerator.dataGenerator() {
    if (printStringFlag) {
        messageTagGenerator("%.*s\\0", 1)
        printStringTag = messageCounter - 1
        addPrintString(printStringTag)
    }

    if (printBoolFlag) {
        messageTagGenerator("true\\0", 1)
        printBoolTrueTag = messageCounter - 1
        messageTagGenerator("false\\0", 1)
        printBoolFalseTag = messageCounter - 1
        addPrintBool(printBoolTrueTag, printBoolFalseTag)
    }

    if (printIntFlag) {
        messageTagGenerator("%d\\0", 1)
        printIntTag = messageCounter - 1
        addPrintInt(printIntTag)
    }

    if (printReferenceFlag) {
        messageTagGenerator("%p\\0", 1)
        printReferenceTag = messageCounter - 1
        addPrintReference(printReferenceTag)
    }

    if (printLnFlag) {
        messageTagGenerator("\\0", 1)
        printLnTag = messageCounter - 1
        addPrintLn(printLnTag)
    }

    if (intInputFlag) {
        messageTagGenerator("%d\\0", 1)
        intInputTag = messageCounter - 1
        addIntInput(intInputTag)
    }

    if (charInputFlag) {
        messageTagGenerator(" %c\\0", 1)
        charInputTag = messageCounter - 1
        addCharInput(charInputTag)
    }

    if (throwOverflowFlag) {
        messageTagGenerator(
            "OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n",
            1
        )
        throwOverflowTag = messageCounter - 1
        addThrowOverflowError(throwOverflowTag)
    }

    if (throwRuntimeFlag) {
        addThrowRuntimeError()
        throwRuntimeFlag = false
    }

    if (divideByZeroFlag) {
        messageTagGenerator("DivideByZeroError: divide or modulo by zero\\n\\0", 2)
        divideByZeroTag = messageCounter - 1
        addCheckDivideByZero(divideByZeroTag)
    }

    if (checkArrayFlag) {
        messageTagGenerator("ArrayIndexOutOfBoundsError: negative index\\n\\0", 2)
        checkArrayNegativeBoundsTag = messageCounter - 1
        messageTagGenerator("ArrayIndexOutOfBoundsError: index too large\\n\\0", 2)
        checkArrayOutOfBoundsTag = messageCounter - 1
        addCheckArrayOutOfBounds(checkArrayOutOfBoundsTag, checkArrayNegativeBoundsTag)
    }

    if (checkNullPointerFlag) {
        if (throwRuntimeFlag) {
            addThrowRuntimeError()
        }

        messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0", 2)
        checkNullPointerTag = messageCounter - 1
        addCheckNullPointer(checkNullPointerTag)
    }
    if (freeArrayFlag || freePairFlag) {

        if (freeArrayFlag) {
            messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0", 2)
            freeArrayTag = messageCounter - 1
            addFreeArray(freeArrayTag)
            if (throwRuntimeFlag) {
                addThrowRuntimeError()
            }
        }

        if (freePairFlag) {
            messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0", 2)
            freePairTag = messageCounter - 1
            addFreePair(freePairTag)
            if (throwRuntimeFlag) {
                addThrowRuntimeError()
            }
        }

        if (!printStringFlag) {
            messageTagGenerator("%.*s\\0", 1)
            printStringTag = messageCounter - 1
            addPrintString(printStringTag)
        }

    }
}

fun CodeGenerator.readInput(statement: Statement.ReadInput) {
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