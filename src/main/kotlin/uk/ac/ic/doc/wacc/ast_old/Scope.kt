package uk.ac.ic.doc.wacc.ast_old

open class Scope(parent: Node?, scope: Scope?) : Node(parent, scope) {
    var definitions: HashMap<String, Type> = HashMap()
}