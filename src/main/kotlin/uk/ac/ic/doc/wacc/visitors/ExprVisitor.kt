package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import java.security.InvalidParameterException

class ExprVisitor: WaccParserBaseVisitor<Expression>() {


    override fun visitArg_list(ctx: WaccParser.Arg_listContext): Expression
            = Expression.ExpressionList(ctx.expr().map { it.accept(this) })

    override fun visitCallFunc(ctx: WaccParser.CallFuncContext): Expression {
        val name = Expression.Identifier(ctx.IDENT().toString())
        return when {
            ctx.arg_list() != null -> Expression.CallFunction(name, ctx.arg_list().accept(this))
            else -> Expression.CallFunction(name,null)
        }
    }

    override fun visitBinaryOp(ctx: WaccParser.BinaryOpContext): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.op.MULT()  != null -> Expression.BinaryOperator.BMult(e1, e2)
            ctx.op.DIV()   != null -> Expression.BinaryOperator.BDiv(e1, e2)
            ctx.op.MOD()   != null -> Expression.BinaryOperator.BMod(e1, e2)
            ctx.op.PLUS()  != null -> Expression.BinaryOperator.BPlus(e1, e2)
            ctx.op.MINUS() != null -> Expression.BinaryOperator.BMinus(e1, e2)
            ctx.op.GT()    != null -> Expression.BinaryOperator.BGT(e1, e2)
            ctx.op.GTE()   != null -> Expression.BinaryOperator.BGTE(e1, e2)
            ctx.op.LT()    != null -> Expression.BinaryOperator.BLT(e1, e2)
            ctx.op.LTE()   != null -> Expression.BinaryOperator.BLTE(e1, e2)
            ctx.op.EQ()    != null -> Expression.BinaryOperator.BEQ(e1, e2)
            ctx.op.NOTEQ() != null -> Expression.BinaryOperator.BNotEQ(e1, e2)
            ctx.op.AND()   != null -> Expression.BinaryOperator.BAnd(e1, e2)
            ctx.op.OR()    != null -> Expression.BinaryOperator.BOr(e1, e2)
            else -> throw InvalidParameterException("Binary Op does not exist")
        }

        /*
        when (a)  {
            is Expression.BinaryOperator.BMult -> // repeat onwards for BPlus, BMinus, BMult, BMod, BDiv
                /*
                    Type x = evaluate e1 recursively to obtain overall type
                    Type y = evaluate e2 recursively to obtain overall type

                    add an extra type : error type

                    if x or y is an errortype then problem
                        if x and y dont have the same type then problem

                 */ */
    }


    override fun visitUnaryOp(ctx: WaccParser.UnaryOpContext): Expression {
        val e = ctx.expr().accept(this)
        return when {
            ctx.unaryOper().NOT()   != null -> Expression.UnaryOperator.UNot(e)
            ctx.unaryOper().MINUS() != null -> Expression.UnaryOperator.UMinus(e)
            ctx.unaryOper().LEN()   != null -> Expression.UnaryOperator.ULen(e)
            ctx.unaryOper().ORD()   != null -> Expression.UnaryOperator.UOrd(e)
            ctx.unaryOper().CHR()   != null -> Expression.UnaryOperator.UChr(e)
            else -> throw InvalidParameterException("Unary Op does not exist")
        }
    }

    override fun visitParenth(ctx: WaccParser.ParenthContext): Expression
        = ctx.expr().accept(this)

    override fun visitIntLit(ctx: WaccParser.IntLitContext): Expression
        = Expression.Literal.LInt(
        if (ctx.MINUS() != null) {
            "-"+ctx.INT_LITER().toString()
        } else {
            ctx.INT_LITER().toString()
        })

    override fun visitBoolLit(ctx: WaccParser.BoolLitContext): Expression
            = Expression.Literal.LBool(ctx.BOOL_LITER().toString().toBoolean())

    override fun visitCharLit(ctx: WaccParser.CharLitContext): Expression
            = Expression.Literal.LChar(ctx.CHAR_LITER().toString().first())

    override fun visitStrLit(ctx: WaccParser.StrLitContext): Expression
            = Expression.Literal.LString(ctx.STR_LITER().toString())

    override fun visitPairLit(ctx: WaccParser.PairLitContext): Expression
            = Expression.Literal.LPair

    override fun visitNewPair(ctx: WaccParser.NewPairContext): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)
        return Expression.ExpressionPair(e1, e2)
    }

    override fun visitArray_liter(ctx: WaccParser.Array_literContext): Expression {
        return Expression.ExpressionList(ctx.expr().map { it.accept(this) })
    }

    override fun visitArray_elem(ctx: WaccParser.Array_elemContext): Expression {
        val array =  Expression.Identifier(ctx.IDENT().toString())
        val indexes = Expression.ExpressionList(ctx.expr().map { it.accept(this) })
        return Expression.ArrayElem(array, indexes)
    }

    override fun visitIdent(ctx: WaccParser.IdentContext): Expression
            = Expression.Identifier(ctx.IDENT().toString())

    override fun visitLhsIdent(ctx: WaccParser.LhsIdentContext): Expression
            = Expression.Identifier(ctx.IDENT().toString())

    override fun visitFst(ctx: WaccParser.FstContext): Expression
        = Expression.Fst(ctx.expr().accept(this))

    override fun visitSnd(ctx: WaccParser.SndContext): Expression
        = Expression.Snd(ctx.expr().accept(this))

}