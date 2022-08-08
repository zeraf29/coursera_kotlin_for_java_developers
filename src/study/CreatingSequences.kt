package study

import java.math.BigInteger
import kotlin.random.Random

class CreatingSequences {
}

fun main() {
    val seq = generateSequence {
        // will return null, when value becomes non-positive,
        // and that will terminate the sequence
        Random.nextInt(5).takeIf { it > 0 }
    }
    println(seq.toList())

    // seq.forEach {  }  // <- iterating that sequence second time will fail, This sequence can be consumed only once.

    //val input = generateSequence {
    //    readLine().takeIf { it != "exit" }
    //}
    //println(input.toList())


    //val numbers = generateSequence(0) { it + 1 }
    //println(numbers.take(5).toList())

    // to prevent integer overflow:
    val numbers = generateSequence(BigInteger.ZERO) {
        it + BigInteger.ONE
    }

    val numbers2 = generateSequence(3) { n ->
        println("Generating element...")
        (n + 1).takeIf { it < 7 }
    }
    println(numbers2.first())
    //This asked for the first element, and this first element is already given
    //Sequences are evaluated lazily, nothing happens until you explicitly ask for it
    //This only ask for the first element, so this lambda is never called

    //When you explicitly ask for other elements, then the necessary elements are generated.
    println(numbers2.toList())

}