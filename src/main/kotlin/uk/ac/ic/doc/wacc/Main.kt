package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser

fun main(args : Array<String>) {

    if(args.isEmpty()) {
        println("Please enter a file")
        return
    }

    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String) = WaccParser(tokenStream(resourceName)).prog()

    val visitor = BlockVisitor()
    parseResource(args[0]).accept(visitor)
}
