package uk.ac.ic.doc.wacc.ast

import java.util.*

class ActiveScope(private var currentScope: Scope, private var parentScope: ActiveScope?) {

    fun getSize(): Int {
        return currentScope.getSize() + (parentScope?.getSize() ?: 0)
    }

    fun getPosition(name: String): Int {
       // var pos = currentScope.getPosition(name).orElse(parentScope?.currentScope?.getPosition(name)?.get() ?: -1)
        val pos = currentScope.getPosition(name)
        return if(pos.isPresent) {
            getSize() - (pos.get() + (parentScope?.getSize() ?: 0))
        } else {
            currentScope.getSize() + parentScope!!.getPosition(name)
        }
    }

    fun findDef(s: String): Optional<Type> =
        Optional.ofNullable(currentScope.definitions[s]?.type).or { parentScope?.findDef(s) ?: Optional.empty() }

    fun isVarInCurrScope(s: String): Boolean {
        if (currentScope.definitions[s] != null) {
            println("Error, $s already defined")
            return true
        }
        return false
    }

    fun add(def: Definition) {
        currentScope.definitions[def.name] = Scope.Definition(def.type, false)
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

    fun newSubScope(scope: Scope = Scope()): ActiveScope =
        ActiveScope(scope, this)

}

