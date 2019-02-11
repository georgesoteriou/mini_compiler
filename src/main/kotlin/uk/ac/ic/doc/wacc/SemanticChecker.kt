package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.ast.Statement
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



fun checkStatement(param: Statement, activeScope: ActiveScope): Boolean {

    return when (param) {
        is Statement.Block -> checkStatements(param, activeScope)



        is Statement.VariableDeclaration -> true
        /* check if lhs var exists in scope
            if it does then proboem
            else:
                evaluate lhs and rhs to see what type theu have
                    if types dont match -> problem
                    else
                        add to activescope
         */

        is Statement.VariableAssignment -> true
        else -> false
    }
}
