package uk.ac.ic.doc.wacc.assembly_code

sealed class Instruction {
    data class LABEL(var name: String) : Instruction()
    data class ADD(var rd: String, var rn: String, var operand: String): Instruction()
    data class LDR(var rd: String, var addr_mode2: String): Instruction()
    data class PUSH(var regList : List<String>): Instruction()
    data class POP(var regList : List<String>): Instruction()
    data class MOV(var rd: String, var rn: String): Instruction()
}