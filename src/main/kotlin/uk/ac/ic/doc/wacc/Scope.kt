package uk.ac.ic.doc.wacc

open class Scope(var parent: Scope?) {
    var definitions : HashMap<String, Type> = HashMap()

    fun addDef(type:Type, variable:String) {
        definitions[variable] = type
    }

    fun checkDef(variable:String): Type? {
        if(parent == null) {
            return definitions[variable]
        }
        return if(definitions.containsKey(variable)) {
            definitions[variable]
        } else {
            parent!!.checkDef(variable)
        }
    }
}