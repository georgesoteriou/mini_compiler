package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.ActiveScope

fun errorLog(it: Statement) {
    when (it) {
        is Statement.Block -> println("Error within Block starting at line ${it.location.lineNum}")
        is Statement.While -> println("Error within While starting at line ${it.location.lineNum}")
        is Statement.If -> println("Error within If starting at line ${it.location.lineNum}")
        is Statement.Print -> println("Error in print at line ${it.location.lineNum}")
        is Statement.PrintLn -> println("Error in println at line ${it.location.lineNum}")
        is Statement.Exit -> println("Error in exit at line ${it.location.lineNum}")
        is Statement.Return -> println("Error in return at line ${it.location.lineNum}")
        is Statement.FreeVariable -> println("Error in free at line ${it.location.lineNum}")
        is Statement.ReadInput -> println("Error in read at line ${it.location.lineNum}")
        is Statement.VariableAssignment -> println("Error in assignment at line ${it.location.lineNum}")
        is Statement.VariableDeclaration -> println("Error in declaration at line ${it.location.lineNum}")
        is Statement.Skip -> println("Error in skip at line ${it.location.lineNum}")
    }
}

fun semanticCheck(prog: Program): Boolean {
    var valid = true
    val activeScope = ActiveScope(Scope(), null)



    prog.functions.forEach { f ->
        f.name += Type.toAppend(f.type.returnType)
        f.type.params.forEach {
            f.name += Type.toAppend(it)
        }

    }
    if (!activeScope.addAll(prog.functions.map { Definition(it.name, it.type) })) {
        return false
    }
    prog.functions.forEach { f ->
        val definitions =
            f.params.zip(f.type.params).map { def -> Pair(def.first, Scope.Definition(def.second, true)) }


        f.block.scope.definitions.putAll(definitions)
        f.block.scope.definitions[""] = Scope.Definition(Type.TAny, true)
        valid = valid && checkStatement(f.block, activeScope, f.type.returnType)
    }

    valid = valid && checkStatement(prog.block, activeScope, Type.TError)
    return valid
}


