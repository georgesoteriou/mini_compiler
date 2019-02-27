import org.junit.Test
import java.io.File

class InvalidTests {

    @Test
    fun testBadCharExit() {
        testcompile(File("src/test/resources/invalid/semanticErr/exit/badCharExit.wacc"), 200, "")
    }

}