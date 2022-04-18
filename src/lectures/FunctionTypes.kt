package assignments

/**
 * Summary of "Function types" lecture
 */

fun main(args: Array<String>) {

    //paramter: Int x, Int y. return is Int (x+y)
    val sum = { x: Int, y: Int -> x + y }

    //paramters: Int
    //return: Boolean
    //isEven variable's type: (Int) -> Boolean
    //This is same-> val isEven: (Int) -> Boolean = { i: Int -> i % 2 == 0}
    val isEven = { i: Int -> i % 2 == 0 }
    //Lambda is the whole function containing the logic of how to compute the Boolean result from the given Int argument
    //while the result is simply a Boolean value.

    //You can call a variable a function type as a regular function, providing all the unnecessary arguments.

    //To call a Lambda stored in a variable might be convenient if you want to postpone calling a Lambda,
    //store somewhere and call it on the later.
    //When you store a Lambda and a variable, you can pass this variable whenever an expression of function type is expected.
    val list = listOf(1, 2, 3, 4)
    list.any(isEven)    //true
    list.filter(isEven) //[2, 4]



    // () -> Int?
    // This is lambda's return type is nullable

    // (() -> Int)?
    // variable declared lambda is nullable

    //val f1: () -> Int? = null       // compile error: Can't store null in a variable of a non-nullable type.
                                    // To store null, you need to declare a variable of a nullable type as in f3.
    val f2: () -> Int? = { null }   // lambda without arguments that always returns null
    val cond = true
    val f3: (() -> Int)? = if(cond) { { 42 } } else null // either lambda returning Int or null reference
    //val f4: (() -> Int)? = { null } // The compiler expects the Lambda that returns only integer values
                                    // But this tried to assign there a Lambda which returns null.


    //how you should call a variable of a nullable function type
    //(You can't call it as a regular function because it's nullable)

    //1. check the variable explicitly for being not null, then call it
    val f: (() -> Int)? = null
    if(f != null) {
        f()
    }

    //2.safe access syntax
    //you call it variable of function type by calling its invoked function.
    //Each variable or function type can be called by invoke,
    //but irregularly you don't need that since there is a simpler alternative, the syntax to call it directly.
    f?.invoke()


}