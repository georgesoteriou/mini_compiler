package uk.ac.ic.doc.wacc.ast

data class Function(var name: String, var params: List<String>, var block: Statement.Block, var type: Type.TFunction)