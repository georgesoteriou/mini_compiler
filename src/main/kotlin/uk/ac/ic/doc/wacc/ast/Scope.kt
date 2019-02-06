package uk.ac.ic.doc.wacc.ast

open class Scope(parent: Node?, Scope: Scope?) : Node(parent, Scope) {
    var definitions: HashMap<Ident, Type> = HashMap()
}