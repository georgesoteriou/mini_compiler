package uk.ac.ic.doc.wacc.ast_old

open class Node(var parent: Node?, var scope: Scope? ) {
    var children : ArrayList<Node> = ArrayList()
}