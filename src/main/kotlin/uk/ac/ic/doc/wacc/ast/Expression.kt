package uk.ac.ic.doc.wacc.ast

sealed class Expression {

    data class Variable(var name: String, var type: Type): Expression()
    data class CallFunction(var name: String, var params: List<Expression>): Expression()
    data class ArrayElem(var name: String, var indexes: List<Expression>): Expression()


    sealed class Literal: Expression() {
        data class LInt(var int: Int): Literal()
        data class LBool(var bool: Boolean): Literal()
        data class LChar(var char: Char): Literal()
        data class LString(var string: String): Literal()
        object LPair: Literal()
    }

    sealed class BinaryOperator: Expression() {
        data class BMult (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BDiv  (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BMod  (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BPlus (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BMinus(var e1: Expression, var e2: Expression): BinaryOperator()
        data class BGT   (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BGTE  (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BLT   (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BLTE  (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BEQ   (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BNotEQ(var e1: Expression, var e2: Expression): BinaryOperator()
        data class BAnd  (var e1: Expression, var e2: Expression): BinaryOperator()
        data class BOr   (var e1: Expression, var e2: Expression): BinaryOperator()
    }

    sealed class UnaryOperator: Expression() {
        data class UNot  (var expression: Expression): UnaryOperator()
        data class UMinus(var expression: Expression): UnaryOperator()
        data class ULen  (var expression: Expression): UnaryOperator()
        data class UOrd  (var expression: Expression): UnaryOperator()
        data class UChar (var expression: Expression): UnaryOperator()
    }

    data class UFst  (var expression: Expression): Expression()
    data class USnd  (var expression: Expression): Expression()


}
