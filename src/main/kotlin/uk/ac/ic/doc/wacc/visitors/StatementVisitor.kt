package uk.ac.ic.doc.wacc.visitors

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.misc.ParseCancellationException
import uk.ac.ic.doc.wacc.ast.Definition
import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Scope
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

class StatementVisitor : WaccParserBaseVisitor<Statement>() {
    /*override fun visitSwitch(ctx: WaccParser.SwitchContext): Statement {
        val switch_block = arrayListOf<Statement>()
       val expr = ctx.expr().accept(ExprVisitor())
       ctx.switch_line().forEach{
           val cond = Expression.BinaryOperation(expr, it.expr().accept(ExprVisitor()), Expression.BinaryOperator.EQ)
           val if_stat = Statement.If(cond, it.stat_list().accept(this), Statement.Block(arrayListOf(), Scope()))
           switch_block.add(if_stat)
       }

        return Statement.Block(switch_block, Scope())
    }*/
    override fun visitWhen(ctx: WaccParser.WhenContext): Statement {
        val expr = ctx.expr().accept(ExprVisitor())
        val binop = ctx.getChild(2) as Expression.BinaryOperator

        val emptyBlock = Statement.Block(arrayListOf(), Scope())
        val whenIf = Statement.If(Expression.Literal.LBool(true), emptyBlock, emptyBlock)
        var currentIf = whenIf
        ctx.switch_line().forEach{
            val cond = Expression.BinaryOperation(expr, it.expr().accept(ExprVisitor()), binop)
            currentIf.condition = cond
            currentIf.ifThen = it.stat_list().accept(this)
            currentIf.elseThen = emptyBlock
            currentIf = Statement.If(Expression.Literal.LBool(true), emptyBlock, emptyBlock)
        }

        return Statement.Block(arrayListOf(whenIf), Scope())
    }

    override fun visitSide_eff_unary(ctx: WaccParser.Side_eff_unaryContext): Statement {
        val op = ctx.side_effs_unary().accept(UnSideEffVisitor())
        val ident = Expression.Identifier(ctx.IDENT().toString())
        val rhs = Expression.Literal.LInt(1)
        return Statement.VariableAssignment(ident, Expression.BinaryOperation(ident, rhs, op))
    }

    override fun visitSide_eff_binary(ctx: WaccParser.Side_eff_binaryContext): Statement {
        val op = ctx.side_effs_binary().accept(BinSideEffVisitor())
        val ident = Expression.Identifier(ctx.IDENT().toString())
        val rhs = ctx.expr().accept(ExprVisitor())

        return Statement.VariableAssignment(ident,Expression.BinaryOperation(ident, rhs, op))
    }

    private fun Statement.at(token: Token): Statement {
        location.lineNum = token.line
        location.colNum = token.charPositionInLine
        return this
    }

    override fun visitShort_if(ctx: WaccParser.Short_ifContext): Statement {
        val condition = ctx.expr().accept(ExprVisitor())
        val ifThen = ctx.stat_list().accept(this)
        return Statement.If(condition, ifThen, Statement.Block(arrayListOf(), Scope()))
            .at(ctx.start)
    }

    override fun visitDo_while(ctx: WaccParser.Do_whileContext): Statement {
        val block = ctx.stat_list().accept(this) as Statement.Block
        val whileStat = Statement.While(ctx.expr().accept(ExprVisitor()), block)
        val body = arrayListOf(block, whileStat)
        return Statement.Block(body, block.scope).at(ctx.start)
    }

    override fun visitFor(ctx: WaccParser.ForContext): Statement {
        val block = ctx.stat_list().accept(this) as Statement.Block
        val step = ctx.stat(1).accept(this)
        val whileBody = Statement.Block(arrayListOf(block, step), block.scope)
        val whileStat = Statement.While(ctx.expr().accept(ExprVisitor()), whileBody)
        val declStat = ctx.stat(0).accept(this) as? Statement.VariableDeclaration ?: throw ParseCancellationException()
        return Statement.Block(arrayListOf(declStat, whileStat), Scope()).at(ctx.start)
    }

    override fun visitDeclare(ctx: WaccParser.DeclareContext): Statement {
        val lhs = Definition(
            ctx.IDENT().toString(),
            ctx.type().accept(TypeVisitor())
        )
        val rhs = ctx.assign_rhs().accept(ExprVisitor())

        return Statement.VariableDeclaration(lhs, rhs)
            .at(ctx.start)
    }

    override fun visitAssign(ctx: WaccParser.AssignContext): Statement {
        val e = ExprVisitor()
        val lhs = ctx.assign_lhs().accept(e)
        val rhs = ctx.assign_rhs().accept(e)
        return Statement.VariableAssignment(lhs, rhs)
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

    override fun visitBegin(ctx: WaccParser.BeginContext): Statement = ctx.stat_list().accept(this)
        .at(ctx.start)

    override fun visitSkip(ctx: WaccParser.SkipContext): Statement = Statement.Skip()
        .at(ctx.start)

    override fun visitRead(ctx: WaccParser.ReadContext): Statement =
        Statement.ReadInput(ctx.assign_lhs().accept(ExprVisitor()))
            .at(ctx.start)

    override fun visitFree(ctx: WaccParser.FreeContext): Statement =
        Statement.FreeVariable(ctx.expr().accept(ExprVisitor()))
            .at(ctx.start)

    override fun visitReturn(ctx: WaccParser.ReturnContext): Statement =
        Statement.Return(ctx.expr().accept(ExprVisitor()))
            .at(ctx.start)

    override fun visitExit(ctx: WaccParser.ExitContext): Statement = Statement.Exit(ctx.expr().accept(ExprVisitor()))
        .at(ctx.start)

    override fun visitPrint(ctx: WaccParser.PrintContext): Statement = Statement.Print(ctx.expr().accept(ExprVisitor()))
        .at(ctx.start)

    override fun visitPrintln(ctx: WaccParser.PrintlnContext): Statement =
        Statement.PrintLn(ctx.expr().accept(ExprVisitor()))
            .at(ctx.start)

    override fun visitStat_list(ctx: WaccParser.Stat_listContext): Statement {
        val scope = Scope()
        val block = Statement.Block(ctx.stat().map { it.accept(this) }, scope)
        return block.at(ctx.start)
    }


}