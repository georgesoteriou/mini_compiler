package uk.ac.ic.doc.wacc.ast

open class Type {

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
}