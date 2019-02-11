import org.junit.Test

class SemanticTests {

    @Test
    fun invalidExit() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/exit/")
    }

    @Test
    fun invalidExpressions() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/expressions/")
    }

    @Test
    fun invalidFunction() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/function/")
    }

    @Test
    fun invalidIf() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/if/")
    }

    @Test
    fun invalidIO() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/IO/")
    }

    @Test
    fun invalidMultiple() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/multiple/")
    }

    @Test
    fun invalidPairs() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/pairs/")
    }

    @Test
    fun invalidPrint() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/print/")
    }

    @Test
    fun invalidRead() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/read/")
    }

    @Test
    fun invalidScope() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/scope/")
    }

    @Test
    fun invalidVariables() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/variables/")
    }

    @Test
    fun invalidWhile() {
        testInvalidSemantics("src/test/resources/invalid/semanticErr/while/")
    }

}