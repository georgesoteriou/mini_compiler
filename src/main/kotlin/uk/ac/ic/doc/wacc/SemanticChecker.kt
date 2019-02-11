package uk.ac.ic.doc.wacc

import uk.ac.ic.doc.wacc.ast.*
import uk.ac.ic.doc.wacc.ast.Function
import uk.ac.ic.doc.wacc.visitors.ActiveScope

fun semanticCheck (prog: Program) {
    var valid = true
    prog.functions.forEach{
        val scope = (it.block as Statement.Block).scope
        (it.params as Expression.ExpressionList).expressions.forEach{
            scope.variables.add(it)
        }
        valid = valid && checkStatements(it.block as Statement.Block, ActiveScope(Scope(),null),it.returnType, prog.functions)
    }

    // TODO to go over the main block of the program

}


fun checkStatements(block: Statement.Block, activeScope: ActiveScope, returnType: Type, functions: List<Function>): Boolean {
    val newActiveScope = ActiveScope(block.scope, activeScope)
    var valid = true
    block.statements.forEach{
        valid = valid && checkStatement(it, newActiveScope, returnType, functions)
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
                    it.returnType
                }
            }
            Type.TError
        }

        is Expression.ExpressionPair -> Type.TPair(exprType(expr.e1,activeScope,functions), exprType(expr.e2,activeScope,functions))

        else -> Type.TError
    }
        /* FOR PRINT:
            evaluate expression recursively to see if:
                expression has a uniform type
                if no then problem
                if yes then check if type is printable

         */

    /*      FOR RETURN
            evaluate expression recursively to see if uniform type
            if uniform type then see if returnable
            if not uniform then problem

         */

    /*
        REALLY FANCY AND USEFUL
    var lhsType = activeScope.findType((param.lhs as Expression.Identifier))

    if something is an identifier, IT HAS TO EXIST in the scope so check it here
    else error

     */
}

fun checkStatement(param: Statement, activeScope: ActiveScope, returnType:Type, functions : List<Function>): Boolean {
    return when (param) {
        is Statement.Block -> checkStatements(param, activeScope,returnType, functions)

        is Statement.While -> exprType(param.condition, activeScope, functions) !is Type.TBool
                && checkStatements(param.then as Statement.Block, activeScope,returnType, functions)

        is Statement.If -> exprType(param.condition, activeScope, functions) !is Type.TBool
                && checkStatements(param.ifThen as Statement.Block, activeScope,returnType, functions)
                && checkStatements(param.elseThen as Statement.Block, activeScope,returnType, functions)


        is Statement.PrintLn -> exprType(param.expression,activeScope, functions) !is Type.TError



        is Statement.Print -> exprType(param.expression,activeScope, functions) !is Type.TError

        is Statement.Exit -> exprType(param.expression,activeScope, functions) !is Type.TInt


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

        is Statement.ReadInput -> true
        /*
            nothing to check for semantically here, any errors will be a runtime error and not a
            semantic error
         */

        is Statement.VariableAssignment -> {

            val lhsType = exprType(param.lhs,activeScope, functions)
            val rhsType = exprType(param.rhs,activeScope, functions)
            lhsType::class == rhsType::class
        }

        is Statement.VariableDeclaration -> {
            val lhs = param.lhs as Expression.Variable
            val rhsType = exprType(param.rhs, activeScope, functions)

            if (!activeScope.isVarInCurrScope(lhs.name))
            {
                activeScope.currentScope.variables.add(lhs)
                lhs.type::class == rhsType::class
            } else {
                false
            }

        }



        else -> false
    }
}
