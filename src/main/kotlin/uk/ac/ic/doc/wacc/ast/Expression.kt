package uk.ac.ic.doc.wacc.ast

sealed class Expression {

    var exprType:Type = Type.TAny
    data class CallFunction(var name: String, var params: List<Expression>) : Expression()

    data class NewPair(var e1: Expression, var e2: Expression) : Expression()

    data class Identifier(var name: String) : Expression()

    sealed class Literal : Expression() {
        data class LInt(var int: Int) : Literal()
        data class LBool(var bool: Boolean) : Literal()
        data class LChar(var char: Char) : Literal()
        data class LString(var string: String) : Literal()
        data class LArray(var params: List<Expression>) : Literal()
        object LPair : Literal()
    }

    enum class BinaryOperator {
        MULT, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NOTEQ, AND, OR
    }

    data class BinaryOperation(var e1: Expression, var e2: Expression, var operator: BinaryOperator) : Expression()

    enum class UnaryOperator {
        NOT, MINUS, LEN, ORD, CHR
    }

    data class UnaryOperation(var expression: Expression, var operator: UnaryOperator) : Expression()

    data class ArrayElem(var array: String, var indexes: List<Expression>) : Expression()
    data class Fst(var expression: Identifier) : Expression()
    data class Snd(var expression: Identifier) : Expression()


}
