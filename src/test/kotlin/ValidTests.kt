import org.junit.Test


class ValidTests {


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
}
