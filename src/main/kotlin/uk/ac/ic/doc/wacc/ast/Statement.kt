package uk.ac.ic.doc.wacc.ast

sealed class Statement {

    var location = Location(0, 0)
    var context = ""

    class Skip : Statement()
    data class VariableDeclaration(var lhs: Definition, var rhs: Expression) : Statement()
    data class VariableAssignment(var lhs: Expression, var rhs: Expression) : Statement()
    data class ReadInput(var expression: Expression) : Statement()
    data class FreeVariable(var expression: Expression) : Statement()
    data class Return(var expression: Expression) : Statement()
    data class Exit(var expression: Expression) : Statement()
    data class Print(var expression: Expression) : Statement()
    data class PrintLn(var expression: Expression) : Statement()
    data class If(var condition: Expression, var ifThen: Statement.Block, var elseThen: Statement.Block) : Statement()
    data class While(var condition: Expression, var then: Statement.Block) : Statement()
    data class Block(var statements: List<Statement>, var scope: Scope) : Statement()
}

