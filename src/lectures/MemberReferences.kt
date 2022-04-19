package lectures


data class Person(
    val name: String,
    val age: Int
)


fun main(args: Array<String>) {

    //Like Java, Kotlin has member references, which can replace simple Lambdas that only call a member function
    //or return a member property, it can convert Lambda to member reference automatically when it's possible.

    //These ways are same results. you can choose them flexibly.
    // people.maxBy{ it.age }
    // people.maxBy{ Person::age }
    // people.maxBy{ p -> p.age }


    //you can store Lambda in a variable,
    val isEven: (Int) -> Boolean = { i: Int -> i % 2 == 0 }

    //however, you can't store a function in a variable.
    fun isEven2(i: Int): Boolean = i % 2 == 0
    //val predicate = isEven2   //Compiler error


    //Use function reference instead
    //Function references allow you to store a reference to any defined function
    // in a variable to be able to store it and qualitative it.
    val predicate = ::isEven2
    //Keep in mind that this syntax is just another way to call a function inside the Lambda,
    // underlying implementation are the same
    val predicate2 = { i: Int -> isEven2(i) }


    //If a re-approach member is a property, or it's a function that takes zero or one argument,
    // then member reference syntax isn't that concise in comparison with the explicit Lambda syntax.
    //However, if the reproached function takes several arguments, you have to repeat all the parameter names as Lambda parameters,
    //and then explicitly pass them through, that makes this syntax robust.
    val action = { person: Person, message: String ->
        sendEmail(person, message) //This lambda is re-approach to sendEmail function
    }
    //Member references allow you to hide all the parameters, because the compiler infers the types for you.
    val action2 = ::sendEmail //Use member references


    //You can pass a function reference as an argument, whenever your Lambda tends to grow too large and to become too complicated,
    // it makes sense to extract Lambda code into a separate function, then you use a reference to this function instead of a huge Lambda.
    val list = listOf(1, 2, 3, 4)
    list.any(::isEven2)     //true
    list.filter(::isEven2)  //[2, 4]

}


fun sendEmail(person: Person, message: String) {
    //doSometing
}
