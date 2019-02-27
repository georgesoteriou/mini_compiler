import org.junit.Test
import java.io.File


class ValidTests {


    // WHILE TESTS
    @Test
    fun testFibonacciIterative() {
        testcompile(File("src/test/resources/valid/while/fibonacciIterative.wacc"),
            0,
            "The first 20 fibonacci numbers are:\n" +
                    "0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, ...")
    }

    @Test
    fun testLoopCharCondition() {
        testcompile(File("src/test/resources/valid/while/loopCharCondition.wacc"),
            0,
            "Change c\n" + "Should print \"Change c\" once before.")
    }

    @Test
    fun testLoopIntCondition() {
        testcompile(File("src/test/resources/valid/while/loopIntCondition.wacc"),
            0,
            "Change n\n" + "Should print \"Change n\" once before.")
    }

    // BASIC TESTS

    @Test
    fun testPrint() {
        testcompile(File("src/test/resources/valid/IO/print/print.wacc"),
            0,
            "Hello World!")
    }

    @Test
    fun testPrintBool() {
        testcompile(File("src/test/resources/valid/IO/print/printBool.wacc"),
            0,
            "True is true\n" + "False is false")
    }















//    @Test
//    fun testMultipleStringsAssignment() {
//        testcompile(File("src/test/resources/valid/IO/print/multipleStringsAssignment.wacc"), 0,
//            "s1 is Hi\ns2 is Hi\nThey are not the same.\nNow modify s1[0] = 'h'\ns1 is hi\ns2 is Hi\nThey are not the same."
//        )
//    }
}
