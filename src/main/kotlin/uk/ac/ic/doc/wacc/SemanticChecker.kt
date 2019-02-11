package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.visitors.ActiveScope
import kotlin.system.exitProcess

fun semanticCheck (prog: Program): Boolean {
    var valid = true
    if(!checkFunctions(prog.functions))
    {
        return false
    }
    prog.functions.forEach{
        val scope = (it.block as Statement.Block).scope
        if(it.params != null) {
            it.params!!.forEach { i ->
                scope.variables.add(i)
            }
        }
        valid = valid && checkStatements(it.block as Statement.Block, ActiveScope(Scope(),null),it.returnType, prog.functions)
    }

    valid = valid && checkStatements(prog.block as Statement.Block, ActiveScope(Scope(),null),Type.TError, prog.functions)
    return valid
}

fun checkFunctions(functions: List<Function>): Boolean {
    val functionNameMap :HashMap<String, Int> = HashMap()
    functions.forEach { f ->
        var count = functionNameMap[f.name.name]

        if ( count == null )
        {
            count = 1
        } else {
            count += 1
        }

        functionNameMap[f.name.name] = count
    }

    functionNameMap.forEach{
        if (it.value > 1 ) {
            return false
        }

    }
    return true
}


fun checkStatements(block: Statement.Block, activeScope: ActiveScope, returnType: Type, functions: List<Function>): Boolean {
    val newActiveScope = ActiveScope(block.scope, activeScope)
    var valid = true
    block.statements.forEach{

        val check = checkStatement(it, newActiveScope, returnType, functions)
        if (!check)
        {
           // println(it.location)
        }
        valid = valid && check
    }
    return valid
}

