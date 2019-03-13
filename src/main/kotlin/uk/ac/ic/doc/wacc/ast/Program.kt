package uk.ac.ic.doc.wacc.ast

import java.io.File


data class Program( var includes:List<String> , var functions: MutableList<Function>, var block: Statement.Block)

