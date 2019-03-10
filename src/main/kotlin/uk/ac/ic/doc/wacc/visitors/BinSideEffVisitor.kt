package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

class BinSideEffVisitor : WaccParserBaseVisitor<Expression.BinaryOperator>() {
    override fun visitPlus_eq(ctx: WaccParser.Plus_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.PLUS

    override fun visitMinus_eq(ctx: WaccParser.Minus_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.MINUS

    override fun visitMult_eq(ctx: WaccParser.Mult_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.MULT

    override fun visitDiv_eq(ctx: WaccParser.Div_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.DIV

    override fun visitMod_eq(ctx: WaccParser.Mod_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.MOD

    override fun visitAnd_eq(ctx: WaccParser.And_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.AND

    override fun visitOr_eq(ctx: WaccParser.Or_eqContext?): Expression.BinaryOperator =
        Expression.BinaryOperator.OR
}