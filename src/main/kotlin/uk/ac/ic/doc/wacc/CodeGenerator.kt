package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.assembly_code.Instruction
import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.helpers.*
import java.io.File

class CodeGenerator(var program: Program) {

    var labelCounter = 0
    var instructions: MutableList<Instruction> = arrayListOf()
    var data: MutableList<Instruction> = arrayListOf()
    var activeScope = ActiveScope(Scope(), null)
    var currentBlock: Statement.Block? = null

    fun compile() : CodeGenerator {
        instructions.add(Instruction.Flag(".text"))
        instructions.add(Instruction.Flag(".global main"))

        // Compiling the functions
        program.functions.forEach {
            currentBlock = it.block
            compileBlock(it.name, it.block, true, it.params)
        }

        // Compiling main
        currentBlock = program.block
        compileBlock("main", program.block)

        // Generating messages required for string printing/ error messages
        dataGenerator()

        return this
    }

    fun compileStatement(statement: Statement) {
        when (statement) {
            is Statement.Block -> compileBlock("", statement)
            is Statement.If -> ifInstructions(statement)
            is Statement.While -> whileInstructions(statement)
            is Statement.VariableDeclaration -> declare(statement)
            is Statement.VariableAssignment -> assign(statement)
            is Statement.ReadInput -> readInput(statement)
            is Statement.FreeVariable -> freeVariable(statement)
            is Statement.Return -> returnStatementInstructions(statement)
            is Statement.Exit -> exitStatementInstructions(statement)
            is Statement.Print -> printInstructions(statement.expression)
            is Statement.PrintLn -> printlnInstructions(statement.expression)
        }
    }

    fun compileExpression(expression: Expression, actualDst: Int) {
        var dest = actualDst
        if (actualDst > 10) {
            dest = 10
        }

        when (expression) {
            is Expression.CallFunction -> callFunctionExprInstructions(expression, dest)
            is Expression.Identifier -> addPointerLDR(expression, dest)
            is Expression.Literal.LInt -> intExprInstructions(expression, actualDst, dest)
            is Expression.Literal.LBool -> boolExprInstructions(expression, actualDst, dest)
            is Expression.Literal.LChar -> charExprInstructions(expression, actualDst, dest)
            is Expression.Literal.LString -> stringExprInstructions(expression, dest)
            is Expression.Literal.LPair -> nullExprInstructions(dest)
            is Expression.BinaryOperation -> binOpExprInstructions(expression, actualDst, dest)
            is Expression.UnaryOperation -> unOpExprInstructions(expression, dest)
            is Expression.ArrayElem -> arrayElemExprInstructions(expression, dest)
            is Expression.Fst -> pairElemExprInstructions(
                expression.expression.name,
                expression.expression.exprType,
                0,
                4
            )
            is Expression.Snd -> pairElemExprInstructions(
                expression.expression.name,
                expression.expression.exprType,
                4,
                4
            )
        }
    }

    fun outputAssembly(filename: String) {
        val file = File("$filename.s")

        if (!data.isEmpty()) {
            file.writeText(".data\n")
        }

        data.forEach { file.appendText("$it\n") }
        instructions.forEach { file.appendText("$it\n") }
    }
}

