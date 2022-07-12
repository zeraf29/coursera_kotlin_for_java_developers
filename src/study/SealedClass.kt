/*
 * Note that from Kotlin 1.6 non-exhaustive when statements will emit a warning and from Kotlin 1.7 they will emit an error. See the issue
 * https://youtrack.jetbrains.com/issue/KT-47709
 */



/*
interface Expr

class Num(val value: Int): Expr
class Sum(val left: Expr, val right: Expr): Expr

fun eval(e: Expr): Int =
    when (e){
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
        // Kotlin Compiler force needing "else" if you use "when" for checking Expr type.
    }
*/

//If you use sealed class, you can limit of subclass definition.
//You have to overlap sealed class's subclass to upper-class.
sealed class Expr {
    class Num(val value: Int): Expr()
    class Sum(val left: Expr, val right: Expr): Expr()
}

fun eval(e: Expr): Int =
    when (e){
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left)
        // Kotlin Compiler force needing "else" if you use "when" for checking Expr type.
    }

fun main() {
    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
}


