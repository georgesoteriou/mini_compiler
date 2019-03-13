package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.ParseCancellationException
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.visitors.ProgramVisitor
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Please enter a file")
        return
    }

    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String): WaccParser.ProgContext {
        if(!File(resourceName).exists()) {
            throw ParseCancellationException("$resourceName does not exist")
        }
        val parser = WaccParser(tokenStream(resourceName))
        parser.errorHandler = BailErrorStrategy()
        return parser.prog()
    }

    fun recursiveInclude(program: Program) {
        val visitor = ProgramVisitor()
        program.includes.forEach{
            val includesProg = parseResource(it).accept(visitor)
            recursiveInclude(includesProg)

            if (!includesProg.block.statements.isEmpty()) {
                throw ParseCancellationException("$it has a main function")
            }

            program.functions.addAll(includesProg.functions)
        }
    }

    try {
        val visitor = ProgramVisitor()
        val program = parseResource(args[0]).accept(visitor)

        recursiveInclude(program)

        if (!semanticCheck(program)) {
            exitProcess(200)
        }

        val outputFile = File(args[0]).nameWithoutExtension
        CodeGenerator(program).compile().outputAssembly(outputFile)
    } catch (e: ParseCancellationException) {
        println("Syntax error ${if(e.message != null) {e.message} else {""}}")
        exitProcess(100)
    }

}





