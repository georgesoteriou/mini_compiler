package uk.ac.ic.doc.wacc.ast

open class Type {

    object TAny   : Type()
    object TInt   : Type()
    object TBool  : Type()
    object TChar  : Type()
    object TString: Type()

    object TError: Type()
    class TArray(var type: Type): Type() {
        var size: Int = 0
    }
    data class TPair(var t1: Type, var t2: Type): Type()
    object TPairSimple: Type()

    data class TFunction(var type: Type, var params: List<Type>): Type()

    companion object {
        fun compare(t1: Type, t2: Type): Boolean {
            if (t1 is TError || t2 is TError) return false
            if (t1::class == t2::class) return true
            if (t1 is TAny || t2 is TAny) return true
            if (t1 is TPair && t2 is TPair) {
                return compare(t1.t1, t2.t1) && compare(t1.t2, t2.t2)
            }
            return false
        }
    }

    override fun equals(other: Any?): Boolean {
        if(this is TError || other is TError) return false
        if(this::class == other!!::class) return true
        if(this is TAny || other is TAny) return true
        if(this is TPair && other is TPair) {
            return compare(this.t1, other.t1) && compare(this.t2, other.t2)
        }
        return false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}