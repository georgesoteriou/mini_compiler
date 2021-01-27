package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

class UnSideEffVisitor : WaccParserBaseVisitor<Expression.BinaryOperator>() {
    override fun visitPlus_plus(ctx: WaccParser.Plus_plusContext): Expression.BinaryOperator =
        Expression.BinaryOperator.PLUS


    override fun visitMinus_minus(ctx: WaccParser.Minus_minusContext): Expression.BinaryOperator =
        Expression.BinaryOperator.MINUS
}