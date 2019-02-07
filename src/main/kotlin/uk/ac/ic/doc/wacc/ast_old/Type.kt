package uk.ac.ic.doc.wacc.ast_old

import java.lang.IllegalArgumentException

enum class Type {
    INT, BOOL, CHAR, STRING;

    companion object {
        fun getType(t: String): Type {
            return when (t) {
                "int" -> INT
                "string" -> STRING
                "char" -> CHAR
                "bool" -> BOOL
                else -> throw IllegalArgumentException("Invalid type!")
            }
        }
    }
}