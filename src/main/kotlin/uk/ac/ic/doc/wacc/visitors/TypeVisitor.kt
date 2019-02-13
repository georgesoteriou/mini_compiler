package uk.ac.ic.doc.wacc.visitors

import com.sun.jdi.InvalidTypeException
import uk.ac.ic.doc.wacc.ast.Type
import uk.ac.ic.doc.wacc.grammar.WaccParser
import uk.ac.ic.doc.wacc.grammar.WaccParserBaseVisitor

class TypeVisitor : WaccParserBaseVisitor<Type>() {
    override fun visitInt(ctx: WaccParser.IntContext): Type = Type.TInt

    override fun visitChar(ctx: WaccParser.CharContext): Type = Type.TChar

    override fun visitBool(ctx: WaccParser.BoolContext): Type = Type.TBool

    override fun visitString(ctx: WaccParser.StringContext): Type = Type.TString

    override fun visitArray_type(ctx: WaccParser.Array_typeContext): Type = when {
        ctx.array_type() != null -> Type.TArray(ctx.array_type().accept(this))
        ctx.base_type() != null -> Type.TArray(ctx.base_type().accept(this))
        ctx.pair_type() != null -> Type.TArray(ctx.pair_type().accept(this))
        else -> throw InvalidTypeException("Array type does not exist")
    }

    override fun visitPair_type(ctx: WaccParser.Pair_typeContext): Type {
        val t1 = ctx.pair_elem_type(0).accept(this)
        val t2 = ctx.pair_elem_type(1).accept(this)
        return Type.TPair(t1, t2)
    }

    override fun visitPair_elem_type(ctx: WaccParser.Pair_elem_typeContext): Type {
        return if (ctx.PAIR() == null) {
            super.visitPair_elem_type(ctx)
        } else {
            Type.TPair(Type.TAny, Type.TAny)
        }
    }

}