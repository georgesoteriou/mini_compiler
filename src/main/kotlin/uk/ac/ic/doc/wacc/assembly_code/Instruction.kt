package uk.ac.ic.doc.wacc.assembly_code

sealed class Instruction {
    data class Flag(var name: String) : Instruction() {
        override fun toString(): String = name
    }
    data class LABEL(var name: String) : Instruction() {
        override fun toString(): String = "$name:"
    }
    data class ADD(var rd: Operand, var rn: Operand, var operand: Operand): Instruction() {
        override fun toString(): String = "ADD $rd, $rn, $operand"
    }
    data class SUB(var rd: Operand, var rn: Operand, var operand: Operand): Instruction() {
        override fun toString(): String = "SUB $rd, $rn, $operand"
    }
    data class LDR(var rd: Operand, var addr_mode2: Operand): Instruction() {
        override fun toString(): String = "LDR $rd, $addr_mode2"
    }
    data class PUSH(var regList : List<Operand>): Instruction() {
        override fun toString(): String = "PUSH {${regList.joinToString(separator = ", ")}}"
    }
    data class POP(var regList : List<Operand>): Instruction() {
        override fun toString(): String = "POP {${regList.joinToString(separator = ", ")}}"
    }
    data class MOV(var rd: Operand, var rn: Operand): Instruction() {
        override fun toString(): String = "MOV $rd, $rn"
    }
    data class BL(var name: String): Instruction() {
        override fun toString(): String = "BL $name"
    }
    data class STR(var register: Operand, var addrBase: Operand, var addrOffset: Operand): Instruction() {
        override fun toString(): String = "STR $register, [$addrBase, $addrOffset]"
    }
    data class WORD(var length: Int) : Instruction() {
        override fun toString(): String = ".word $length"
    }
    data class ASCII(var contents: String) : Instruction() {
        override fun toString(): String = ".ascii \"$contents\""
    }

}