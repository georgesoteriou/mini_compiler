import org.junit.Test
import java.io.File

class InvalidTests {
    class SemanticErr {
        class ContextExit {
            @Test
            fun badCharExit() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/exit/badCharExit.wacc"), 200
                )
            }

            @Test
            fun exitNonInt() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/exit/exitNonInt.wacc"), 200
                )
            }

            @Test
            fun globalReturn() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/exit/globalReturn.wacc"), 200
                )
            }
        }

        class ContextExpressions {
            @Test
            fun boolOpTypeErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/boolOpTypeErr.wacc"), 200
                )
            }

            @Test
            fun exprTypeErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/exprTypeErr.wacc"), 200
                )
            }

            @Test
            fun intOpTypeErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/intOpTypeErr.wacc"), 200
                )
            }

            @Test
            fun lessPairExpr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/lessPairExpr.wacc"), 200
                )
            }

            @Test
            fun mixedOpTypeErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/mixedOpTypeErr.wacc"), 200
                )
            }

            @Test
            fun moreArrExpr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/expressions/moreArrExpr.wacc"), 200
                )
            }
        }

        class Functions {
            @Test
            fun functionAssign() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionAssign.wacc"), 200
                )
            }

            @Test
            fun functionBadArgUse() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionBadArgUse.wacc"), 200
                )
            }

            @Test
            fun functionBadCall() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionBadCall.wacc"), 200
                )
            }

            @Test
            fun functionBadParam() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionBadParam.wacc"), 200
                )
            }

            @Test
            fun functionBadReturn() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionBadReturn.wacc"), 200
                )
            }

            @Test
            fun functionOverArgs() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionOverArgs.wacc"), 200
                )
            }

            @Test
            fun functionRedefine() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionRedefine.wacc"), 200
                )
            }

            @Test
            fun functionSwapArgs() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionSwapArgs.wacc"), 200
                )
            }

            @Test
            fun functionUnderArgs() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/functionUnderArgs.wacc"), 200
                )
            }

            @Test
            fun funcVarAccess() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/function/funcVarAccess.wacc"), 200
                )
            }
        }

        class If {
            @Test
            fun ifIntCondition() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/if/ifIntCondition.wacc"), 200
                )
            }
        }

        class Io {
            @Test
            fun readTypeErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/IO/readTypeErr.wacc"), 200
                )
            }
        }

        class Multiple {
            @Test
            fun funcMess() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/multiple/funcMess.wacc"), 200
                )
            }

            @Test
            fun ifAndWhileErrs() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/multiple/ifAndWhileErrs.wacc"), 200
                )
            }

            @Test
            fun messyExpr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/multiple/messyExpr.wacc"), 200
                )
            }

            @Test
            fun multiCaseSensitivity() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/multiple/multiCaseSensitivity.wacc"), 200
                )
            }

            @Test
            fun multiTypeErrs() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/multiple/multiTypeErrs.wacc"), 200
                )
            }
        }

        class Pairs {
            @Test
            fun freeNonPair() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/pairs/freeNonPair.wacc"), 200
                )
            }
        }

        class Print {
            @Test
            fun printTypeErr01() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/print/printTypeErr01.wacc"), 200
                )
            }


        }

        class Read {
            @Test
            fun readTypeErr01() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/read/readTypeErr01.wacc"), 200
                )
            }


        }

        class Scope {
            @Test
            fun badScopeRedefine() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/scope/badScopeRedefine.wacc"), 200
                )
            }
        }

        class Variables {
            @Test
            fun basicTypeErr01() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr01.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr02() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr02.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr03() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr03.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr04() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr04.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr05() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr05.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr06() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr06.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr07() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr07.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr08() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr08.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr09() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr09.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr10() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr10.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr11() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr11.wacc"), 200
                )
            }

            @Test
            fun basicTypeErr12() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/basicTypeErr12.wacc"), 200
                )
            }

            @Test
            fun caseMatters() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/caseMatters.wacc"), 200
                )
            }

            @Test
            fun doubleDeclare() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/doubleDeclare.wacc"), 200
                )
            }

            @Test
            fun undeclaredScopeVar() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/undeclaredScopeVar.wacc"), 200
                )
            }

            @Test
            fun undeclaredVar() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/undeclaredVar.wacc"), 200
                )
            }

            @Test
            fun undeclaredVarAccess() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/variables/undeclaredVarAccess.wacc"), 200
                )
            }
        }

        class While {
            @Test
            fun falsErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/while/falsErr.wacc"), 200
                )
            }

            @Test
            fun truErr() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/while/truErr.wacc"), 200
                )
            }

            @Test
            fun whileIntCondition() {
                testCompile(
                    File("src/test/resources/invalid/semanticErr/while/whileIntCondition.wacc"), 200
                )
            }
        }
    }

    class SyntaxErr {
        class Array {
            @Test
            fun arrayExpr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/array/arrayExpr.wacc"), 100
                )
            }

        }

        class Basic {
            @Test
            fun badComment() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/badComment.wacc"), 100
                )
            }

            @Test
            fun badComment2() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/badComment2.wacc"), 100
                )
            }

            @Test
            fun badEscape() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/badEscape.wacc"), 100
                )
            }

            @Test
            fun beginNoend() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/beginNoend.wacc"), 100
                )
            }

            @Test
            fun bgnErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/bgnErr.wacc"), 100
                )
            }

            @Test
            fun multipleBegins() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/multipleBegins.wacc"), 100
                )
            }

            @Test
            fun noBody() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/noBody.wacc"), 100
                )
            }

            @Test
            fun skpErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/skpErr.wacc"), 100
                )
            }

            @Test
            fun unescapedChar() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/basic/unescapedChar.wacc"), 100
                )
            }

        }

        class Expressions {
            @Test
            fun missingOperand1() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/expressions/missingOperand1.wacc"), 100
                )
            }

            @Test
            fun missingOperand2() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/expressions/missingOperand2.wacc"), 100
                )
            }

            @Test
            fun printlnConcat() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/expressions/printlnConcat.wacc"), 100
                )
            }

        }

        class Function {
            @Test
            fun badlyNamed() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/badlyNamed.wacc"), 100
                )
            }

            @Test
            fun badlyPlaced() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/badlyPlaced.wacc"), 100
                )
            }

            @Test
            fun funcExpr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/funcExpr.wacc"), 100
                )
            }

            @Test
            fun funcExpr2() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/funcExpr2.wacc"), 100
                )
            }

            @Test
            fun functionConditionalNoReturn() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionConditionalNoReturn.wacc"), 100
                )
            }

            @Test
            fun functionJunkAfterReturn() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionJunkAfterReturn.wacc"), 100
                )
            }

            @Test
            fun functionLateDefine() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionLateDefine.wacc"), 100
                )
            }

            @Test
            fun functionMissingCall() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionMissingCall.wacc"), 100
                )
            }

            @Test
            fun functionMissingParam() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionMissingParam.wacc"), 100
                )
            }

            @Test
            fun functionMissingPType() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionMissingPType.wacc"), 100
                )
            }

            @Test
            fun functionMissingType() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionMissingType.wacc"), 100
                )
            }

            @Test
            fun functionNoReturn() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionNoReturn.wacc"), 100
                )
            }

            @Test
            fun functionScopeDef() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/functionScopeDef.wacc"), 100
                )
            }

            @Test
            fun mutualRecursionNoReturn() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/mutualRecursionNoReturn.wacc"), 100
                )
            }

            @Test
            fun noBodyAfterFuncs() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/noBodyAfterFuncs.wacc"), 100
                )
            }

            @Test
            fun thisIsNotC() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/function/thisIsNotC.wacc"), 100
                )
            }

        }

        class If {
            @Test
            fun ifiErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/if/ifiErr.wacc"), 100
                )
            }

            @Test
            fun ifNoelse() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/if/ifNoelse.wacc"), 100
                )
            }

            @Test
            fun ifNofi() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/if/ifNofi.wacc"), 100
                )
            }

            @Test
            fun ifNothen() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/if/ifNothen.wacc"), 100
                )
            }

        }

        class Pairs {
            @Test
            fun badLookup01() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/pairs/badLookup01.wacc"), 100
                )
            }

            @Test
            fun badLookup02() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/pairs/badLookup02.wacc"), 100
                )
            }

        }

        class Sequence {
            @Test
            fun doubleSeq() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/sequence/doubleSeq.wacc"), 100
                )
            }

            @Test
            fun emptySeq() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/sequence/emptySeq.wacc"), 100
                )
            }

            @Test
            fun endSeq() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/sequence/endSeq.wacc"), 100
                )
            }

            @Test
            fun extraSeq() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/sequence/extraSeq.wacc"), 100
                )
            }

            @Test
            fun missingSeq() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/sequence/missingSeq.wacc"), 100
                )
            }

        }

        class Variables {
            @Test
            fun badintAssignments() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/variables/badintAssignments.wacc"), 100
                )
            }

            @Test
            fun bigIntAssignment() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/variables/bigIntAssignment.wacc"), 100
                )
            }

            @Test
            fun varNoName() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/variables/varNoName.wacc"), 100
                )
            }

        }

        class While {
            @Test
            fun donoErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/while/donoErr.wacc"), 100
                )
            }

            @Test
            fun dooErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/while/dooErr.wacc"), 100
                )
            }

            @Test
            fun whileNodo() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/while/whileNodo.wacc"), 100
                )
            }

            @Test
            fun whileNodone() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/while/whileNodone.wacc"), 100
                )
            }

            @Test
            fun whilErr() {
                testCompile(
                    File("src/test/resources/invalid/syntaxErr/while/whilErr.wacc"), 100
                )
            }

        }
    }

}



















