package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Expression
import uk.ac.ic.doc.wacc.ast.Scope
import uk.ac.ic.doc.wacc.ast.Type
import java.lang.IllegalArgumentException

class ActiveScope(var currentScope: Scope, var parentScope: ActiveScope?) {
    fun findVar(s : Expression.Identifier): Expression.Variable? {
        currentScope.variables.forEach {
            when(it) {
                is Expression.Variable -> if (it.name == s) { return it }
            }
        }
        return if (parentScope == null){
            null
        } else {
            parentScope!!.findVar(s)
        }
    }

    fun findType(s : Expression.Identifier): Type {
        val ret = findVar(s)
        return ret?.type ?: Type.TError
    }

    fun isVarInCurrScope(s: Expression.Identifier): Boolean {
        currentScope.variables.forEach {
            when(it) {
                is Expression.Variable -> if (it.name == s) { return true }
            }
        }
        return false
    }
}

var activeScope: ActiveScope? = ActiveScope(Scope(), null)
