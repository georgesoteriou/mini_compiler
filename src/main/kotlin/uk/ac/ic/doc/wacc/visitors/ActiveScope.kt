package uk.ac.ic.doc.wacc.visitors

import uk.ac.ic.doc.wacc.ast.Definition
import uk.ac.ic.doc.wacc.ast.Scope
import uk.ac.ic.doc.wacc.ast.Type
import java.util.*

class ActiveScope(private var currentScope: Scope, private var parentScope: ActiveScope?) {
    fun findDef(s: String): Optional<Type> =
        Optional.ofNullable(currentScope.definitions[s]).or { parentScope?.findDef(s) ?: Optional.empty() }

    fun isVarInCurrScope(s: String): Boolean {
        if (currentScope.definitions[s] != null) {
            println("Error, $s already defined")
            return true
        }
        return false
    }

    fun add(def: Definition) {
        currentScope.definitions[def.name] = def.type
    }

    fun addAll(defs: List<Definition>): Boolean {
        defs.forEach {
            if (isVarInCurrScope(it.name)) {
                return false
            }
            add(it)
        }
        return true
    }

    fun newSubScope(scope: Scope = Scope()): ActiveScope = ActiveScope(scope, this)

}

