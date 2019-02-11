package uk.ac.ic.doc.wacc.ast

data class Function(var name: Expression.Identifier, var params: Expression?, var block: Statement, var returnType: Type)