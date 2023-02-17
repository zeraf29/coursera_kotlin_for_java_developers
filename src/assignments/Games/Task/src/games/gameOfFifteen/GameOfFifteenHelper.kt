package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val listOfOriginToPermutation = permutation.map { p ->
        permutation.indexOf(p) + 1 to p
    }
    return with(listOfOriginToPermutation) {
        val cnt = flatMap { (i, p1) ->
            filter { (j, _) ->
                i < j
            }.map { (_, p2) ->
                p1 to p2
            }
        }.count { (p1, p2) ->
            p1 > p2
        }
        (cnt % 2 == 0)
    }
}