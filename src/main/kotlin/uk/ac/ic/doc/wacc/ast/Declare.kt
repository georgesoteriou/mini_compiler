package uk.ac.ic.doc.wacc.ast

class Declare(parent: Node?, scope: Scope?, type: Type) : Node(parent, scope) {
    fun lhs(ident: Ident) { this.children[0] = ident }
    fun rhs(expr: Expr) { this.children[1] = expr }
    fun lhs(): Ident { return this.children[0] as Ident }
    fun rhs(): Expr { return this.children[1] as Expr}
}