fun exprType(expr: Expression, activeScope: ActiveScope, functions: List<Function> ) : Type {

    return when (expr) {
        is Expression.Variable -> expr.type

        is Expression.CallFunction -> {
            functions.forEach{
                if (it.name == expr.name)
                {
                    return it.returnType
                }
            }
            Type.TError
        }

        is Expression.ExpressionPair -> Type.TPair(exprType(expr.e1,activeScope,functions), exprType(expr.e2,activeScope,functions))

        /*
            if something is an identifier, IT HAS TO EXIST in the scope so check it here
    else error
         */
        is Expression.Identifier -> activeScope.findType(expr)

        is Expression.Literal.LInt -> Type.TInt
        is Expression.Literal.LBool -> Type.TBool
        is Expression.Literal.LChar -> Type.TChar
        is Expression.Literal.LString-> Type.TString
        is Expression.Literal.LPair -> Type.TPairSimple

        is Expression.BinaryOperator.BMult -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BDiv -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BMod -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BPlus -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BMinus -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class && e1Type is Type.TInt)
            {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BGT -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BGTE -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BLT -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BLTE -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                ( e1Type is Type.TInt || e1Type is Type.TChar))
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BEQ -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
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
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                e1Type !is Type.TError )
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BAnd -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                e1Type is Type.TBool)
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.BinaryOperator.BOr -> {
            val e1Type = exprType(expr.e1,activeScope,functions)
            val e2Type = exprType(expr.e2,activeScope,functions)

            if (e1Type::class == e2Type::class &&
                e1Type is Type.TBool)
            {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UNot -> {
            val eType = exprType(expr.expression,activeScope,functions)

            if (eType is Type.TBool) {
                Type.TBool
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UMinus -> {
            val eType = exprType(expr.expression,activeScope,functions)

            if (eType is Type.TInt) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.ULen -> {
            val eType = exprType(expr.expression,activeScope,functions)

            if (eType is Type.TArray) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UOrd -> {
            val eType = exprType(expr.expression,activeScope,functions)

            if (eType is Type.TChar) {
                Type.TInt
            } else {
                Type.TError
            }
        }

        is Expression.UnaryOperator.UChr -> {
            val eType = exprType(expr.expression,activeScope,functions)

            if (eType is Type.TInt) {
                Type.TChar
            } else {
                Type.TError
            }
        }

        is Expression.ArrayElem -> {
            (expr.indexes as Expression.ExpressionList).expressions.forEach {
                if(exprType(it, activeScope, functions) !is Type.TInt){
                    Type.TError
                }
            }

            var arrType = activeScope.findType(expr.array as Expression.Identifier)
            (expr.indexes as Expression.ExpressionList).expressions.forEach {
                when (arrType) {
                    is Type.TArray -> arrType = (arrType as Type.TArray).type
                    else -> arrType = Type.TError
                }
            }
            arrType
        }

        is Expression.Fst -> {
            val pairType = activeScope.findType(expr.expression as Expression.Identifier)
            return when (pairType) {
                is Type.TPair -> pairType.t1
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }

        is Expression.Snd -> {
            val pairType = activeScope.findType(expr.expression as Expression.Identifier)
            return when (pairType) {
                is Type.TPair -> pairType.t2
                is Type.TPairSimple -> Type.TPairSimple
                else -> Type.TError
            }
        }
        else -> Type.TError
    }
}

fun checkStatement(param: Statement, activeScope: ActiveScope, returnType:Type, functions : List<Function>): Boolean {
    return when (param) {
        is Statement.Block -> checkStatements(param, activeScope,returnType, functions)

        is Statement.While -> exprType(param.condition, activeScope, functions) is Type.TBool
                && checkStatements(param.then as Statement.Block, activeScope,returnType, functions)

        is Statement.If -> exprType(param.condition, activeScope, functions) is Type.TBool
                && checkStatements(param.ifThen as Statement.Block, activeScope,returnType, functions)
                && checkStatements(param.elseThen as Statement.Block, activeScope,returnType, functions)


        is Statement.PrintLn -> exprType(param.expression,activeScope, functions) !is Type.TError



        is Statement.Print -> exprType(param.expression,activeScope, functions) !is Type.TError

        is Statement.Exit -> exprType(param.expression,activeScope, functions) is Type.TInt


        is Statement.Return -> {
            if (returnType is Type.TError) {
               false
            } else {
                when (returnType) {
                    is Type.TInt-> exprType(param.expression,activeScope, functions) is Type.TInt
                    is Type.TBool-> exprType(param.expression,activeScope, functions) is Type.TBool
                    is Type.TChar-> exprType(param.expression,activeScope, functions) is Type.TChar
                    is Type.TString-> exprType(param.expression,activeScope, functions) is Type.TString
                    is Type.TArray-> exprType(param.expression,activeScope, functions) is Type.TArray
                    is Type.TPair -> exprType(param.expression,activeScope, functions) is Type.TPair
                    is Type.TPairSimple -> exprType(param.expression,activeScope, functions) is Type.TPairSimple
                    else -> false
                }

            }
        }


        is Statement.FreeVariable -> {
            when (exprType(param.expression, activeScope, functions)) {
                is Type.TArray -> true
                is Type.TPair -> true
                is Type.TPairSimple -> true
                is Type.TString -> true
                else -> false
            }
        }

        is Statement.ReadInput -> {
            when (exprType(param.expression, activeScope, functions)) {
                is Type.TInt -> true
                is Type.TChar -> true
                else -> false
            }
        }
        /*
            nothing to check for semantically here, any errors will be a runtime error and not a
            semantic error
         */

        is Statement.VariableAssignment -> {

            val lhsType = exprType(param.lhs,activeScope, functions)
            val rhsType = exprType(param.rhs,activeScope, functions)

            var funcValid = true
            when(param.rhs) {
                is Expression.CallFunction ->
                    functions.forEach { f ->
                        if (f.name == (param.rhs as Expression.CallFunction).name) {
                            var count = 0
                            if((param.rhs as Expression.CallFunction).params != null && f.params != null) {
                                if(((param.rhs as Expression.CallFunction).params as Expression.ExpressionList).expressions.size == f.params!!.size) {
                                    ((param.rhs as Expression.CallFunction).params
                                            as Expression.ExpressionList).expressions.forEach {
                                        funcValid =
                                            funcValid && exprType(f.params!![count], activeScope, functions)::class ==
                                                    exprType(it, activeScope, functions)::class
                                        count++
                                    }
                                } else {
                                    funcValid = false
                                }
                            }
                        }
                    }
            }

            funcValid && (lhsType::class == rhsType::class ||
                    (lhsType is Type.TPair && rhsType is Type.TPairSimple) ||
                    (rhsType is Type.TPair && lhsType is Type.TPairSimple))
        }

        is Statement.VariableDeclaration -> {
            val lhs = param.lhs as Expression.Variable
            val rhsType = exprType(param.rhs, activeScope, functions)

            var funcValid = true
            when(param.rhs) {
                is Expression.CallFunction ->
                    functions.forEach { f ->
                        if (f.name == (param.rhs as Expression.CallFunction).name) {
                            var count = 0
                            if((param.rhs as Expression.CallFunction).params != null && f.params != null) {
                                if(((param.rhs as Expression.CallFunction).params as Expression.ExpressionList).expressions.size == f.params!!.size) {
                                    ((param.rhs as Expression.CallFunction).params
                                            as Expression.ExpressionList).expressions.forEach {
                                        funcValid =
                                            funcValid && exprType(f.params!![count], activeScope, functions)::class ==
                                                    exprType(it, activeScope, functions)::class
                                        count++
                                    }
                                } else {
                                    funcValid = false
                                }
                            }
                        }
                    }
            }

            if (funcValid && !activeScope.isVarInCurrScope(lhs.name))
            {
                activeScope.currentScope.variables.add(lhs)
                lhs.type::class == rhsType::class || (lhs.type is Type.TPair && rhsType is Type.TPairSimple)
            } else {
                false
            }

        }



        else -> false
    }
}
