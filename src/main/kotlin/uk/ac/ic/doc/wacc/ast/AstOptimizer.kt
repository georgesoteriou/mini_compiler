package uk.ac.ic.doc.wacc.ast

class AstOptimizer(val program: Program) {
    fun optimize() {
        program.functions.forEach { optimizeStatement(it.block) }
        program.block.statements.forEach(this::optimizeStatement)
    }

    private fun optimizeStatement(statement: Statement) {
        when (statement) {
            is Statement.VariableDeclaration -> {
                statement.rhs = optimizeExpression(statement.rhs)
            }
            is Statement.VariableAssignment -> {
                statement.rhs = optimizeExpression(statement.rhs)
            }
            is Statement.If -> {
                statement.condition = optimizeExpression(statement.condition)
                optimizeStatement(statement.ifThen)
                optimizeStatement(statement.elseThen)
            }
            is Statement.While -> {
                statement.condition = optimizeExpression(statement.condition)
                optimizeStatement(statement.then)
            }
            is Statement.Block -> {
                statement.statements.forEach(this::optimizeStatement)
            }
        }
    }

    private fun optimizeExpression(expression: Expression): Expression {
        when (expression) {
            is Expression.BinaryOperation -> {
                val e1 = optimizeExpression(expression.e1)
                val e2 =optimizeExpression(expression.e2)
                return Expression.BinaryOperation(e1, e2, expression.operator)
            }
            is Expression.UnaryOperation -> {
                val subExpr = expression.expression
                return when (expression.operator) {
                    Expression.UnaryOperator.MINUS -> {
                        when(subExpr) {
                            is Expression.Literal.LInt ->
                                Expression.Literal.LInt(
                                    "-${subExpr.int.replaceFirst(Regex("^0+(?!$)"), "")}"
                                )
                            else -> Expression.UnaryOperation(optimizeExpression(subExpr), expression.operator)
                        }
                    }
                    else -> Expression.UnaryOperation(optimizeExpression(subExpr), expression.operator)
                }
            }
            is Expression.Literal.LInt -> {
                val newInt = expression.int.replaceFirst(Regex("^0+(?!$)"), "")
                return Expression.Literal.LInt(newInt)
            }
            else -> return expression
        }
    }


}
