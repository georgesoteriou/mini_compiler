package uk.ac.ic.doc.wacc.ast

sealed class Expression {

    data class Variable(var name: Identifier, var type: Type): Expression()
    data class CallFunction(var name: Identifier, var params: Expression?): Expression()

    data class ExpressionList(var expressions: List<Expression>) : Expression()
    data class ExpressionPair(var e1: Expression, var e2: Expression) : Expression()

    data class Identifier(var name: String): Expression()

    sealed class Literal: Expression() {
        data class LInt(var int: String): Literal()
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
        data class UChr (var expression: Expression): UnaryOperator()
    }

    data class ArrayElem(var array: Expression, var indexes: Expression): Expression()
    data class Fst  (var expression: Expression): Expression()
    data class Snd  (var expression: Expression): Expression()


}
