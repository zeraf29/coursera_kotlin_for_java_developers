package lectures


//This function Just return empty list for listOf(3, 0, 5)
//Because "if (it == 0) return listOf()" line directly end loop.
fun duplicateNonZero(list: List<Int>): List<Int> {
    return list.flatMap {
        //you can't expect that return continue to behave in the same way.
        if (it == 0) return listOf() //Return inside foreach returns from the whole function as well.
        listOf(it, it)
    }
}
fun containsZero(list: List<Int>): Boolean{
    for (i in list) {
        if (i==0) return true // Return simply returns from the function, end of function
    }
    return false
}
fun duplicateNonZeroReturnFromLambda(list: List<Int>): List<Int> {
    //return list.flatMap l1@{ //you can set specific label name
    return list.flatMap {
        //you can't expect that return continue to behave in the same way.
        //if (it == 0) return@l1 listOf<Int>() //you can call specific label name
        if (it == 0) return@flatMap listOf<Int>()//Return inside foreach returns from the whole function as well.
        listOf(it, it)
    }
}

fun main(args: Array<String>) {
    val list = listOf(3, 0, 5)

    println(duplicateNonZero(list))

    println(containsZero(list))

    println(duplicateNonZeroReturnFromLambda(list))

}