fun checkStatement(statement: Statement, activeScope: ActiveScope, returnType: Type): Boolean {
    return when (statement) {
        is Statement.Block -> {
            val newActiveScope = activeScope.newSubScope(statement.scope)
            var valid = true
            statement.statements.forEach {
                val check = checkStatement(it, newActiveScope, returnType)
                if (!check) {
                    errorLog(it)
                }
                valid = valid && check
            }
            return valid
        }

        is Statement.While ->
            exprType(statement.condition, activeScope) is Type.TBool
                    && checkStatement(statement.then as Statement.Block, activeScope, returnType)

        is Statement.If -> exprType(statement.condition, activeScope) is Type.TBool
                && checkStatement(statement.ifThen as Statement.Block, activeScope, returnType)
                && checkStatement(statement.elseThen as Statement.Block, activeScope, returnType)

        is Statement.PrintLn -> {
            val type = exprType(statement.expression, activeScope)
            type !is Type.TError && type !is Type.TFunction
        }

        is Statement.Print -> {
            val type = exprType(statement.expression, activeScope)
            type !is Type.TError && type !is Type.TFunction
        }
        is Statement.Exit -> exprType(statement.expression, activeScope) is Type.TInt

        is Statement.Return -> {
            if (returnType is Type.TError) {
                false
            } else {
                Type.compare(exprType(statement.expression, activeScope), returnType)
            }
        }

        is Statement.FreeVariable -> {
            when (exprType(statement.expression, activeScope)) {
                is Type.TArray -> true
                is Type.TPair -> true
                is Type.TPairSimple -> true
                is Type.TString -> true
                else -> false
            }
        }

        is Statement.ReadInput -> {
            when (exprType(statement.expression, activeScope)) {
                is Type.TInt -> true
                is Type.TChar -> true
                else -> false
            }
        }

        is Statement.VariableAssignment -> {

            val lhsType = exprType(statement.lhs, activeScope)
            val rhsT = statement.rhs

            if (rhsT is Expression.CallFunction) {
                rhsT.name += Type.toAppend(lhsType)
            }

            val rhsType = exprType(statement.rhs, activeScope)

            val rhs = statement.rhs
            if (rhs is Expression.CallFunction) {
                val funType = activeScope.findType(rhs.name).orElse(Type.TError) as? Type.TFunction ?: return false

                if (funType.params.size != rhs.params.size) {
                    return false
                }
                return Type.compare(lhsType, funType.returnType) &&
                        funType.params.zip(rhs.params).foldRight(true) { type, next ->
                            next && Type.compare(type.first, exprType(type.second, activeScope))
                        }
            } else {
                Type.compare(lhsType, rhsType)
            }
        }

        is Statement.VariableDeclaration -> {
            val lhs = statement.lhs

            val rhsT = statement.rhs
            if (rhsT is Expression.CallFunction) {
                rhsT.name += Type.toAppend(lhs.type)
            }

            val rhsType = exprType(statement.rhs, activeScope)

            val rhs = statement.rhs
            if (rhs is Expression.CallFunction) {
                val funType = activeScope.findType(rhs.name).orElse(Type.TError) as? Type.TFunction ?: return false

                if (funType.params.size != rhs.params.size) {
                    return false
                }
                val funtype = Type.compare(lhs.type, funType.returnType) &&
                        funType.params.zip(rhs.params).foldRight(true) { type, next ->
                            next && Type.compare(type.first, exprType(type.second, activeScope))
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
            return if (Type.compare(lhs.type, rhsType)) {
                if(rhsT !is Expression.Literal.LPair) {
                    lhs.type = rhsType
                }
                activeScope.add(lhs)
                true
            } else {
                false
            }
        }

        is Statement.Skip -> true
    }
}


fun exprType(expr: Expression, activeScope: ActiveScope): Type {

    expr.exprType = when (expr) {
        is Expression.CallFunction -> {
            expr.params.forEach {
                it.exprType = exprType(it, activeScope)
                expr.name += Type.toAppend(it.exprType)
            }

            val type = activeScope.findType(expr.name).orElse(Type.TError)

            when (type) {
                is Type.TFunction -> type.returnType
                else -> Type.TError
            }
        }

        is Expression.Identifier -> activeScope.findType(expr.name).orElse(Type.TError)

        is Expression.Literal.LInt -> Type.TInt
        is Expression.Literal.LBool -> Type.TBool
        is Expression.Literal.LChar -> Type.TChar
        is Expression.Literal.LString -> Type.TString
        is Expression.Literal.LPair -> Type.TPair(Type.TAny, Type.TAny)
        is Expression.Literal.LArray -> {
            val arrayElemType = expr.params.map { exprType(it, activeScope) }
            val type = if (!arrayElemType.isEmpty()) {
                arrayElemType.foldRight(arrayElemType[0]) { type, acc ->
                    if (Type.compare(type, acc)) {
                        type
                    } else {
                        return Type.TError
                    }
                }
            } else {
                Type.TAny
            }
            Type.TArray(type, arrayElemType)
        }
        is Expression.NewPair -> Type.TPair(exprType(expr.e1, activeScope), exprType(expr.e2, activeScope))


        is Expression.BinaryOperation -> {
            val e1Type = exprType(expr.e1, activeScope)
            val e2Type = exprType(expr.e2, activeScope)
            val op = expr.operator
            if (Type.compare(e1Type, e2Type)) {
                when (op) {
                    Expression.BinaryOperator.MULT,
                    Expression.BinaryOperator.DIV,
                    Expression.BinaryOperator.MOD,
                    Expression.BinaryOperator.PLUS,
                    Expression.BinaryOperator.MINUS ->
                        if (e1Type is Type.TInt) {
                            Type.TInt
                        } else {
                            Type.TError
                        }
                    Expression.BinaryOperator.GT,
                    Expression.BinaryOperator.GTE,
                    Expression.BinaryOperator.LT,
                    Expression.BinaryOperator.LTE ->
                        if (e1Type is Type.TInt || e1Type is Type.TChar) {
                            Type.TBool
                        } else {
                            Type.TError
                        }
                    Expression.BinaryOperator.EQ,
                    Expression.BinaryOperator.NOTEQ ->
                        if (e1Type !is Type.TError) {
                            Type.TBool
                        } else {
                            Type.TError
                        }
                    Expression.BinaryOperator.AND,
                    Expression.BinaryOperator.OR ->
                        if (e1Type is Type.TBool) {
                            Type.TBool
                        } else {
                            Type.TError
                        }
                }
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperation -> {
            val eType = exprType(expr.expression, activeScope)
            val op = expr.operator
            when (op) {
                Expression.UnaryOperator.NOT ->
                    if (eType is Type.TBool) {
                        Type.TBool
                    } else {
                        Type.TError
                    }
                Expression.UnaryOperator.MINUS ->
                    if (eType is Type.TInt) {
                        Type.TInt
                    } else {
                        Type.TError
                    }
                Expression.UnaryOperator.LEN ->
                    if (eType is Type.TArray) {
                        Type.TInt
                    } else {
                        Type.TError
                    }
                Expression.UnaryOperator.ORD ->
                    if (eType is Type.TChar) {
                        Type.TInt
                    } else {
                        Type.TError
                    }
                Expression.UnaryOperator.CHR ->
                    if (eType is Type.TInt) {
                        Type.TChar
                    } else {
                        Type.TError
                    }
            }

        }

        is Expression.ArrayElem -> {
            expr.indexes.forEach {
                if (exprType(it, activeScope) !is Type.TInt) {
                    return Type.TError
                }
            }

            var arrType = activeScope.findType(expr.array).orElse(Type.TError)
            expr.indexes.forEach {
                arrType = when (arrType) {
                    is Type.TArray -> {
                        val size = (arrType as Type.TArray).types.size
                        if(it is Expression.Literal.LInt) {
                            if (it.int >= size || it.int < 0) {
                                return Type.TError
                            }
                        }
                        (arrType as Type.TArray).type
                    }
                    is Type.TString -> Type.TChar
                    else -> Type.TError
                }
            }
            arrType
        }

        is Expression.Fst -> {
            val pairType = exprType(expr.expression, activeScope)
            when (pairType) {
                is Type.TPair -> pairType.t1
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }

        is Expression.Snd -> {
            val pairType = exprType(expr.expression, activeScope)
            when (pairType) {
                is Type.TPair -> pairType.t2
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }
    }

    return expr.exprType
}
