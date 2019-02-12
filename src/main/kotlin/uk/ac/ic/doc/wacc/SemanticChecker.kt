package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.visitors.ActiveScope

fun semanticCheck (prog: Program): Boolean {
    var valid = true
    val activeScope = ActiveScope(Scope(), null)

    activeScope.addAll(prog.functions.map { Definition(it.name, it.type) })
    
    prog.functions.forEach { f ->
        val definitions = f.params.zip(f.type.params).map { def -> Pair(def.first, def.second) }
        f.block.scope.definitions.putAll(definitions)
        valid = valid && checkStatement(f.block, activeScope, f.type)
    }

    valid = valid && checkStatement(prog.block as Statement.Block, activeScope,Type.TError)
    return valid
}


fun checkStatement(param: Statement, activeScope: ActiveScope, returnType:Type): Boolean {
    return when (param) {
        is Statement.Block ->  {
            val newActiveScope = activeScope.newSubScope(param.scope)
            var valid = true
            param.statements.forEach{
                val check = checkStatement(it, newActiveScope, returnType)
                if (!check)
                {
                    println(it.location)
                }
                valid = valid && check
            }
            return valid
        }

        is Statement.While ->
            exprType(param.condition, activeScope) is Type.TBool
                && checkStatement(param.then as Statement.Block, activeScope,returnType)

        is Statement.If -> exprType(param.condition, activeScope) is Type.TBool
                && checkStatement(param.ifThen as Statement.Block, activeScope,returnType)
                && checkStatement(param.elseThen as Statement.Block, activeScope,returnType)


        is Statement.PrintLn -> exprType(param.expression,activeScope) !is Type.TError

        is Statement.Print -> exprType(param.expression,activeScope) !is Type.TError

        is Statement.Exit -> exprType(param.expression,activeScope) is Type.TInt

        is Statement.Return -> {
            if (returnType is Type.TError) {
                false
            } else {
                exprType(param.expression,activeScope) == returnType
            }
        }

        is Statement.FreeVariable -> {
            when (exprType(param.expression, activeScope)) {
                is Type.TArray -> true
                is Type.TPair -> true
                is Type.TPairSimple -> true
                is Type.TString -> true
                else -> false
            }
        }

        is Statement.ReadInput -> {
            when (exprType(param.expression, activeScope)) {
                is Type.TInt -> true
                is Type.TChar -> true
                else -> false
            }
        }

        is Statement.VariableAssignment -> {

            val lhsType = exprType(param.lhs,activeScope)
            val rhsType = exprType(param.rhs,activeScope)

            val rhs = param.rhs
            if(rhs is Expression.CallFunction) {
                val funType = activeScope.findDef(rhs.name).orElse(Type.TError) as? Type.TFunction ?: return false

                if(funType.params.size != rhs.params.size) {
                    return false
                }
                return lhsType == funType.type &&
                        funType.params.zip(rhs.params).foldRight(true) { type, next ->
                            next && type.first == exprType(type.second, activeScope)
                        }
            } else {
                Type.compare(lhsType, rhsType)
            }
        }

        is Statement.VariableDeclaration -> {
            val lhs = param.lhs
            val rhsType = exprType(param.rhs, activeScope)

            val rhs = param.rhs
            if(rhs is Expression.CallFunction) {
                val funType = activeScope.findDef(rhs.name).orElse(Type.TError) as? Type.TFunction ?: return false

                if(funType.params.size != rhs.params.size) {
                    return false
                }
                val funtype = lhs.type == funType.type &&
                        funType.params.zip(rhs.params).foldRight(true) { type, next ->
                            next && type.first == exprType(type.second, activeScope)
                        }

                if (!funtype || activeScope.isVarInCurrScope(lhs.name)) {
                    return false
                }
                activeScope.add(lhs)
                return true
            }

            if (activeScope.isVarInCurrScope(lhs.name)) {
                return false
            }
            return if(lhs.type == rhsType) {
                lhs.type = rhsType
                activeScope.add(lhs)
                true
            } else {
                false
            }
        }

        is Statement.Skip -> true
    }
}


fun exprType(expr: Expression, activeScope: ActiveScope) : Type {

    return when (expr) {
        is Expression.CallFunction -> activeScope.findDef(expr.name).orElse(Type.TError)

        is Expression.Identifier -> activeScope.findDef(expr.name).orElse(Type.TError)

        is Expression.Literal.LInt -> Type.TInt
        is Expression.Literal.LBool -> Type.TBool
        is Expression.Literal.LChar -> Type.TChar
        is Expression.Literal.LString-> Type.TString
        is Expression.Literal.LPair -> Type.TPair(Type.TAny, Type.TAny)
        is Expression.NewPair -> Type.TPair(exprType(expr.e1,activeScope), exprType(expr.e2,activeScope))


        is Expression.BinaryOperator.BMult -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BDiv -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BMod -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BPlus -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BMinus -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BGT -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BGTE -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BLT -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BLTE -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BEQ -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                 e1Type !is Type.TError)
            {
                Type.TBool
            } else if ( (e1Type is Type.TPair && e2Type is Type.TPairSimple) ||
                (e1Type is Type.TPairSimple && e2Type is Type.TPair)) {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BNotEQ -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)


            return if (Type.compare(e1Type, e2Type) &&
                e1Type !is Type.TError )
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BAnd -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                e1Type is Type.TBool)
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BOr -> {
            val e1Type = exprType(expr.e1,activeScope)
            val e2Type = exprType(expr.e2,activeScope)

            return if (e1Type == e2Type &&
                e1Type is Type.TBool)
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UNot -> {
            val eType = exprType(expr.expression,activeScope)

            return if (eType is Type.TBool) {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UMinus -> {
            val eType = exprType(expr.expression,activeScope)

            return if (eType is Type.TInt) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.ULen -> {
            val eType = exprType(expr.expression,activeScope)

            return if (eType is Type.TArray) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UOrd -> {
            val eType = exprType(expr.expression,activeScope)

            return if (eType is Type.TChar) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UChr -> {
            val eType = exprType(expr.expression,activeScope)

            return if (eType is Type.TInt) {
                Type.TChar
            } else {
                Type.TError
            }
        }

        is Expression.ArrayElem -> {
            expr.indexes.forEach {
                if(exprType(it, activeScope) !is Type.TInt){
                    return Type.TError
                }
            }

            var arrType = activeScope.findDef(expr.array).orElse(Type.TError)
            repeat(expr.indexes.size) {
                when (arrType) {
                    is Type.TArray -> arrType = (arrType as Type.TArray).type
                    else -> arrType = Type.TError
                }
            }
            return arrType
        }

        is Expression.Fst -> {
            val pairType = exprType(expr.expression, activeScope)
            return when (pairType) {
                is Type.TPair -> pairType.t1
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }

        is Expression.Snd -> {
            val pairType = exprType(expr.expression, activeScope)
            return when (pairType) {
                is Type.TPair -> pairType.t2
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }

        else -> return Type.TError
    }
}
