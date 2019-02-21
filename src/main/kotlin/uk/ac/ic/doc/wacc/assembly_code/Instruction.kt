package uk.ac.ic.doc.wacc.assembly_code

sealed class Instruction {
    data class Flag(var name: String) : Instruction() {
        override fun toString(): String = name
    }

    data class LABEL(var name: String) : Instruction() {
        override fun toString(): String = "$name:"
    }

    data class ADD(var rd: Operand, var rn: Operand, var operand: Operand) : Instruction() {
        override fun toString(): String = "ADD $rd, $rn, $operand"
    }

    data class SUB(var rd: Operand, var rn: Operand, var operand: Operand) : Instruction() {
        override fun toString(): String = "SUB $rd, $rn, $operand"
    }

    data class LDRSimple(var rd: Operand, var value: Operand) : Instruction() {
        override fun toString(): String = "LDR $rd, $value"
    }

    data class LDRRegister(var rd: Operand, var addrBase: Operand, var addrOffset: Operand) : Instruction() {
        override fun toString(): String = "LDR $rd, [$addrBase, $addrOffset]"
    }

    data class LDRCond(var rd: Operand, var value: Operand, var cond: String) : Instruction() {
        override fun toString(): String = "LDR$cond $rd, $value"
    }

    data class PUSH(var regList: List<Operand>) : Instruction() {
        override fun toString(): String = "PUSH {${regList.joinToString(separator = ", ")}}"
    }

    data class POP(var regList: List<Operand>) : Instruction() {
        override fun toString(): String = "POP {${regList.joinToString(separator = ", ")}}"
    }

    data class MOV(var rd: Operand, var rn: Operand) : Instruction() {
        override fun toString(): String = "MOV $rd, $rn"
    }

    data class BL(var name: String) : Instruction() {
        override fun toString(): String = "BL $name"
    }

    data class STRSimple(var register: Operand, var addr: Operand) : Instruction() {
        override fun toString(): String = "STR $register, [$addr]"
    }

    data class STROffset(var register: Operand, var addrBase: Operand, var addrOffset: Operand.Offset) : Instruction() {
        override fun toString(): String {
            return if (addrOffset.value != 0) {
                "STR $register, [$addrBase, $addrOffset]"
            } else {
                "STR $register, [$addrBase]"
            }
        }
    }

    data class STRBSimple(var register: Operand, var addrBase: Operand) : Instruction() {
        override fun toString(): String = "STRB $register, [$addrBase]"
    }

    data class STRBOffset(var register: Operand, var addrBase: Operand, var addrOffset: Operand) : Instruction() {
        override fun toString(): String = "STRB $register, [$addrBase, $addrOffset]"
    }

    data class WORD(var length: Int) : Instruction() {
        override fun toString(): String = ".word $length"
    }

    data class ASCII(var contents: String) : Instruction() {
        override fun toString(): String = ".ascii \"$contents\""
    }
    data class CMP (var rn: Operand, var op2: Operand) : Instruction() {
        override fun toString(): String = "CMP $rn, $op2"
    }

}
