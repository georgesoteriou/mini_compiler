import org.junit.Test

class InvalidTests {

    @Test
    fun invalidExit() {
        testSynAndSem("src/test/resources/invalid/semanticErr/exit/", 200)
    }

    @Test
    fun invalidExpressions() {
        testSynAndSem("src/test/resources/invalid/semanticErr/expressions/", 200)
    }

    @Test
    fun invalidFunction() {
        testSynAndSem("src/test/resources/invalid/semanticErr/function/", 200)
    }

    @Test
    fun invalidIf() {
        testSynAndSem("src/test/resources/invalid/semanticErr/if/", 200)
    }

    @Test
    fun invalidIO() {
        testSynAndSem("src/test/resources/invalid/semanticErr/IO/", 200)
    }

    @Test
    fun invalidMultiple() {
        testSynAndSem("src/test/resources/invalid/semanticErr/multiple/", 200)
    }

    @Test
    fun invalidPairs() {
        testSynAndSem("src/test/resources/invalid/semanticErr/pairs/", 200)
    }

    @Test
    fun invalidPrint() {
        testSynAndSem("src/test/resources/invalid/semanticErr/print/", 200)
    }

    @Test
    fun invalidRead() {
        testSynAndSem("src/test/resources/invalid/semanticErr/read/", 200)
    }

    @Test
    fun invalidScope() {
        testSynAndSem("src/test/resources/invalid/semanticErr/scope/", 200)
    }

    @Test
    fun invalidVariables() {
        testSynAndSem("src/test/resources/invalid/semanticErr/variables/", 200)
    }

    @Test
    fun invalidWhile() {
        testSynAndSem("src/test/resources/invalid/semanticErr/while/", 200)
    }

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