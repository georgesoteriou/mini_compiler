package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.assembly_code.Operand
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.ast.Statement

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()

    fun compile() {
        instructions.add(Instruction.LABEL(".global main"))
        compileStatement(program.block, "main")
        instructions.forEach {System.out.println(it.toString())}
    }

    fun compileStatement(statement: Statement, name: String = ".L$labelCounter") {
        when (statement) {
            is Statement.Block -> {
                instructions.add(Instruction.LABEL(name))
                instructions.add(Instruction.PUSH(arrayListOf(Operand.Lr)))
                statement.statements.forEach { compileStatement(it) }
                labelCounter++
                instructions.add(Instruction.LDR(Operand.Register(0), Operand.Literal(0)))
                instructions.add(Instruction.POP(arrayListOf(Operand.Pc)))
            }
            is Statement.Skip -> {}
            is Statement.VariableDeclaration -> {}
            is Statement.VariableAssignment -> {}
            is Statement.ReadInput -> {}
            is Statement.FreeVariable -> {}
            is Statement.Return -> {}
            is Statement.Exit -> {
                compileExpression(statement.expression)
                instructions.add(Instruction.MOV(Operand.Register(0), Operand.Register(4)))
                instructions.add(Instruction.BL("exit"))
            }
            is Statement.Print -> {}
            is Statement.PrintLn -> {}
            is Statement.If -> {}
            is Statement.While -> {}
        }

    }

    fun compileExpression(expression: Expression) {
        when (expression) {
            is Expression.CallFunction -> {}
            is Expression.NewPair -> {}
            is Expression.Identifier -> {}

            is Expression.Literal.LInt -> {
                instructions.add(Instruction.MOV(Operand.Register(4), Operand.Literal(expression.int)))
            }
            is Expression.Literal.LBool -> {}
            is Expression.Literal.LChar -> {}
            is Expression.Literal.LString -> {}
            is Expression.Literal.LArray -> {}
            is Expression.Literal.LPair -> {}

            is Expression.BinaryOperation -> {}
            is Expression.UnaryOperation -> {
                when(expression.operator) {
                    Expression.UnaryOperator.MINUS -> {
                        instructions.add(Instruction.MOV(Operand.Register(4),
                            Operand.Literal(-(expression.expression as Expression.Literal.LInt).int)))
                    }
                    Expression.UnaryOperator.CHR,
                    Expression.UnaryOperator.LEN,
                    Expression.UnaryOperator.NOT,
                    Expression.UnaryOperator.ORD -> {}
                }
            }

            is Expression.ArrayElem -> {}
            is Expression.Fst -> {}
            is Expression.Snd -> {}
        }
    }
}