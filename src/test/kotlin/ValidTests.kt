import org.junit.Test
import java.io.File

/*
@Test
fun test() {
    testCompile(
        File("src/test/resources/"),
        0
    )
}


*/
class ValidTests {

    class Advanced {}
    class Array {
        @Test
        fun array() {
            testCompile(
                File("src/test/resources/valid/array/array.wacc"), 0, " = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}"
            )
        }

        @Test
        fun arrayBasic() {
            testCompile(
                File("src/test/resources/valid/array/arrayBasic.wacc"), 0
            )
        }

        @Test
        fun arrayEmpty() {
            testCompile(
                File("src/test/resources/valid/array/arrayEmpty.wacc"), 0
            )
        }

        @Test
        fun arrayLength() {
            testCompile(
                File("src/test/resources/valid/array/arrayLength.wacc"),
                0, "4"
            )
        }
        @Test
        fun arrayLookup() {
            testCompile(
                File("src/test/resources/valid/array/arrayLookup.wacc"),
                0,"43"
            )
        }
        @Test
        fun arrayNested() {
            testCompile(
                File("src/test/resources/valid/array/arrayNested.wacc"),
                0, "3\n" + "3"
            )
        }
        @Test
        fun arrayPrint() {
            testCompile(
                File("src/test/resources/valid/array/arrayPrint.wacc"),
                0, " = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}"
            )
        }
        @Test
        fun arraySimple() {
            testCompile(
                File("src/test/resources/valid/array/arraySimple.wacc"),
                0, "42"
            )
        }
        @Test
        fun modifyString() {
            testCompile(
                File("src/test/resources/valid/array/modifyString.wacc"),
                0, "hello world!\n" + "Hello world!\n" + "Hi!"
            )
        }
        @Test
        fun printRef() {
            testCompile(
                File("src/test/resources/valid/array/printRef.wacc"),
                0, "Printing an array variable gives an address, such as 0x"
            )
        }

    }

    class Basic {
        class Exit {
            @Test
            fun exit255() {
                testCompile(
                    File("src/test/resources/valid/basic/exit/exit-1.wacc"),
                    255
                )
            }
            @Test
            fun exitBasic() {
                testCompile(
                    File("src/test/resources/valid/basic/exit/exitBasic.wacc"),
                    7
                )
            }
            @Test
            fun exitBasic2() {
                testCompile(
                    File("src/test/resources/valid/basic/exit/exitBasic2.wacc"),
                    42
                )
            }
            @Test
            fun test() {
                testCompile(
                    File("src/test/resources/valid/basic/exit/exitWrap.wacc"),
                    0
                )
            }

        }
        class Skip {
            @Test
            fun comment() {
                testCompile(
                    File("src/test/resources/valid/basic/skip/comment.wacc"),
                    0
                )
            }
            @Test
            fun commentInLine() {
                testCompile(
                    File("src/test/resources/valid/basic/skip/commentInLine.wacc"),
                    0
                )
            }
            @Test
            fun test() {
                testCompile(
                    File("src/test/resources/valid/basic/skip/skip.wacc"),
                    0
                )
            }

        }
    }

