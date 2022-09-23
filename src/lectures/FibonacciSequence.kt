package lectures

import common.function.eq

fun fibonacci(): Sequence<Int> = sequence {
    var elements = Pair(0, 1)

    while (true) {
        yield(elements.first) //Yields a value to the Iterator being built and suspends until the next value is requested.
        elements = Pair(elements.second, elements.first + elements.second)
        /**
         * [0]
         * [0, 1]
         * [0, 1, 1]
         * [0, 1, 1, 2]
         * [0, 1, 1, 2, 3]
         * [0, 1, 1, 2, 3, 5]
         * ...
         */
    }
}

fun main(args: Array<String>) {
    fibonacci().take(4).toList().toString() eq
            "[0, 1, 1, 2]"

    fibonacci().take(10).toList().toString() eq
            "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}