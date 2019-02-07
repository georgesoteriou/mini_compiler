package uk.ac.ic.doc.wacc.ast_old

abstract class Expr(parent: Node?, scope: Scope?) : Node(parent, scope)

class IntLit(parent: Node?, scope: Scope?, int: Int): Expr(parent, scope)

class StringLit(parent: Node?, scope: Scope?, string:String): Expr(parent, scope)

class CharLit(parent: Node?, scope: Scope?, char: Char): Expr(parent, scope)

class BoolLit(parent: Node?, scope: Scope?, bool: Boolean): Expr(parent, scope)

class PairLit(parent: Node?, scope: Scope?): Expr(parent, scope) {
    fun e1(): Expr { return this.children[0] as Expr
    }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr
    }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class ArrayLit(parent: Node?, scope: Scope?): Expr(parent, scope)

class Ident(parent: Node?, scope: Scope?, name: String): Expr(parent, scope)

class BinOp(parent: Node?, scope: Scope?, operation: BinOps): Expr(parent, scope)  {
    fun e1(): Expr { return this.children[0] as Expr
    }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr
    }
    fun e2(expr: Expr) {  this.children[1] = expr }
}


class UnOp(parent: Node?, scope: Scope?, operation: UnOps): Expr(parent, scope)
