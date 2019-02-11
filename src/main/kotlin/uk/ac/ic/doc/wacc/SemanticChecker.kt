package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.ast.Program
import uk.ac.ic.doc.wacc.ast.Statement
import uk.ac.ic.doc.wacc.visitors.ActiveScope

fun semanticCheck (prog: Program) {

}


fun checkStatement(param: Statement, scope: ActiveScope) {

    when (param) {
        is Statement.VariableDeclaration -> {}
        /* check if lhs var exists in scope
            if it does then proboem
            else:
                evaluate lhs and rhs to see what type theu have
                    if types dont match -> problem
                    else
                        add to activescope
         */

        is Statement.VariableAssignment -> {}
    }
}


fun semanticChecker (prog: Program)
{

}