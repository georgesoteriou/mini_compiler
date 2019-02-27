package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import java.util.*

class ExprVisitor : WaccParserBaseVisitor<Expression>() {

    override fun visitCallFunc(ctx: WaccParser.CallFuncContext): Expression {
        val name = ctx.IDENT().toString()
        return Expression.CallFunction(name, ctx.arg_list()?.expr()?.map {
            it.accept(this)
        } ?: Collections.emptyList())

    }

    override fun visitBinaryOp1(ctx: WaccParser.BinaryOp1Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.MULT() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.MULT)
            ctx.DIV() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.DIV)
            ctx.MOD() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.MOD)
            else -> throw RuntimeException("Binary Op does not exist")
        }
    }

    override fun visitBinaryOp2(ctx: WaccParser.BinaryOp2Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.PLUS() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.PLUS)
            ctx.MINUS() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.MINUS)
            else -> throw RuntimeException("Binary Op does not exist")
        }
    }

    override fun visitBinaryOp3(ctx: WaccParser.BinaryOp3Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.GT() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.GT)
            ctx.GTE() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.GTE)
            ctx.LT() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.LT)
            ctx.LTE() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.LTE)
            else -> throw RuntimeException("Binary Op does not exist")
        }
    }

    override fun visitBinaryOp4(ctx: WaccParser.BinaryOp4Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.EQ() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.EQ)
            ctx.NOTEQ() != null -> Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.NOTEQ)
            else -> throw RuntimeException("Binary Op does not exist")
        }
    }

    override fun visitBinaryOp5(ctx: WaccParser.BinaryOp5Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)
        return Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.AND)
    }

    override fun visitBinaryOp6(ctx: WaccParser.BinaryOp6Context): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)
        return Expression.BinaryOperation(e1, e2, Expression.BinaryOperator.OR)
    }


    override fun visitUnaryOp(ctx: WaccParser.UnaryOpContext): Expression {
        val e = ctx.expr().accept(this)
        return when {
            ctx.unaryOper().NOT()  != null -> Expression.UnaryOperation(e, Expression.UnaryOperator.NOT)
            ctx.unaryOper().MINUS()!= null -> Expression.UnaryOperation(e, Expression.UnaryOperator.MINUS)
            ctx.unaryOper().LEN()  != null -> Expression.UnaryOperation(e, Expression.UnaryOperator.LEN)
            ctx.unaryOper().ORD()  != null -> Expression.UnaryOperation(e, Expression.UnaryOperator.ORD)
            ctx.unaryOper().CHR()  != null -> Expression.UnaryOperation(e, Expression.UnaryOperator.CHR)
            else -> throw RuntimeException("Unary Op does not exist")
        }
    }

    override fun visitParenth(ctx: WaccParser.ParenthContext): Expression = ctx.expr().accept(this)

    override fun visitIntLit(ctx: WaccParser.IntLitContext): Expression = Expression.Literal.LInt(
        if (ctx.MINUS() != null) {
            "-" + ctx.INT_LITER().toString()
        } else {
            ctx.INT_LITER().toString()
        }
    )

    override fun visitBoolLit(ctx: WaccParser.BoolLitContext): Expression =
        Expression.Literal.LBool(ctx.BOOL_LITER().toString().toBoolean())

    override fun visitCharLit(ctx: WaccParser.CharLitContext): Expression {
        var foundString = ctx.CHAR_LITER().toString()
        foundString = foundString.reversed()
        //println(foundString)
        var charFound:Char = foundString[1]
        return Expression.Literal.LChar(charFound)
    }

    override fun visitStrLit(ctx: WaccParser.StrLitContext): Expression =
        Expression.Literal.LString(ctx.STR_LITER().toString().substring(1,ctx.STR_LITER().toString().length-1))

    override fun visitPairLit(ctx: WaccParser.PairLitContext): Expression = Expression.Literal.LPair

    override fun visitNewPair(ctx: WaccParser.NewPairContext): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)
        return Expression.NewPair(e1, e2)
    }

    override fun visitArray_liter(ctx: WaccParser.Array_literContext): Expression {
        return Expression.Literal.LArray(ctx.expr().map { it.accept(this) })
    }

    override fun visitArray_elem(ctx: WaccParser.Array_elemContext): Expression {
        val array = ctx.IDENT().toString()
        val indexes = ctx.expr().map { it.accept(this) }
        return Expression.ArrayElem(array, indexes)
    }

    override fun visitIdent(ctx: WaccParser.IdentContext): Expression = Expression.Identifier(ctx.IDENT().toString())

    override fun visitLhsIdent(ctx: WaccParser.LhsIdentContext): Expression =
        Expression.Identifier(ctx.IDENT().toString())

    override fun visitFst(ctx: WaccParser.FstContext): Expression = Expression.Fst(ctx.expr().accept(this))

    override fun visitSnd(ctx: WaccParser.SndContext): Expression = Expression.Snd(ctx.expr().accept(this))

}