package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.Scope

data class ActiveScope(var currentScope: Scope, var parentScope: ActiveScope?)