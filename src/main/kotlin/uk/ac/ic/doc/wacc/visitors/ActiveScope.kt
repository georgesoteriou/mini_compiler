package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Scope
import java.lang.IllegalArgumentException

class ActiveScope(var currentScope: Scope, var parentScope: ActiveScope?) {
    fun findVar(s : Expression.Identifier) : Expression.Variable {
        currentScope.variables.forEach {
            when(it) {
                is Expression.Variable -> if (it.name == s) { return it }
            }
        }

        if (parentScope != null){
            return parentScope!!.findVar(s)
        }

        throw IllegalArgumentException("Variable \"$s\" not found")
    }
}

var activeScope: ActiveScope? = ActiveScope(Scope(), null)
