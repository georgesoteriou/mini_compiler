package uk.ac.ic.doc.wacc.ast

abstract class Expr(parent: Node?, scope: Scope?) : Node(parent, scope)

class IntLit(parent: Node?, Scope: Scope?, int: Int): Expr(parent, Scope)

class StringLit(parent: Node?, Scope: Scope?, string:String): Expr(parent, Scope)

class CharLit(parent: Node?, Scope: Scope?, char: Char): Expr(parent, Scope)

class BoolLit(parent: Node?, Scope: Scope?, bool: Boolean): Expr(parent, Scope)

class PairLit(parent: Node?, Scope: Scope?): Expr(parent, Scope){
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Ident(parent: Node?, Scope: Scope?, name: String): Expr(parent, Scope)

class ArrayLit(parent: Node?, Scope: Scope?): Expr(parent, Scope)

class Plus(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Minus(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Mult(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Div(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Mod(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Eq(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Neq(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class And(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Or(parent: Node?, Scope: Scope?): Expr(parent, Scope) {
    fun e1(): Expr { return this.children[0] as Expr }
    fun e1(expr: Expr) {  this.children[0] = expr }
    fun e2(): Expr { return this.children[1] as Expr }
    fun e2(expr: Expr) {  this.children[1] = expr }
}

class Not(parent: Node?, Scope: Scope?): Expr(parent, Scope)

class Neg(parent: Node?, Scope: Scope?): Expr(parent, Scope)

class Len(parent: Node?, Scope: Scope?): Expr(parent, Scope)

class Ord(parent: Node?, Scope: Scope?): Expr(parent, Scope)

class Chr(parent: Node?, Scope: Scope?): Expr(parent, Scope)