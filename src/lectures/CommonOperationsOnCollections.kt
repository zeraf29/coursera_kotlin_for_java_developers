package lectures

/**
 * practice of common operations on collections
 */

data class Hero(
    val name: String,
    val age: Int,
    val gender: Gender?
)

enum class Gender { MALE, FEMALE }


fun main(args: Array<String>) {

    val heroes = listOf(
        Hero("The Captain", 60, Gender.MALE),
        Hero("Frenchy", 42, Gender.MALE),
        Hero("The Kid", 9, null),
        Hero("Lady Lauren", 29, Gender.MALE),
        Hero("First Mate", 29, Gender.MALE),
        Hero("Sir Stephen", 37, Gender.MALE)
    )

    // "Sir Stephen"
    // get last element
    println(heroes.last().name)

    // return null
    // get condition value or null if not exist
    println(heroes.firstOrNull { it.age == 30 }?.name)

    //NoSuchElementException
    //heroes.first { it.age==30 }.name

    //Map transform each element in a collection and stores all thr resulting elements in a new list.
    println(heroes.map { it.age }.distinct().size)      //this return 5
    heroes.map { it.age }                               //[70, 42, 9, 29, 29, 37]
    heroes.map { it.age }.distinct()                    //[60.42.9.29.37]

    //Filter returns only the elements that satisfy the predicate,
    //and in a sense, throws out all the elements that don't satisfy the predicate.
    println(heroes.filter { it.age < 30 }.size)         //return 3

    // Partition divides the collection into two collections.
    // If you need to keep both groups of elements that satisfy or do not satisfy the predicate, you can use the partition.
    // It returns two collections, for the good elements and the remaining ones.
    val (yougest, oldest) = heroes.partition { it.age < 30 }
    println(oldest.size) // return 3

    //maxBy() was deprecated
    //Returns the first element yielding the largest value of the given function or null if there are no elements.
    println(heroes.maxByOrNull { it.age }?.name) //return The Captain

    //Returns true if all elements match the given predicate.
    heroes.all { it.age < 50 } // return false

    //Returns true if array has at least one element.
    heroes.any { it.gender == Gender.FEMALE } // return true
}