package uk.ac.ic.doc.wacc.ast_old

import uk.ac.ic.doc.wacc.grammar.WaccParser

class Function(parent: Node?, scope: Scope?, name: String, returnType: Type) : Scope(parent, scope) {
    var parameters: ArrayList<Type> = ArrayList()
    var defined: Boolean = false
    
    companion object {
        fun funcFromCTX(ctx: WaccParser.FuncContext?, root: Root) : Node {
            val funcScope = Scope(root, root.scope)
            val t = Type.getType(ctx!!.type().toString())
            val currNode = Function(root, root.scope, ctx.IDENT().toString(), t)
            currNode.children.add(funcScope)
            root.functions.add(currNode)
            root.functions.last().defined = true
            return currNode
        }
    }
}