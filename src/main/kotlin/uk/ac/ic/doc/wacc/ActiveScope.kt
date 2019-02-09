package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Scope
import java.lang.IllegalArgumentException

class ActiveScope(var currentScope: Scope, var parentScope: ActiveScope?) {
    fun findVar(s : String) : Expression.Variable {
        currentScope.variables.forEach {
            if (it.name == s) {
                return it
            }
        }

        if (parentScope != null){
            return parentScope!!.findVar(s)
        }

        throw IllegalArgumentException("Variable not found")
    }
}

