import org.antlr.v4.runtime.*
import org.junit.Assert
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.semanticCheck
import uk.ac.ic.doc.wacc.visitors.ProgramVisitor
import java.io.File

fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
fun parseResource(resourceName: String): Program {
    val visitor = ProgramVisitor()
    val parser = WaccParser(tokenStream(resourceName))
    parser.removeErrorListeners()
    parser.addErrorListener(TestErrorListener())
    return parser.prog().accept(visitor)
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

fun testInvalidSemantics (pathname: String) {
    File(pathname).listFiles().forEach {
        if (it.extension == "wacc") {
            Assert.assertFalse(semanticCheck(parseResource(it.absolutePath)))
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