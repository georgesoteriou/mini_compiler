package uk.ac.ic.doc.wacc.ast

sealed class Statement {

    data class VariableDeclaration(var lhs: Expression, var rhs: Expression) : Statement()
    data class VariableAssignment(var lhs: Expression, var rhs: Expression) : Statement()
    data class ReadInput(var expression: Expression) : Statement()
    data class FreeVariable(var expression: Expression) : Statement()
    data class Return(var expression: Expression) : Statement()
    data class Exit(var expression: Expression) : Statement()
    data class Print(var expression: Expression) : Statement()
    data class PrintLn(var expression: Expression) : Statement()
    data class If(var condition: Expression, var ifThen: Block, var elseThen: Block) : Statement()
    data class While(var condition: Expression, var then: Block) : Statement()
    class Block(var statements: List<Statement>) : Statement() {
        var scope: Scope = Scope()
    }
}

