package uk.ac.ic.doc.wacc.ast

open class Type {

    object TInt   : Type()
    object TBool  : Type()
    object TChar  : Type()
    object TString: Type()

    data class TArray(var type: Type, var size: Int): Type()
    data class TPair(var t1: Type, var t2: Type): Type()

}