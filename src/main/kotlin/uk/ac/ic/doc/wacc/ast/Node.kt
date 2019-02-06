package uk.ac.ic.doc.wacc.ast

open class Node(var parent: Node?, var scope: Scope? ) {
    var children : ArrayList<Node> = ArrayList()
}