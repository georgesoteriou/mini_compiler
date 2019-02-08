package uk.ac.ic.doc.wacc.ast

open class Program {
    var functions: List<Function> = ArrayList()
    var block: Statement.Block = Statement.Block(ArrayList())
}