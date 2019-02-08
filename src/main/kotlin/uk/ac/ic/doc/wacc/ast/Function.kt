package uk.ac.ic.doc.wacc.ast

class Function {

    var params: List<Expression.Variable> = ArrayList()
    var block: Statement.Block = Statement.Block(ArrayList())
}