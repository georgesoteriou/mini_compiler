import org.antlr.v4.runtime.*
import org.junit.Assert
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.visitors.ProgramVisitor
import java.io.File

fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
fun parseResource(resourceName: String) {
    val visitor = ProgramVisitor()
    val parser = WaccParser(tokenStream(resourceName))
    parser.removeErrorListeners()
    parser.addErrorListener(TestErrorListener())
    parser.prog().accept(visitor)
}

fun testValid(pathname: String) {
    File(pathname).listFiles().forEach {
        if (it.extension == "wacc") {
            try {
                parseResource(it.absolutePath)
            } catch (e: RuntimeException) {
                Assert.fail("Cannot parse " + it.name + "\nReason: " + e)
            }
        }
    }
}

fun testInvalid(pathname: String) {
    File(pathname).listFiles().forEach {
        if (it.extension == "wacc") {
            try {
                parseResource(it.absolutePath)
            } catch (e: RuntimeException) {
                return@forEach
            }
            Assert.fail("Shouldn't successfully parse " + it.name)
        }
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