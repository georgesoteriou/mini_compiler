package uk.ac.ic.doc.wacc.ast

import java.lang.IllegalArgumentException

class Scope {
    var variables: MutableList<Expression.Variable> = arrayListOf()

    fun findVar(s : String) : Expression.Variable {
        variables.forEach() {
            if(it.name == s) {
                return it
            }
        }

        throw IllegalArgumentException("Variable not found")
    }
}
