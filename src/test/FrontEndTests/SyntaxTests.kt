import org.antlr.v4.runtime.*
import org.junit.Assert.fail
import org.junit.Test
import uk.ac.ic.doc.wacc.grammar.WaccLexer
import uk.ac.ic.doc.wacc.grammar.WaccParser
import java.io.File


class SyntaxTests {

    fun lexerForResource(resourceName: String) = WaccLexer(CharStreams.fromFileName(resourceName))
    fun tokenStream(resourceName: String) = CommonTokenStream(lexerForResource(resourceName))
    fun parseResource(resourceName: String) {
        val parser = WaccParser(tokenStream(resourceName))
        parser.removeErrorListeners()
        parser.addErrorListener(TestErrorListener())
        parser.prog()
    }

    private fun testValid(pathname: String) {
        File(pathname).listFiles().forEach {
            try {
                parseResource(it.absolutePath)
            } catch (e: RuntimeException) {
                fail("Cannot parse " + it.name + "\nReason: " + e)
            }
        }
    }

    private fun testInvalid(pathname: String) {
        File(pathname).listFiles().forEach {
            try {
                parseResource(it.absolutePath)
            } catch (e: RuntimeException) {
                return@forEach
            }
            fail("Shouldn't successfully parse " + it.name)
        }
    }


    @Test
    fun validAdvanced() {
        testValid("src/test/resources/valid/advanced/")
    }

    @Test
    fun validArray() {
        testValid("src/test/resources/valid/array/")
    }

    @Test
    fun validBasicExit() {
        testValid("src/test/resources/valid/basic/exit/")
    }

    @Test
    fun validBasicSkip() {
        testValid("src/test/resources/valid/basic/skip/")
    }

    @Test
    fun validExpressions() {
        testValid("src/test/resources/valid/expressions/")
    }

    @Test
    fun validFunctionNestedFunctions() {
        testValid("src/test/resources/valid/function/nested_functions")
    }

    @Test
    fun validFunctionSimpleFunctions() {
        testValid("src/test/resources/valid/function/simple_functions")
    }

    @Test
    fun validIf() {
        testValid("src/test/resources/valid/if/")
    }

    @Test
    fun validIO() {
        testValid("src/test/resources/valid/IO/basic")
    }

    @Test
    fun validIOPrint() {
        testValid("src/test/resources/valid/IO/print/")
    }

    @Test
    fun validIORead() {
        testValid("src/test/resources/valid/IO/read/")
    }

    @Test
    fun validPairs() {
        testValid("src/test/resources/valid/pairs/")
    }

    @Test
    fun validRuntimeErrArrayOutOfBounds() {
        testValid("src/test/resources/valid/runtimeErr/arrayOutOfBounds/")
    }

    @Test
    fun validRuntimeErrDivideByZero() {
        testValid("src/test/resources/valid/runtimeErr/divideByZero/")
    }

    @Test
    fun validRuntimeErrDoubleFrees() {
        testValid("src/test/resources/valid/runtimeErr/doubleFrees/")
    }

    @Test
    fun validRuntimeErrIntegerOverflow() {
        testValid("src/test/resources/valid/runtimeErr/integerOverflow/")
    }

    @Test
    fun validRuntimeErrNullDereference() {
        testValid("src/test/resources/valid/runtimeErr/nullDereference/")
    }

    @Test
    fun validScope() {
        testValid("src/test/resources/valid/scope/")
    }

    @Test
    fun validSequence() {
        testValid("src/test/resources/valid/sequence/")
    }

    @Test
    fun validVariables() {
        testValid("src/test/resources/valid/variables/")
    }

    @Test
    fun validWhile() {
        testValid("src/test/resources/valid/while/")
    }

    // INVALIDS:

    @Test
    fun invalidSyntaxErrBasic() {
        testInvalid("src/test/resources/invalid/syntaxErr/basic/")
    }

    @Test
    fun invalidSyntaxErrArray() {
        testInvalid("src/test/resources/invalid/syntaxErr/array/")
    }

    @Test
    fun invalidSyntaxErrExpressions() {
        testInvalid("src/test/resources/invalid/syntaxErr/expressions/")
    }

    @Test
    fun invalidSyntaxErrFunction() {
        testInvalid("src/test/resources/invalid/syntaxErr/function/")
    }

    @Test
    fun invalidSyntaxErrIf() {
        testInvalid("src/test/resources/invalid/syntaxErr/if/")
    }

    @Test
    fun invalidSyntaxErrPairs() {
        testInvalid("src/test/resources/invalid/syntaxErr/pairs/")
    }

    @Test
    fun invalidSyntaxErrSequence() {
        testInvalid("src/test/resources/invalid/syntaxErr/sequence/")
    }

    @Test
    fun invalidSyntaxErrVariables() {
        testInvalid("src/test/resources/invalid/syntaxErr/variables/")
    }

    @Test
    fun invalidSyntaxErrWhile() {
        testInvalid("src/test/resources/invalid/syntaxErr/while/")
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