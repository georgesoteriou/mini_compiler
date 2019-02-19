package uk.ac.ic.doc.wacc.ast

import java.util.*

class Scope {
    data class Definition(var type: Type, var isDeclared: Boolean)
    var definitions: LinkedHashMap<String, Definition> = linkedMapOf()

    fun getSize(): Int {
        return definitions.values.fold(0) { next, def ->
            next + Type.size(def.type)
        }
    }

   fun getPosition(name: String): Optional<Int> {
        var size = 0
        var found = false
        definitions.forEach { s, def ->
            if (!found) {
                size += Type.size(def.type)
                if (name == s) {
                    found = true
                }
            }
        }

       return if(!found) {
           Optional.empty()
       } else {
           Optional.of(size)
       }
    }

}
