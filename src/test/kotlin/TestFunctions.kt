import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.misc.ParseCancellationException
import org.junit.Assert
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.semanticCheck
import uk.ac.ic.doc.wacc.visitors.ProgramVisitor
import java.io.File


fun testSynAndSem(pathname: String, expectedExit: Int) {
    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String): WaccParser.ProgContext {
        val parser = WaccParser(tokenStream(resourceName))
        parser.errorHandler = BailErrorStrategy()
        parser.removeErrorListeners()
        parser.addErrorListener(TestErrorListener())
        return parser.prog()
    }

    var fail = false
    File(pathname).listFiles().forEach {
        if (it.extension == "wacc") {

            var exit = 0
            try {
                val visitor = ProgramVisitor()
                val program = parseResource(it.absolutePath).accept(visitor)

                if (!semanticCheck(program)) {
                    exit = 200
                }
            } catch (e: ParseCancellationException) {
                exit = 100
            } catch (e: java.lang.RuntimeException) {
                exit = 100
            }
            if (exit != expectedExit) {
                fail = true
                println("Failed: ${it.name}, Expected $expectedExit but got $exit")
            }
        }
    }
    if (fail) {
        Assert.fail("At least one file failed")
    }
}

class TestErrorListener : BaseErrorListener() {
    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        throw RuntimeException("line $line:$charPositionInLine: $msg")
    }
}