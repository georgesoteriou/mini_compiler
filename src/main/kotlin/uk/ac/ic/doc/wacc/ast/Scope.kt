package uk.ac.ic.doc.wacc.ast

open class Scope(parent: Node?, scope: Scope?) : Node(parent, scope) {
    var definitions: HashMap<String, Type> = HashMap()
}