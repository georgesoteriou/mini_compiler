package uk.ac.ic.doc.wacc.ast

data class Function(var name: Expression.Identifier, var params: List<Expression>?, var block: Statement, var returnType: Type)