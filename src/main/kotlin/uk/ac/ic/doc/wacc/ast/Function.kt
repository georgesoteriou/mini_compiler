package uk.ac.ic.doc.wacc.ast

class Function(parent: Node?, Scope: Scope?, name: String, parameters: ArrayList<Type>) : Scope(parent, Scope) {
    var defined: Boolean = false
}