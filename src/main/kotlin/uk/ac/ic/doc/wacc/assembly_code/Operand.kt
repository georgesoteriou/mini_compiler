package uk.ac.ic.doc.wacc.assembly_code

sealed class Operand {

    data class Register(var number:Int): Operand(){
        override fun toString(): String = "r$number"
    }
    data class Literal(var value:Int): Operand() {
        override fun toString(): String = "=$value"
    }
    object Lr: Operand() {
        override fun toString(): String = "lr"
    }
    object Pc: Operand() {
        override fun toString(): String = "pc"
    }
}