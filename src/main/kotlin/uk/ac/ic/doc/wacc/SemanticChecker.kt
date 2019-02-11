package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.visitors.ActiveScope

fun semanticCheck (prog: Program) {

}


fun checkStatements(block: Statement.Block, activeScope: ActiveScope): Boolean {
    val newActiveScope = ActiveScope(block.scope, activeScope)
    var valid = true
    block.statements.forEach{
        valid = valid && checkStatement(it, newActiveScope)
    }
    return valid
}

fun exprType(expr: Expression, activeScope: ActiveScope) : Type {


    return Type.TError
}

fun checkStatement(param: Statement, activeScope: ActiveScope): Boolean {

    return when (param) {
        is Statement.Block -> checkStatements(param, activeScope)

        is Statement.While -> {
            var ret =  exprType(param.condition, activeScope) !is Type.TBool
            ret = ret && checkStatements(param.then as Statement.Block, activeScope)
            return ret
        }
        /*


         */

        is Statement.If -> true
        /*


         */

        is Statement.PrintLn -> true
        /*
            evaluate expression recursively to see if:
                expression has a uniform type
                if no then problem
                if yes then check if type is printable

         */

        is Statement.Print -> true
        /*
            same as above
         */

        is Statement.Exit -> true
        /*

         */

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
