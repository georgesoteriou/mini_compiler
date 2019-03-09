package uk.ac.ic.doc.wacc.ast

class AstOptimizer(val program: Program) {
    fun optimize() {
        program.functions.forEach { optimizeStatement(it.block) }
        program.block.statements.forEach(this::optimizeStatement)
    }

    private fun optimizeStatement(statement: Statement) {
        when (statement) {
            is Statement.VariableDeclaration -> {
                optimizeExpression(statement.rhs)
            }
            is Statement.VariableAssignment -> {
                optimizeExpression(statement.rhs)
            }
            is Statement.If -> {
                optimizeExpression(statement.condition)
                optimizeStatement(statement.ifThen)
                optimizeStatement(statement.elseThen)
            }
            is Statement.While -> {
                optimizeExpression(statement.condition)
                optimizeStatement(statement.then)
            }
            is Statement.Block -> {
                statement.statements.forEach(this::optimizeStatement)
            }
        }
    }

    private fun optimizeExpression(expression: Expression) {
        when (expression) {
            is Expression.BinaryOperation -> {
                val e1 = optimizeExpression(expression.e1)
                val e2 = optimizeExpression(expression.e2)
                //Expression.BinaryOperation(e1, e2, expression.operator)
            }
            is Expression.UnaryOperation -> {

            }
            else -> expression
        }
    }


}
