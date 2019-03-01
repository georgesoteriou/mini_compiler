import org.junit.Test
import java.io.File

/*
@Test
fun test() {
    testCompile(
        File("src/test/"),
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
            fun exitWrap() {
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
            fun skip() {
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
            fun fibonacciRecursive() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/fibonacciRecursive.wacc"),
                    0, "The first 20 fibonacci numbers are:\n" +
                            "0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181..."
                )
            }

            @Test
            fun fixedPointRealArithmetic() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/fixedPointRealArithmetic.wacc"),
                    0, "Using fixed-point real: 10 / 3 * 3 = 10"
                )
            }

            @Test
            fun functionConditionalReturn() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/functionConditionalReturn.wacc"),
                    0, "true"
                )
            }
            @Test
            fun mutualRecursion() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/mutualRecursion.wacc"),
                    0,
                    "r1: sending 8\n" + "r2: received 8\n" + "r1: sending 7\n" + "r2: received 7\n" +
                            "r1: sending 6\n" + "r2: received 6\n" + "r1: sending 5\n" + "r2: received 5\n" +
                            "r1: sending 4\n" + "r2: received 4\n" + "r1: sending 3\n" + "r2: received 3\n" +
                            "r1: sending 2\n" + "r2: received 2\n" + "r1: sending 1\n" + "r2: received 1"
                )
            }

            @Test
            fun printTriangle() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/printTriangle.wacc"),
                    0, "--------\n" +
                            "-------\n" +
                            "------\n" +
                            "-----\n" +
                            "----\n" +
                            "---\n" +
                            "--\n" +
                            "-"
                )
            }

            @Test
            fun simpleRecursion() {
                testCompile(
                    File("src/test/resources/valid/function/nested_functions/simpleRecursion.wacc"),
                    0
                )
            }
        }
        class SimpleFunctions {
            @Test
            fun asciiTable() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/asciiTable.wacc"),
                    0, "Asci character lookup table:\n" +
                            "-------------\n" +
                            "|   32 =    |\n" +
                            "|   33 = !  |\n" +
                            "|   34 = \"  |\n" +
                            "|   35 = #  |\n" +
                            "|   36 = \$  |\n" +
                            "|   37 = %  |\n" +
                            "|   38 = &  |\n" +
                            "|   39 = '  |\n" +
                            "|   40 = (  |\n" +
                            "|   41 = )  |\n" +
                            "|   42 = *  |\n" +
                            "|   43 = +  |\n" +
                            "|   44 = ,  |\n" +
                            "|   45 = -  |\n" +
                            "|   46 = .  |\n" +
                            "|   47 = /  |\n" +
                            "|   48 = 0  |\n" +
                            "|   49 = 1  |\n" +
                            "|   50 = 2  |\n" +
                            "|   51 = 3  |\n" +
                            "|   52 = 4  |\n" +
                            "|   53 = 5  |\n" +
                            "|   54 = 6  |\n" +
                            "|   55 = 7  |\n" +
                            "|   56 = 8  |\n" +
                            "|   57 = 9  |\n" +
                            "|   58 = :  |\n" +
                            "|   59 = ;  |\n" +
                            "|   60 = <  |\n" +
                            "|   61 = =  |\n" +
                            "|   62 = >  |\n" +
                            "|   63 = ?  |\n" +
                            "|   64 = @  |\n" +
                            "|   65 = A  |\n" +
                            "|   66 = B  |\n" +
                            "|   67 = C  |\n" +
                            "|   68 = D  |\n" +
                            "|   69 = E  |\n" +
                            "|   70 = F  |\n" +
                            "|   71 = G  |\n" +
                            "|   72 = H  |\n" +
                            "|   73 = I  |\n" +
                            "|   74 = J  |\n" +
                            "|   75 = K  |\n" +
                            "|   76 = L  |\n" +
                            "|   77 = M  |\n" +
                            "|   78 = N  |\n" +
                            "|   79 = O  |\n" +
                            "|   80 = P  |\n" +
                            "|   81 = Q  |\n" +
                            "|   82 = R  |\n" +
                            "|   83 = S  |\n" +
                            "|   84 = T  |\n" +
                            "|   85 = U  |\n" +
                            "|   86 = V  |\n" +
                            "|   87 = W  |\n" +
                            "|   88 = X  |\n" +
                            "|   89 = Y  |\n" +
                            "|   90 = Z  |\n" +
                            "|   91 = [  |\n" +
                            "|   92 = \\  |\n" +
                            "|   93 = ]  |\n" +
                            "|   94 = ^  |\n" +
                            "|   95 = _  |\n" +
                            "|   96 = `  |\n" +
                            "|   97 = a  |\n" +
                            "|   98 = b  |\n" +
                            "|   99 = c  |\n" +
                            "|  100 = d  |\n" +
                            "|  101 = e  |\n" +
                            "|  102 = f  |\n" +
                            "|  103 = g  |\n" +
                            "|  104 = h  |\n" +
                            "|  105 = i  |\n" +
                            "|  106 = j  |\n" +
                            "|  107 = k  |\n" +
                            "|  108 = l  |\n" +
                            "|  109 = m  |\n" +
                            "|  110 = n  |\n" +
                            "|  111 = o  |\n" +
                            "|  112 = p  |\n" +
                            "|  113 = q  |\n" +
                            "|  114 = r  |\n" +
                            "|  115 = s  |\n" +
                            "|  116 = t  |\n" +
                            "|  117 = u  |\n" +
                            "|  118 = v  |\n" +
                            "|  119 = w  |\n" +
                            "|  120 = x  |\n" +
                            "|  121 = y  |\n" +
                            "|  122 = z  |\n" +
                            "|  123 = {  |\n" +
                            "|  124 = |  |\n" +
                            "|  125 = }  |\n" +
                            "|  126 = ~  |\n" +
                            "-------------"
                )
            }

            @Test
            fun functionDeclaration() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/functionDeclaration.wacc"),
                    0
                )
            }

            @Test
            fun functionReturnPair() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/functionReturnPair.wacc"),
                    0, "10"
                )
            }

            @Test
            fun functionSimple() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/functionSimple.wacc"),
                    0, "0"
                )
            }

            @Test
            fun functionUpdateParameter() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/functionUpdateParameter.wacc"),
                    0, "y is 1\n" +
                            "x is 1\n" +
                            "x is now 5\n" +
                            "y is still 1"
                )
            }

            @Test
            fun incFunction() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/incFunction.wacc"),
                    0, "1\n" +
                            "4"
                )
            }

            @Test
            fun negFunction() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/negFunction.wacc"),
                    0, "true\n" +
                            "false\n" +
                            "true"
                )
            }

            @Test
            fun sameArgName() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/sameArgName.wacc"),
                    0, "99"
                )
            }

            @Test
            fun sameArgName2() {
                testCompile(
                    File("src/test/resources/valid/function/simple_functions/sameArgName2.wacc"),
                    0, "99"
                )
            }
        }
    }

    class If {
        @Test
        fun if1() {
            testCompile(
                File("src/test/resources/valid/if/if1.wacc"),
                0, "correct"
            )
        }

        @Test
        fun if2() {
            testCompile(
                File("src/test/resources/valid/if/if2.wacc"),
                0, "correct"
            )
        }

        @Test
        fun if3() {
            testCompile(
                File("src/test/resources/valid/if/if3.wacc"),
                0, "correct"
            )
        }

        @Test
        fun if4() {
            testCompile(
                File("src/test/resources/valid/if/if4.wacc"),
                0, "correct"
            )
        }

        @Test
        fun if5() {
            testCompile(
                File("src/test/resources/valid/if/if5.wacc"),
                0, "correct"
            )
        }

        @Test
        fun if6() {
            testCompile(
                File("src/test/resources/valid/if/if6.wacc"),
                0, "correct"
            )
        }

        @Test
        fun ifBasic() {
            testCompile(
                File("src/test/resources/valid/if/ifBasic.wacc"),
                0
            )
        }

        @Test
        fun ifFalse() {
            testCompile(
                File("src/test/resources/valid/if/ifFalse.wacc"),
                0, "here"
            )
        }

        @Test
        fun ifTrue() {
            testCompile(
                File("src/test/resources/valid/if/ifTrue.wacc"),
                0, "here"
            )
        }

        @Test
        fun whitespace() {
            testCompile(
                File("src/test/resources/valid/if/whitespace.wacc"),
                0, "1"
            )
        }
    }
    class Io {
        class Basic {}
        class Print {
            @Test
            fun hashInProgram() {
                testCompile(
                    File("src/test/resources/valid/IO/print/hashInProgram.wacc"),
                    0, "We can print the hash character: #\n" +
                            "We can also print # when its in a string."
                )
            }

            @Test
            fun multipleStringsAssignment() {
                testCompile(
                    File("src/test/resources/valid/IO/print/multipleStringsAssignment.wacc"),
                    0, "s1 is Hi\n" +
                            "s2 is Hi\n" +
                            "They are not the same.\n" +
                            "Now modify s1[0] = 'h'\n" +
                            "s1 is hi\n" +
                            "s2 is Hi\n" +
                            "They are not the same."
                )
            }

            @Test
            fun print() {
                testCompile(
                    File("src/test/resources/valid/IO/print/print.wacc"), 0, "Hello World!"
                )
            }

            @Test
            fun printBool() {
                testCompile(
                    File("src/test/resources/valid/IO/print/printBool.wacc"), 0, "True is true\n" + "False is false"
                )
            }

            @Test
            fun printChar() {
                testCompile(
                    File("src/test/resources/valid/IO/print/printChar.wacc"),
                    0, "A simple character example is f"
                )
            }

            @Test
            fun printEscChar() {
                testCompile(
                    File("src/test/resources/valid/IO/print/printEscChar.wacc"),
                    0, "An escaped character example is \""
                )
            }

            @Test
            fun printInt() {
                testCompile(
                    File("src/test/resources/valid/IO/print/printInt.wacc"),
                    0, "An example integer is 189"
                )
            }

            @Test
            fun println() {
                testCompile(
                    File("src/test/resources/valid/IO/print/println.wacc"),
                    0, "Hello World!"
                )
            }

            @Test
            fun stringAssignmentWithPrint() {
                testCompile(
                    File("src/test/resources/valid/IO/print/stringAssignmentWithPrint.wacc"),
                    0, "foo\n" +
                            "bar"
                )
            }
        }

        class Read {}
    }

    class Pairs {
        @Test
        fun checkRefPair() {
            testCompile(
                File("src/test/resources/valid/pairs/checkRefPair.wacc"),
                0, "true\n" +
                        "10\n" +
                        "10\n" +
                        "true\n" +
                        "a\n" +
                        "a\n" +
                        "true"
            )
        }

        @Test
        fun createPair() {
            testCompile(
                File("src/test/resources/valid/pairs/createPair.wacc"),
                0
            )
        }

        @Test
        fun createPair02() {
            testCompile(
                File("src/test/resources/valid/pairs/createPair02.wacc"),
                0
            )
        }

        @Test
        fun createPair03() {
            testCompile(
                File("src/test/resources/valid/pairs/createPair03.wacc"),
                0
            )
        }

        @Test
        fun createRefPair() {
            testCompile(
                File("src/test/resources/valid/pairs/createRefPair.wacc"),
                0
            )
        }

        @Test
        fun free() {
            testCompile(
                File("src/test/resources/valid/pairs/free.wacc"),
                0
            )
        }

        @Test
        fun linkedList() {
            testCompile(
                File("src/test/resources/valid/pairs/linkedList.wacc"),
                0, "list = {1, 2, 4, 11}"
            )
        }

        @Test
        fun nestedPair() {
            testCompile(
                File("src/test/resources/valid/pairs/nestedPair.wacc"),
                0
            )
        }

        @Test
        fun nullTest() {
            testCompile(
                File("src/test/resources/valid/pairs/null.wacc"),
                0, "(nil)\n" +
                        "(nil)"
            )
        }

        @Test
        fun printNull() {
            testCompile(
                File("src/test/resources/valid/pairs/printNull.wacc"),
                0, "(nil)"
            )
        }

        @Test
        fun printNullPair() {
            testCompile(
                File("src/test/resources/valid/pairs/printNullPair.wacc"),
                0, "(nil)"
            )
        }

        @Test
        fun printPair() {
            testCompile(
                File("src/test/resources/valid/pairs/printPair.wacc"),
                0, " = (10, a)"
            )
        }

        @Test
        fun printPairOfNulls() {
            testCompile(
                File("src/test/resources/valid/pairs/printPairOfNulls.wacc"),
                0, " = ((nil),(nil))"
            )
        }

        @Test
        fun writeFst() {
            testCompile(
                File("src/test/resources/valid/pairs/writeFst.wacc"),
                0, "10\n" +
                        "42"
            )
        }

        @Test
        fun writeSnd() {
            testCompile(
                File("src/test/resources/valid/pairs/writeSnd.wacc"),
                0, "a\n" +
                        "Z"
            )
        }
    }
    class RuntimeErr {
        class ArrayOutOfBounds {
            @Test
            fun arrayNegBounds() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/arrayOutOfBounds/arrayNegBounds.wacc"),
                    255
                )
            }
        }

        @Test
        fun arrayOutOfBounds() {
            testCompile(
                File("src/test/resources/valid/runtimeErr/arrayOutOfBounds/arrayOutOfBounds.wacc"),
                255
            )
        }

        @Test
        fun arrayOutOfBoundsWrite() {
            testCompile(
                File("src/test/resources/valid/runtimeErr/arrayOutOfBounds/arrayOutOfBoundsWrite.wacc"),
                255
            )
        }
        class DivideByZero {
            @Test
            fun divideByZero() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/divideByZero/divideByZero.wacc"),
                    255
                )
            }

            @Test
            fun divZero() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/divideByZero/divZero.wacc"),
                    255
                )
            }

            @Test
            fun modByZero() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/divideByZero/modByZero.wacc"),
                    255
                )
            }
        }
        class IntegerOverflow {
            @Test
            fun intJustOverflow() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intJustOverflow.wacc"),
                    255, "2147483646\n" +
                            "2147483647"
                )
            }

            @Test
            fun intmultOverflow() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intmultOverflow.wacc"),
                    255, "2147483\n" +
                            "2147483000"
                )
            }

            @Test
            fun intnegateOverflow() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intnegateOverflow.wacc"),
                    255, "-2147483648"
                )
            }

            @Test
            fun intnegateOverflow2() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intnegateOverflow2.wacc"),
                    255, "-2147483648"
                )
            }

            @Test
            fun intnegateOverflow3() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intnegateOverflow3.wacc"),
                    255, "-20000"
                )
            }

            @Test
            fun intnegateOverflow4() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intnegateOverflow4.wacc"),
                    255, "-2000000000"
                )
            }

            @Test
            fun intUnderflow() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intUnderflow.wacc"),
                    255, "-2147483647\n" +
                            "-2147483648"
                )
            }

            @Test
            fun intWayOverflow() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/integerOverflow/intWayOverflow.wacc"),
                    255, "2000000000"
                )
            }
        }
        class NullDereference {
            @Test
            fun freeNull() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/nullDereference/freeNull.wacc"),
                    255
                )
            }

            @Test
            fun setNull1() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/nullDereference/setNull1.wacc"),
                    255
                )
            }

            @Test
            fun setNull2() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/nullDereference/setNull2.wacc"),
                    255
                )
            }

            @Test
            fun useNull1() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/nullDereference/useNull1.wacc"),
                    255
                )
            }

            @Test
            fun useNull2() {
                testCompile(
                    File("src/test/resources/valid/runtimeErr/nullDereference/useNull2.wacc"),
                    255
                )
            }
        }
    }

    class Scope {
        @Test
        fun ifNested1() {
            testCompile(
                File("src/test/resources/valid/scope/ifNested1.wacc"),
                0, "correct"
            )
        }

        @Test
        fun ifNested2() {
            testCompile(
                File("src/test/resources/valid/scope/ifNested2.wacc"),
                0, "correct"
            )
        }

        @Test
        fun indentationNotImportant() {
            testCompile(
                File("src/test/resources/valid/scope/indentationNotImportant.wacc"),
                0
            )
        }

        @Test
        fun intsAndKeywords() {
            testCompile(
                File("src/test/resources/valid/scope/intsAndKeywords.wacc"),
                0
            )
        }

        @Test
        fun printAllTypes() {
            testCompile(
                File("src/test/resources/valid/scope/printAllTypes.wacc"),
                0, "( [1, 2, 3] , [a, b, c] )\n" +
                        "[  = (a, true),  = (b, false) ]\n" +
                        "1, 2\n" +
                        "array, of, strings\n" +
                        "true, false, true\n" +
                        "xyz\n" +
                        "1, 2, 3\n" +
                        "this is a string\n" +
                        "true\n" +
                        "x\n" +
                        "5"
            )
        }

        @Test
        fun scope() {
            testCompile(
                File("src/test/resources/valid/scope/scope.wacc"),
                0
            )
        }

        @Test
        fun scopeBasic() {
            testCompile(
                File("src/test/resources/valid/scope/scopeBasic.wacc"),
                0
            )
        }

        @Test
        fun scopeRedefine() {
            testCompile(
                File("src/test/resources/valid/scope/scopeRedefine.wacc"),
                0, "true\n" +
                        "2"
            )
        }

        @Test
        fun scopeSimpleRedefine() {
            testCompile(
                File("src/test/resources/valid/scope/scopeSimpleRedefine.wacc"),
                0, "true\n" +
                        "12"
            )
        }

        @Test
        fun scopeVars() {
            testCompile(
                File("src/test/resources/valid/scope/scopeVars.wacc"),
                0, "2\n" +
                        "4\n" +
                        "2"
            )
        }
    }
    class Sequence {
        @Test
        fun basicSeq() {
            testCompile(
                File("src/test/resources/valid/sequence/basicSeq.wacc"),
                0
            )
        }

        @Test
        fun basicSeq2() {
            testCompile(
                File("src/test/resources/valid/sequence/basicSeq2.wacc"),
                0
            )
        }

        @Test
        fun boolAssignment() {
            testCompile(
                File("src/test/resources/valid/sequence/boolAssignment.wacc"),
                0
            )
        }

        @Test
        fun charAssignment() {
            testCompile(
                File("src/test/resources/valid/sequence/charAssignment.wacc"),
                0
            )
        }

        @Test
        fun exitSimple() {
            testCompile(
                File("src/test/resources/valid/sequence/exitSimple.wacc"),
                42
            )
        }

        @Test
        fun intAssignment() {
            testCompile(
                File("src/test/resources/valid/sequence/intAssignment.wacc"),
                20
            )
        }

        @Test
        fun intLeadingZeros() {
            testCompile(
                File("src/test/resources/valid/sequence/intLeadingZeros.wacc"),
                0, "42\n" +
                        "0"
            )
        }

        @Test
        fun stringAssignment() {
            testCompile(
                File("src/test/resources/valid/sequence/stringAssignment.wacc"),
                0
            )
        }
    }
    class Variables {
        @Test
        fun _VarNames() {
            testCompile(
                File("src/test/resources/valid/variables/_VarNames.wacc"),
                19
            )
        }

        @Test
        fun boolDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/boolDeclaration.wacc"),
                0
            )
        }

        @Test
        fun boolDeclaration2() {
            testCompile(
                File("src/test/resources/valid/variables/boolDeclaration2.wacc"),
                0
            )
        }

        @Test
        fun capCharDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/capCharDeclaration.wacc"),
                0
            )
        }

        @Test
        fun charDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/charDeclaration.wacc"),
                0
            )
        }

        @Test
        fun charDeclaration2() {
            testCompile(
                File("src/test/resources/valid/variables/charDeclaration2.wacc"),
                0
            )
        }

        @Test
        fun emptyStringDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/emptyStringDeclaration.wacc"),
                0
            )
        }

        @Test
        fun intDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/intDeclaration.wacc"),
                0
            )
        }

        @Test
        fun longVarNames() {
            testCompile(
                File("src/test/resources/valid/variables/longVarNames.wacc"),
                5
            )
        }

        @Test
        fun manyVariables() {
            testCompile(
                File("src/test/resources/valid/variables/manyVariables.wacc"),
                0
            )
        }

        @Test
        fun negIntDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/negIntDeclaration.wacc"),
                0
            )
        }

        @Test
        fun puncCharDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/puncCharDeclaration.wacc"),
                0
            )
        }

        @Test
        fun stringDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/stringDeclaration.wacc"),
                0
            )
        }

        @Test
        fun zeroIntDeclaration() {
            testCompile(
                File("src/test/resources/valid/variables/zeroIntDeclaration.wacc"),
                0
            )
        }
    }
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
        @Test
        fun max() {
            testCompile(
                File("src/test/resources/valid/while/max.wacc"),
                0, "max value = 17"
            )
        }

        @Test
        fun min() {
            testCompile(
                File("src/test/resources/valid/while/min.wacc"),
                0, "min value = 10"
            )
        }

        @Test
        fun rmStyleAdd() {
            testCompile(
                File("src/test/resources/valid/while/rmStyleAdd.wacc"),
                0, "initial value of x: 3\n" +
                        "(+)(+)(+)(+)(+)(+)(+)\n" +
                        "final value of x: 10"
            )
        }

        @Test
        fun whileBasic() {
            testCompile(
                File("src/test/resources/valid/while/whileBasic.wacc"),
                0
            )
        }

        @Test
        fun whileBoolFlip() {
            testCompile(
                File("src/test/resources/valid/while/whileBoolFlip.wacc"),
                0, "flip b!\n" +
                        "end of loop"
            )
        }

        @Test
        fun whileCount() {
            testCompile(
                File("src/test/resources/valid/while/whileCount.wacc"),
                0, "Can you count to 10?\n" +
                        "1\n" +
                        "2\n" +
                        "3\n" +
                        "4\n" +
                        "5\n" +
                        "6\n" +
                        "7\n" +
                        "8\n" +
                        "9\n" +
                        "10"
            )
        }

        @Test
        fun whileFalse() {
            testCompile(
                File("src/test/resources/valid/while/whileFalse.wacc"),
                0, "end of loop"
            )
        }
    }


}
