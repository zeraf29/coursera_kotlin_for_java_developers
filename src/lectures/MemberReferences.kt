package lectures


data class Person(
    val name: String,
    val age: Int
)


fun main(args: Array<String>) {

    //Like Java, Kotlin has member references, which can replace simple Lambdas that only call a member function
    //or return a member property, it can convert Lambda to member reference automatically when it's possible.


    //you can store Lambda in a variable,
    val isEven: (Int) -> Boolean = { i: Int -> i%2 ==0}
    //however, you can't store a function in a variable.
    fun isEven2(i: Int): Boolean = i%2 == 0
    //val predicate = isEven2   //Compiler error


    //Use function reference instead
    //Function references allow you to store a reference to any defined function
    // in a variable to be able to store it and qualitative it.
    val predicate = ::isEven2
    //Keep in mind that this syntax is just another way to call a function inside the Lambda,
    // underlying implementation are the same
    val predicate2 = { i: Int -> isEven2(i) }






}

