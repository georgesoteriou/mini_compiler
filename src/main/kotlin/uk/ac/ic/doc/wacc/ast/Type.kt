package uk.ac.ic.doc.wacc.ast

import uk.ac.ic.doc.wacc.CodeGenerator.Companion.BYTE
import uk.ac.ic.doc.wacc.CodeGenerator.Companion.WORD

sealed class Type {

    object TAny : Type()
    object TInt : Type()
    object TBool : Type()
    object TChar : Type()
    object TString : Type()

    object TError : Type()
    class TArray(var type: Type) : Type() {
        var size: Int = 0
    }

    data class TPair(var t1: Type, var t2: Type) : Type()
    object TPairSimple : Type()

    data class TFunction(var type: Type, var params: List<Type>) : Type()

    companion object {
        fun compare(t1: Type, t2: Type): Boolean {
            if (t1 is TError || t2 is TError) return false
            if (t1 is TAny || t2 is TAny) return true
            if (t1 is TArray && t1.type is TChar && t2 is TString) return true
            if (t2 is TArray && t2.type is TChar && t1 is TString) return true
            if (t1 is TPair && t2 is TPair) {
                return compare(t1.t1, t2.t1) && compare(t1.t2, t2.t2)
            }
            if (t1 is TArray && t2 is TArray) {
                return compare(t1.type, t2.type)
            }
            if (t1::class == t2::class) return true
            return false
        }

        fun size(t: Type): Int {
            return when (t) {
                is Type.TInt,
                is Type.TString,
                is Type.TArray,
                is Type.TPair,
                is Type.TAny,
                is Type.TFunction -> WORD
                is Type.TBool,
                is Type.TChar -> BYTE
                else -> 0
            }
        }
    }
}