package uk.ac.ic.doc.wacc.helpers

import uk.ac.ic.doc.wacc.CodeGenerator
import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Type

fun CodeGenerator.messageTagGenerator(content: String, numEscChars: Int = 0) {
    var length: Int = content.length
    var escInString = 0
    if (numEscChars == 0) {
        for ( i in 0..content.length-2 ) {
            if (content[i] == '\\') {
                if ( i > 0 ) {
                    if (content[i-1] != '\\' || content[i+1] != '\\') {
                        escInString++
                    }
                } else if ( i == 0 ) {
                    if ( content[i+1] != '\\') {
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

fun CodeGenerator.add_throwOverflowError(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required message for this:
    //              "OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
    // resides at tagValue

    instructions.addAll(arrayListOf(
        Instruction.LABEL("p_throw_overflow_error"),
        Instruction.LDRSimple(Operand.Register(0),Operand.MessageTag(tagValue)),
        Instruction.BL("p_throw_runtime_error")
    ))
}

fun CodeGenerator.add_throwRuntimeError() {
    // This should be called at the end of the program, right after add_freeArray or add_freePair is called
    // There are no required messages for this.

    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_throw_runtime_error"),
            Instruction.BL("p_print_string"),
            Instruction.MOV(Operand.Register(0),Operand.Constant(-1)),
            Instruction.BL("exit")
        )
    )
}

fun CodeGenerator.add_checkDivideByZero(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required message for this:
    //              "DivideByZeroError: divide or modulo by zero\n\0"
    // resides at tagValue


    instructions.addAll(arrayListOf(
        Instruction.LABEL("p_check_divide_by_zero"),
        Instruction.PUSH(arrayListOf(Operand.Lr)),
        Instruction.CMP(Operand.Register(1),Operand.Constant(0)),
        Instruction.LDRCond(Operand.Register(0),Operand.MessageTag(tagValue),"EQ"),
        Instruction.BCond("p_throw_runtime_error","LEQ"),
        Instruction.POP(arrayListOf(Operand.Pc))
    ))
}


fun CodeGenerator.add_pPrintString(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required message for this: %.*s\0 resides at tagValue
    instructions.addAll(
        arrayListOf(
            (Instruction.LABEL("p_print_string")),
            (Instruction.PUSH(arrayListOf(Operand.Lr))),
            (Instruction.LDRRegister(Operand.Register(1), Operand.Register(0), Operand.Offset(0))),
            (Instruction.ADD(
                Operand.Register(2),
                Operand.Register(0),
                Operand.Constant(4)
            )),
            (Instruction.LDRSimple(Operand.Register(0), Operand.MessageTag(tagValue))),
            (Instruction.ADD(
                Operand.Register(0),
                Operand.Register(0),
                Operand.Constant(4)
            )),
            (Instruction.BL("printf")),
            (Instruction.MOV(Operand.Register(0), Operand.Constant(0))),
            (Instruction.BL("fflush")),
            (Instruction.POP(arrayListOf(Operand.Pc)))
        )
    )
}

fun CodeGenerator.add_pPrintBool(trueTagValue: Int, falseTagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this:
    //                      true\0 resides at trueTagValue
    //                      false\0 resides at falseTagValue
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

fun CodeGenerator.add_pPrintInt(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this: %d\0 resides at tagValue
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

fun CodeGenerator.add_pPrintReference(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this : %p\0 resides at tagValue
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_print_reference"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1),Operand.Register(0)),
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

fun CodeGenerator.add_pPrintLn(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this : \0 resides at tagValue
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

fun CodeGenerator.add_freeArray(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this : NullReferenceError: dereference a null reference\n\0 resides at tagValue
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

fun CodeGenerator.add_freePair(tagValue: Int) {
    // This should be called at the end of the program after chekcing the flags
    // The required messages for this: NullReferenceError: dereference a null reference\n\0 resides at tagValue
    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_free_pair"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0), Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0), Operand.MessageTag(tagValue), "EQ"),
            Instruction.BCond("p_throw_runtime_error", "EQ"),
            Instruction.PUSH(arrayListOf(Operand.Register(0))),
            Instruction.LDRRegister(Operand.Register(0),Operand.Register(0),Operand.Offset(0)),
            Instruction.BL("free"),
            Instruction.LDRRegister(Operand.Register(0),Operand.Sp,Operand.Offset(0)),
            Instruction.LDRRegister(Operand.Register(0),Operand.Register(0),Operand.Offset(4)),
            Instruction.BL("free"),
            Instruction.POP(arrayListOf(Operand.Register(0))),
            Instruction.BL("free"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}


fun CodeGenerator.add_charInput(tagValue: Int) {
    // This should be called at the end of the program after chekcing the flags
    // The required messages for this : %c\0 resides at tagValue

    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_read_char"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1),Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0),Operand.MessageTag(tagValue)),
            Instruction.ADD(Operand.Register(0),Operand.Register(0),Operand.Constant(4)),
            Instruction.BL("scanf"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )
}

fun CodeGenerator.add_intInput(tagValue: Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this : %d\0 resides at tagValue

    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_read_int"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.MOV(Operand.Register(1),Operand.Register(0)),
            Instruction.LDRSimple(Operand.Register(0),Operand.MessageTag(tagValue)),
            Instruction.ADD(Operand.Register(0),Operand.Register(0),Operand.Constant(4)),
            Instruction.BL("scanf"),
            Instruction.POP(arrayListOf(Operand.Pc))
            )
    )
}

fun CodeGenerator.add_checkNullPointer(tagValue:Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this : NullReferenceError: dereference a null reference\n\0 resides at tagValue

    instructions.addAll(arrayListOf(
        Instruction.LABEL("p_check_null_pointer"),
        Instruction.PUSH(arrayListOf(Operand.Lr)),
        Instruction.CMP(Operand.Register(0),Operand.Constant(0)),
        Instruction.LDRCond(Operand.Register(0),Operand.MessageTag(tagValue),"EQ"),
        Instruction.BCond("p_throw_runtime_error","LEQ"),
        Instruction.POP(arrayListOf(Operand.Pc))
    ))
}

fun CodeGenerator.add_checkArrayOutOfBounds(indexTooLarge: Int, negativeIndex : Int) {
    // This should be called at the end of the program after checking the flags
    // The required messages for this :
    // ArrayIndexOutOfBoundsError: negative index\n\0
    // resides at negativeIndex
    // ArrayIndexOutOfBoundsError: index too large\n\0
    // resides at indexTooLarge

    instructions.addAll(
        arrayListOf(
            Instruction.LABEL("p_check_array_bounds"),
            Instruction.PUSH(arrayListOf(Operand.Lr)),
            Instruction.CMP(Operand.Register(0),Operand.Constant(0)),
            Instruction.LDRCond(Operand.Register(0),Operand.MessageTag(negativeIndex),"LT"),
            Instruction.BCond("p_throw_runtime_error","LLT"),
            Instruction.LDRRegister(Operand.Register(1),Operand.Register(1),Operand.Offset(0)),
            Instruction.CMP(Operand.Register(0),Operand.Register(1)),
            Instruction.LDRCond(Operand.Register(0),Operand.MessageTag(indexTooLarge),"CS"),
            Instruction.BCond("p_throw_runtime_error","CS"),
            Instruction.POP(arrayListOf(Operand.Pc))
        )
    )

}

fun CodeGenerator.printTypeInstructions(expression: Expression) {
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
            //messageTagGenerator((expression as Expression.Literal.LString).string)
            // TODO: check here about what happens because message generator is called here so the tag
            // TODO: is generated here but it has already been passed through compileExpression so maybe
            // TODO: the function call to messageTagGenerator should be in compileExpression
            // TODO: but what if strings are used elsewhere?
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


fun CodeGenerator.dataGenerator() {
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
        messageTagGenerator(" %c\\0", 1)
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

    if(checkArrayFlag) {
        messageTagGenerator("ArrayIndexOutOfBoundsError: negative index\\n\\0",2)
        checkArrayNegativeBoundsTag = messageCounter - 1
        messageTagGenerator("ArrayIndexOutOfBoundsError: index too large\\n\\0",2)
        checkArrayOutOfBoundsTag = messageCounter - 1
        add_checkArrayOutOfBounds(checkArrayOutOfBoundsTag,checkArrayNegativeBoundsTag)
    }

    if (checkNullPointerFlag) {
        if (throwRuntimeFlag) {
            add_throwRuntimeError()
        }

        messageTagGenerator("NullReferenceError: dereference a null reference\\n\\0",2)
        checkNullPointerTag = messageCounter - 1
        add_checkNullPointer(checkNullPointerTag)
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
}
