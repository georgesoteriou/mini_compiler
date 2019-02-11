package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.visitors.ActiveScope

fun semanticCheck (prog: Program) {
    var valid = true
    prog.functions.forEach{
        val scope = (it.block as Statement.Block).scope
        (it.params as Expression.ExpressionList).expressions.forEach{
            scope.variables.add(it)
        }
        valid = valid && checkStatements(it.block as Statement.Block, ActiveScope(Scope(),null),it.returnType)
    }

}


fun checkStatements(block: Statement.Block, activeScope: ActiveScope, returnType: Type): Boolean {
    val newActiveScope = ActiveScope(block.scope, activeScope)
    var valid = true
    block.statements.forEach{
        valid = valid && checkStatement(it, newActiveScope, returnType)
    }
    return valid
}

fun exprType(expr: Expression, activeScope: ActiveScope) : Type {
        /* FOR PRINT:
            evaluate expression recursively to see if:
                expression has a uniform type
                if no then problem
                if yes then check if type is printable

         */

    return Type.TError
}

fun checkStatement(param: Statement, activeScope: ActiveScope, returnType:Type): Boolean {
    return when (param) {
        is Statement.Block -> checkStatements(param, activeScope,returnType)

        is Statement.While -> exprType(param.condition, activeScope) !is Type.TBool
                && checkStatements(param.then as Statement.Block, activeScope,returnType)

        is Statement.If -> exprType(param.condition, activeScope) !is Type.TBool
                && checkStatements(param.ifThen as Statement.Block, activeScope,returnType)
                && checkStatements(param.elseThen as Statement.Block, activeScope,returnType)


        is Statement.PrintLn -> exprType(param.expression,activeScope) !is Type.TError



        is Statement.Print -> exprType(param.expression,activeScope) !is Type.TError

        is Statement.Exit -> exprType(param.expression,activeScope) !is Type.TInt


        is Statement.Return -> true

        /*
            evaluate expression recursively to see if uniform type
            if uniform type then see if returnable
            if not uniform then problem

         */

        is Statement.FreeVariable -> true
        /*


         */

        is Statement.ReadInput -> true
        /*

         */

        is Statement.VariableAssignment -> true
        /*

            check if lhs var exists in scope
            if it doesnt -> problem
            if it does:
                evaluate rhs to see what type
                if types dont match -> problem
                else : //todo
         */

        is Statement.VariableDeclaration -> true
        /* check if lhs var exists in scope
            if it does then proboem
            else:
                evaluate lhs and rhs to see what type theu have
                    if types dont match -> problem
                    else
                        add to activescope
         */


        else -> false
    }
}
