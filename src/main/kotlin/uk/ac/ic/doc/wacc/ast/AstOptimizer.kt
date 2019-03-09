package uk.ac.ic.doc.wacc.ast

class AstOptimizer(val program: Program) {
    fun optimize() {
        program.functions.forEach { optimizeStatement(it.block) }
        program.block.statements.map(this::optimizeStatement)
    }

    private fun optimizeStatement(statement: Statement): Statement {
        when (statement) {
            is Statement.VariableDeclaration -> {
                statement.rhs = optimizeExpression(statement.rhs)
            }
            is Statement.VariableAssignment -> {
                statement.rhs = optimizeExpression(statement.rhs)
            }
            is Statement.If -> {
                statement.condition = optimizeExpression(statement.condition)
                statement.ifThen = optimizeStatement(statement.ifThen)
                statement.elseThen = optimizeStatement(statement.elseThen)
            }
            is Statement.While -> {
                statement.condition = optimizeExpression(statement.condition)
                statement.then = optimizeStatement(statement.then)
            }
            is Statement.Block -> {
                statement.statements.map(this::optimizeStatement)
            }
        }
        return statement
    }

    private fun optimizeExpression(expression: Expression): Expression {
        when (expression) {
            is Expression.BinaryOperation -> {
                val e1 = optimizeExpression(expression.e1)
                val e2 = optimizeExpression(expression.e2)
                if(e1 is Expression.Literal.LInt && e2 is Expression.Literal.LInt) {
                    val operator = expression.operator
                    val result :Int = when(operator){
                        Expression.BinaryOperator.MULT  -> e1.int * e2.int
                        Expression.BinaryOperator.DIV   -> e1.int / e2.int
                        Expression.BinaryOperator.MOD   -> e1.int % e2.int
                        Expression.BinaryOperator.PLUS  -> e1.int + e2.int
                        Expression.BinaryOperator.MINUS -> e1.int - e2.int
                        else -> 0
                    }
                    return Expression.Literal.LInt(result)
                }
                if(e1 is Expression.Literal.LBool && e2 is Expression.Literal.LBool) {
                    val operator = expression.operator
                    val result: Boolean = when (operator) {
                        Expression.BinaryOperator.AND -> e1.bool && e2.bool
                        Expression.BinaryOperator.OR -> e1.bool || e2.bool
                        else -> false
                    }
                    return Expression.Literal.LBool(result)
                }
                return Expression.BinaryOperation(e1, e2, expression.operator)
            }
            else -> return expression
        }
    }


}
