package uk.ac.ic.doc.wacc

import org.antlr.v4.runtime.Token
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.ast.Type
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor
import java.security.InvalidParameterException

lateinit var activeScope: ActiveScope

class StatementVisitor: WaccParserBaseVisitor<Statement>() {
    
    fun Statement.at(token: Token): Statement {
        location.lineNum = token.line
        location.colNum = token.charPositionInLine
        return this
    }

    override fun visitDeclare(ctx: WaccParser.DeclareContext): Statement {
        val lhs = Expression.Variable(ctx.IDENT().toString(), ctx.type().accept(TypeVisitor()))
        val rhs = ctx.assign_rhs().accept(ExprVisitor())
        activeScope.currentScope.variables.add(lhs)
        return Statement.VariableDeclaration(lhs,rhs)
            .at(ctx.start)
    }

    override fun visitAssign(ctx: WaccParser.AssignContext): Statement {
        val e = ExprVisitor()
        val lhs = ctx.assign_lhs().accept(e)
        val rhs = ctx.assign_rhs().accept(e)
        return Statement.VariableAssignment(lhs,rhs)
            .at(ctx.start)
    }

    override fun visitIf(ctx: WaccParser.IfContext): Statement {
        val condition = ctx.expr().accept(ExprVisitor())
        val ifThen = ctx.stat_list(0).accept(this)
        val elseThen = ctx.stat_list(1).accept(this)
        return Statement.If(condition, ifThen, elseThen)
            .at(ctx.start)
    }

    override fun visitWhile(ctx: WaccParser.WhileContext): Statement {
        val condition = ctx.expr().accept(ExprVisitor())
        val then = ctx.stat_list().accept(this)
        return Statement.While(condition, then)
            .at(ctx.start)
    }

    override fun visitBegin(ctx: WaccParser.BeginContext): Statement
            = ctx.stat_list().accept(this)
                .at(ctx.start)

    override fun visitSkip(ctx: WaccParser.SkipContext): Statement
            = Statement.Skip()
                .at(ctx.start)

    override fun visitRead(ctx: WaccParser.ReadContext): Statement
            = Statement.ReadInput(ctx.assign_lhs().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitFree(ctx: WaccParser.FreeContext): Statement
            = Statement.FreeVariable(ctx.expr().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitReturn(ctx: WaccParser.ReturnContext): Statement
            = Statement.Return(ctx.expr().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitExit(ctx: WaccParser.ExitContext): Statement
            = Statement.Exit(ctx.expr().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitPrint(ctx: WaccParser.PrintContext): Statement
            = Statement.Print(ctx.expr().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitPrintln(ctx: WaccParser.PrintlnContext): Statement
            = Statement.PrintLn(ctx.expr().accept(ExprVisitor()))
                .at(ctx.start)

    override fun visitStat_list(ctx: WaccParser.Stat_listContext): Statement {
        val block = Statement.Block(ctx.stat().map { it.accept(this) })
        activeScope = ActiveScope(block.scope, activeScope)
        return block.at(ctx.start)
    }

}

class ExprVisitor: WaccParserBaseVisitor<Expression>() {
    override fun visitArray_elem(ctx: WaccParser.Array_elemContext): Expression {
        val name = ctx.IDENT().toString()
        val indexes = ctx.expr().map { it.accept(this) }
        return Expression.ArrayElem(name, indexes)
    }

    override fun visitBinaryOp(ctx: WaccParser.BinaryOpContext): Expression {
        val e1 = ctx.expr(0).accept(this)
        val e2 = ctx.expr(1).accept(this)

        return when {
            ctx.op.MULT()  != null -> Expression.BinaryOperator.BMult (e1, e2)
            ctx.op.DIV()   != null -> Expression.BinaryOperator.BDiv  (e1, e2)
            ctx.op.MOD()   != null -> Expression.BinaryOperator.BMod  (e1, e2)
            ctx.op.PLUS()  != null -> Expression.BinaryOperator.BPlus (e1, e2)
            ctx.op.MINUS() != null -> Expression.BinaryOperator.BMinus(e1, e2)
            ctx.op.GT()    != null -> Expression.BinaryOperator.BGT   (e1, e2)
            ctx.op.GTE()   != null -> Expression.BinaryOperator.BGTE  (e1, e2)
            ctx.op.LT()    != null -> Expression.BinaryOperator.BLT   (e1, e2)
            ctx.op.LTE()   != null -> Expression.BinaryOperator.BLTE  (e1, e2)
            ctx.op.EQ()    != null -> Expression.BinaryOperator.BEQ   (e1, e2)
            ctx.op.NOTEQ() != null -> Expression.BinaryOperator.BNotEQ(e1, e2)
            ctx.op.AND()   != null -> Expression.BinaryOperator.BAnd  (e1, e2)
            ctx.op.OR()    != null -> Expression.BinaryOperator.BOr   (e1, e2)
            else -> throw InvalidParameterException("Binary Op does not exist")
        }
    }

    override fun visitUnaryOp(ctx: WaccParser.UnaryOpContext): Expression {
        val e = ctx.expr().accept(this)
        return when {
            ctx.unaryOper().NOT()   != null -> Expression.UnaryOperator.UNot  (e)
            ctx.unaryOper().MINUS() != null -> Expression.UnaryOperator.UMinus(e)
            ctx.unaryOper().LEN()   != null -> Expression.UnaryOperator.ULen  (e)
            ctx.unaryOper().ORD()   != null -> Expression.UnaryOperator.UOrd  (e)
            ctx.unaryOper().CHR()   != null -> Expression.UnaryOperator.UChar (e)
            else -> throw InvalidParameterException("Unary Op does not exist")
        }
    }

    override fun visitParenth(ctx: WaccParser.ParenthContext): Expression
        = ctx.expr().accept(this)

    override fun visitIntLit(ctx: WaccParser.IntLitContext): Expression
        = Expression.Literal.LInt( if(ctx.MINUS() != null) {
             -Integer.parseInt(ctx.INT_LITER().toString())
        } else {
             Integer.parseInt(ctx.INT_LITER().toString())
        })

    override fun visitBoolLit(ctx: WaccParser.BoolLitContext): Expression
        = Expression.Literal.LBool(ctx.BOOL_LITER().toString().toBoolean())

    override fun visitCharLit(ctx: WaccParser.CharLitContext): Expression
        = Expression.Literal.LChar(ctx.CHAR_LITER().toString().first())

    override fun visitStrLit(ctx: WaccParser.StrLitContext): Expression
        =  Expression.Literal.LString(ctx.STR_LITER().toString())

    override fun visitPairLit(ctx: WaccParser.PairLitContext): Expression
        = Expression.Literal.LPair

    override fun visitIdent(ctx: WaccParser.IdentContext): Expression {
        return activeScope.currentScope.findVar(ctx.IDENT().toString()) as Expression
    }

}

class TypeVisitor: WaccParserBaseVisitor<Type>() {

}