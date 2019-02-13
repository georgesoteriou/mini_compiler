import org.junit.Test


class SyntaxTests {


    @Test
    fun validAdvanced() {
        testSynAndSem("src/test/resources/valid/advanced/", 0)
    }

    @Test
    fun validArray() {
        testSynAndSem("src/test/resources/valid/array/", 0)
    }

    @Test
    fun validBasicExit() {
        testSynAndSem("src/test/resources/valid/basic/exit/", 0)
    }

    @Test
    fun validBasicSkip() {
        testSynAndSem("src/test/resources/valid/basic/skip/", 0)
    }

    @Test
    fun validExpressions() {
        testSynAndSem("src/test/resources/valid/expressions/", 0)
    }

    @Test
    fun validFunctionNestedFunctions() {
        testSynAndSem("src/test/resources/valid/function/nested_functions", 0)
    }

    @Test
    fun validFunctionSimpleFunctions() {
        testSynAndSem("src/test/resources/valid/function/simple_functions", 0)
    }

    @Test
    fun validIf() {
        testSynAndSem("src/test/resources/valid/if/", 0)
    }

    @Test
    fun validIO() {
        testSynAndSem("src/test/resources/valid/IO/basic", 0)
    }

    @Test
    fun validIOPrint() {
        testSynAndSem("src/test/resources/valid/IO/print/", 0)
    }

    @Test
    fun validIORead() {
        testSynAndSem("src/test/resources/valid/IO/read/", 0)
    }

    @Test
    fun validPairs() {
        testSynAndSem("src/test/resources/valid/pairs/", 0)
    }

    @Test
    fun validRuntimeErrArrayOutOfBounds() {
        testSynAndSem("src/test/resources/valid/runtimeErr/arrayOutOfBounds/", 0)
    }

    @Test
    fun validRuntimeErrDivideByZero() {
        testSynAndSem("src/test/resources/valid/runtimeErr/divideByZero/", 0)
    }

    @Test
    fun validRuntimeErrDoubleFrees() {
        testSynAndSem("src/test/resources/valid/runtimeErr/doubleFrees/", 0)
    }

    @Test
    fun validRuntimeErrIntegerOverflow() {
        testSynAndSem("src/test/resources/valid/runtimeErr/integerOverflow/", 0)
    }

    @Test
    fun validRuntimeErrNullDereference() {
        testSynAndSem("src/test/resources/valid/runtimeErr/nullDereference/", 0)
    }

    @Test
    fun validScope() {
        testSynAndSem("src/test/resources/valid/scope/", 0)
    }

    @Test
    fun validSequence() {
        testSynAndSem("src/test/resources/valid/sequence/", 0)
    }

    @Test
    fun validVariables() {
        testSynAndSem("src/test/resources/valid/variables/", 0)
    }

    @Test
    fun validWhile() {
        testSynAndSem("src/test/resources/valid/while/", 0)
    }

    // INVALIDS:

    @Test
    fun invalidSyntaxErrBasic() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/basic/", 100)
    }

    @Test
    fun invalidSyntaxErrArray() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/array/", 100)
    }

    @Test
    fun invalidSyntaxErrExpressions() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/expressions/", 100)
    }

    @Test
    fun invalidSyntaxErrFunction() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/function/", 100)
    }

    @Test
    fun invalidSyntaxErrIf() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/if/", 100)
    }

    @Test
    fun invalidSyntaxErrPairs() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/pairs/", 100)
    }

    @Test
    fun invalidSyntaxErrSequence() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/sequence/", 100)
    }

    @Test
    fun invalidSyntaxErrVariables() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/variables/", 100)
    }

    @Test
    fun invalidSyntaxErrWhile() {
        testSynAndSem("src/test/resources/invalid/syntaxErr/while/", 100)
    }
}
