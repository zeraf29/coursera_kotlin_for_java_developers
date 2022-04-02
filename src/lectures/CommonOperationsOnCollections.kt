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


    /**
     *  mapOf(
     *      60 to listOf(Hero("The Captain", 60, Gender.MALE))
     *      42 to listOf(Hero("Frenchy", 42, Gender.MALE))
     *      9 to listOf(Hero("The Kid", 9, null))
     *      29 to listOf(Hero("Lady Lauren", 29, Gender.MALE)
     *              , Hero("First Mate", 29, Gender.MALE)
     *      37 to listOf(Hero("Sir Stephen", 37, Gender.MALE))
     *  )
     */
    val mapByAge: Map<Int, List<Hero>> = heroes.groupBy { it.age }
    //get max value by value's size
    //(29, listOf(Hero("Lady Lauren", 29, Gender.MALE) , Hero("First Mate", 29, Gender.MALE))
    val (age, group) = mapByAge.maxByOrNull { (_, group) -> group.size }!!
    println(age) //return 29


    /**
     * Returns a Map containing the elements from the given collection indexed by the key returned from keySelector function applied to each element.
     */
    val mapByName: Map<String, Hero> = heroes.associateBy { it.name }
    println(mapByName["Frenchy"]?.age)      //42
    // mapByName.["unknown"]?.age           //null
    // mapByName.getValue("unknown").age    //NoSuchElementException


    //Returns a Map containing key-value pairs provided by transform function applied to elements of the given collection.
    val mapByName2 = heroes.associate { it.name to it.age }
    mapByName2.getOrElse("unknown") { 0 }
    /**
     * val mapByName = heroes.associateBy { it.name }
     * val unknownHero = Hero("Unknown", 0, null)
     * mapByName.getOrElse("unknown") { unknownHero }.age
     */


    /**
     * flatMap: Returns a single list of all elements yielded from results of transform function being invoked on each element of original collection.
     * map: Returns a list containing the results of applying the given transform function to each element in the original collection.
     * flatMap{first -> heroes.map{second->first to second}}: return list of map(first to second)
     *         ex) "The Captain" to "The Captain" ... "The Captain" to "Sir Stephen"
     *             ...
     *             "Sir Stephen" to "The Captain" ... "Sir Stephen" to "Sir Stephen"
     */
    val allPossiblePairs = heroes
        .flatMap { first -> heroes.map { second -> first to second } }
    val (oldest2, youngest2) = allPossiblePairs.maxByOrNull { it.first.age - it.second.age }!! //60 -9 is max -> "The captain" to "The Kid"
    //val oldest3 = heroes.maxByOrNull { it.age } //same result
    println(oldest2.name) // return "The Captain"

}