    class Expressions {
        @Test
        fun andExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/andExpr.wacc"),
                0, "false\n" + "true\n" + "false"
            )
        }
        @Test
        fun boolCalc() {
            testCompile(
                File("src/test/resources/valid/expressions/boolCalc.wacc"),
                0, "false"
            )
        }
        @Test
        fun boolExpr1() {
            testCompile(
                File("src/test/resources/valid/expressions/boolExpr1.wacc"),
                0, "Correct"
            )
        }
        @Test
        fun charComparisonExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/charComparisonExpr.wacc"),
                0, "false\n" + "true\n" + "true\n" + "true\n" + "false\n" + "false"
            )
        }
        @Test
        fun divExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/divExpr.wacc"),
                0, "1"
            )
        }
        @Test
        fun equalsExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/equalsExpr.wacc"),
                0, "false\n" + "false\n" + "true"
            )
        }
        @Test
        fun greaterEqExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/greaterEqExpr.wacc"),
                0, "false\n" + "true\n" + "true"
            )
        }
        @Test
        fun greaterExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/greaterExpr.wacc"),
                0, "false\n" + "true"
            )
        }
        @Test
        fun intCalc() {
            testCompile(
                File("src/test/resources/valid/expressions/intCalc.wacc"),
                0, "72"
            )
        }
        @Test
        fun intExpr1() {
            testCompile(
                File("src/test/resources/valid/expressions/intExpr1.wacc"),
                0, "Correct"
            )
        }
        @Test
        fun lessCharExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/lessCharExpr.wacc"),
                0, "true\n" + "false"
            )
        }
        @Test
        fun lessEqExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/lessEqExpr.wacc"),
                0, "true\n" + "false\n" + "true"
            )
        }
        @Test
        fun lessExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/lessExpr.wacc"),
                0, "true\n" + "false"
            )
        }
        @Test
        fun longExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/longExpr.wacc"),
                153
            )
        }
        @Test
        fun longExpr2() {
            testCompile(
                File("src/test/resources/valid/expressions/longExpr2.wacc"),
                10
            )
        }
        @Test
        fun longExpr3() {
            testCompile(
                File("src/test/resources/valid/expressions/longExpr3.wacc"),
                9
            )
        }
        @Test
        fun longSplitExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/longSplitExpr.wacc"),
                153
            )
        }
        @Test
        fun longSplitExpr2() {
            testCompile(
                File("src/test/resources/valid/expressions/longSplitExpr2.wacc"),
                128
            )
        }
        @Test
        fun minusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/minusExpr.wacc"),
                0, "5"
            )
        }
        @Test
        fun minusMinusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/minusMinusExpr.wacc"),
                0, "3"
            )
        }
        @Test
        fun minusNoWhitespaceExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/minusNoWhitespaceExpr.wacc"),
                0, "-1"
            )
        }

        @Test
        fun minusPlusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/minusPlusExpr.wacc"),
                0, "-1"
            )
        }
        @Test
        fun modExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/modExpr.wacc"),
                0, "2"
            )
        }
        @Test
        fun multExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/multExpr.wacc"),
                0, "15"
            )
        }
        @Test
        fun multNoWhitespaceExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/multNoWhitespaceExpr.wacc"),
                0, "2"
            )
        }
        @Test
        fun negBothDiv() {
            testCompile(
                File("src/test/resources/valid/expressions/negBothDiv.wacc"),
                0, "2"
            )
        }
        @Test
        fun negBothMod() {
            testCompile(
                File("src/test/resources/valid/expressions/negBothMod.wacc"),
                0, "-2"
            )
        }
        @Test
        fun negDividendDiv() {
            testCompile(
                File("src/test/resources/valid/expressions/negDividendDiv.wacc"),
                0, "-2"
            )
        }
        @Test
        fun negDividendMod() {
            testCompile(
                File("src/test/resources/valid/expressions/negDividendMod.wacc"),
                0, "-2"
            )
        }
        @Test
        fun negDivisorDiv() {
            testCompile(
                File("src/test/resources/valid/expressions/negDivisorDiv.wacc"),
                0, "-2"
            )
        }
        @Test
        fun negDivisorMod() {
            testCompile(
                File("src/test/resources/valid/expressions/negDivisorMod.wacc"),
                0, "2"
            )
        }
        @Test
        fun negExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/negExpr.wacc"),
                0, "-42"
            )
        }
        @Test
        fun notequalsExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/notequalsExpr.wacc"),
                0, "true\n" + "true\n" + "false"
            )
        }
        @Test
        fun notExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/notExpr.wacc"),
                0, "false\n" + "true"
            )
        }
        @Test
        fun ordAndchrExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/ordAndchrExpr.wacc"),
                0, "a is 97\n" + "99 is c"
            )
        }
        @Test
        fun orExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/orExpr.wacc"),
                0, "true\n" + "true\n" + "false"
            )
        }
        @Test
        fun plusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/plusExpr.wacc"),
                0, "35"
            )
        }
        @Test
        fun plusMinusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/plusMinusExpr.wacc"),
                0, "-1"
            )
        }
        @Test
        fun plusNoWhitespaceExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/plusNoWhitespaceExpr.wacc"),
                0, "3"
            )
        }
        @Test
        fun plusPlusExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/plusPlusExpr.wacc"),
                0, "3"
            )
        }
        @Test
        fun sequentialCount() {
            testCompile(
                File("src/test/resources/valid/expressions/sequentialCount.wacc"),
                0, "Can you count to 10?\n"
                        + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" + "7\n" + "8\n" + "9\n" + "10"
            )
        }
        @Test
        fun stringEqualsExpr() {
            testCompile(
                File("src/test/resources/valid/expressions/stringEqualsExpr.wacc"),
                0, "true\n" + "false\n" + "false"
            )
        }

    }

    class Function {
        class NestedFunctions {
            @Test
            fun functionConditionalReturn() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/functionConditionalReturn.wacc"),
                    0, "true"
                )
            }
            @Test
            fun test() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/mutualRecursion.wacc"),
                    0, 
                    "r1: sending 8\n" + "r2: received 8\n" + "r1: sending 7\n" + "r2: received 7\n" + 
                            "r1: sending 6\n" + "r2: received 6\n" + "r1: sending 5\n" + "r2: received 5\n" + 
                            "r1: sending 4\n" + "r2: received 4\n" + "r1: sending 3\n" + "r2: received 3\n" + 
                            "r1: sending 2\n" + "r2: received 2\n" + "r1: sending 1\n" + "r2: received 1"
                )
            }
        }
        class SimpleFunctions {}
    }

    class If {}
    class Io {
        class Basic {}
        class Print {
            @Test
            fun testPrint() {
                testCompile(
                    File("src/test/resources/valid/IO/print/print.wacc"), 0, "Hello World!"
                )
            }

            @Test
            fun testPrintBool() {
                testCompile(
                    File("src/test/resources/valid/IO/print/printBool.wacc"), 0, "True is true\n" + "False is false"
                )
            }

            //    @Test
            //    fun testMultipleStringsAssignment() {
            //        testCompile(File("src/test/resources/valid/IO/print/multipleStringsAssignment.wacc"), 0,
            //            "s1 is Hi\ns2 is Hi\nThey are not the same.\nNow modify s1[0] = 'h'\ns1 is hi\ns2 is Hi\nThey are not the same."
            //        )
            //    }
        }

        class Read {}
    }

    class Pairs {}
    class RuntimeErr {
        class ArrayOutOfBounds {}
        class DivideByZero {}
        class IntegerOverflow {}
        class NullDereference {}
    }

    class Scope {}
    class Sequence {}
    class Variables {}
    class While {
        @Test
        fun testFibonacciIterative() {
            testCompile(
                File("src/test/resources/valid/while/fibonacciIterative.wacc"),
                0,
                "The first 20 fibonacci numbers are:\n" + "0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, ..."
            )
        }

        @Test
        fun testLoopCharCondition() {
            testCompile(
                File("src/test/resources/valid/while/loopCharCondition.wacc"),
                0,
                "Change c\n" + "Should print \"Change c\" once before."
            )
        }

        @Test
        fun testLoopIntCondition() {
            testCompile(
                File("src/test/resources/valid/while/loopIntCondition.wacc"),
                0,
                "Change n\n" + "Should print \"Change n\" once before."
            )
        }
    }


}
