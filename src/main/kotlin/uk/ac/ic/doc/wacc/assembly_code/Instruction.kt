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

    data class ADDCond(var rd: Operand, var rn: Operand, var operand: Operand, var cond: String) : Instruction() {
        override fun toString(): String = "ADD $rd, $rn, $operand, $cond"
    }

    data class ADDS(var rd: Operand.Register, var r1: Operand.Register, var r2: Operand.Register) : Instruction() {
        override fun toString(): String = "ADDS $rd, $r1, $r2"
    }

    data class SUB(var rd: Operand, var rn: Operand, var operand: Operand) : Instruction() {
        override fun toString(): String = "SUB $rd, $rn, $operand"
    }

    data class SUBS(var rd: Operand.Register, var r1: Operand.Register, var r2: Operand.Register) : Instruction() {
        override fun toString(): String = "SUBS $rd, $r1, $r2"
    }

    data class RSBS(var rd: Operand.Register, var r1: Operand.Register, var operand: Operand) : Instruction() {
        override fun toString(): String = "RSBS $rd, $r1, $operand"
    }

    data class SMULL(
        var rdLo: Operand.Register,
        var rdHi: Operand.Register,
        var rm: Operand.Register,
        var rs: Operand.Register
    ) : Instruction() {
        override fun toString(): String = "SMULL $rdLo, $rdHi, $rm, $rs"
    }

    data class LDRSimple(var rd: Operand, var value: Operand) : Instruction() {
        override fun toString(): String = "LDR $rd, $value"
    }

    data class LDRRegCond(var rd: Operand, var addrBase: Operand, var addrOffset: Operand.Offset, var cond: String) :
        Instruction() {
        override fun toString(): String {
            return if (addrOffset.value != 0) {
                "LDR$cond $rd, [$addrBase, $addrOffset]"
            } else {
                "LDR$cond $rd, [$addrBase]"
            }
        }
    }

    data class LDRRegister(var rd: Operand, var addrBase: Operand, var addrOffset: Operand.Offset) : Instruction() {
        override fun toString(): String {
            return if (addrOffset.value != 0) {
                "LDR $rd, [$addrBase, $addrOffset]"
            } else {
                "LDR $rd, [$addrBase]"
            }
        }
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

    data class MOVCond(var rd: Operand, var rn: Operand, var cond: String) : Instruction() {
        override fun toString(): String = "MOV$cond $rd, $rn"
    }

    data class BCond(var name: String, var cond: String = "") : Instruction() {
        override fun toString(): String = "B$cond $name"
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
                if (addrOffset.value < 0) {
                    "STR $register, [$addrBase, $addrOffset]!"
                } else {
                    "STR $register, [$addrBase, $addrOffset]"
                }
            } else {
                "STR $register, [$addrBase]"
            }
        }
    }

    data class STRBOffset(var register: Operand, var addrBase: Operand, var addrOffset: Operand.Offset) :
        Instruction() {
        override fun toString(): String {
            return if (addrOffset.value != 0) {
                if (addrOffset.value < 0) {
                    "STRB $register, [$addrBase, $addrOffset]!"
                } else {
                    "STRB $register, [$addrBase, $addrOffset]"
                }
            } else {
                "STRB $register, [$addrBase]"
            }
        }
    }

    data class WORD(var length: Int) : Instruction() {
        override fun toString(): String = ".word $length"
    }

    data class ASCII(var contents: String) : Instruction() {
        override fun toString(): String = ".ascii \"$contents\""
    }

    data class CMP(var rn: Operand, var op2: Operand) : Instruction() {
        override fun toString(): String = "CMP $rn, $op2"
    }

    data class CMPCond(var rn: Operand, var op2: Operand, var cond: String) : Instruction() {
        override fun toString(): String = "CMP $rn, $op2, $cond"
    }

    data class AND(var rd: Operand.Register, var r1: Operand.Register, var r2: Operand.Register) : Instruction() {
        override fun toString(): String = "AND $rd, $r1, $r2"
    }

    data class ORR(var rd: Operand.Register, var r1: Operand.Register, var r2: Operand.Register) : Instruction() {
        override fun toString(): String = "ORR $rd, $r1, $r2"
    }

    data class EOR(var rd: Operand.Register, var rn: Operand.Register, var op: Operand.Constant) : Instruction() {
        override fun toString(): String = "EOR $rd, $rn, $op"
    }

}
