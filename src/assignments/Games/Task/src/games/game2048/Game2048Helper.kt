package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/

fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> =
        this.asSequence()
                .filterNotNull()
                .fold(mutableListOf()){ result, curr ->
                        //put values in empty List, then compare near values
                        when(curr){
                                result.lastOrNull() -> {
                                        result[result.lastIndex] = merge(curr)
                                }
                                else -> {
                                        result.add(curr)
                                }
                        }
                        result
                }

/*
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
        var buffer = ""
        val rList = mutableListOf<T>()
        this.filterNotNull().forEach {
                val current = it.toString()
                buffer = if(buffer.contains(current)){
                        rList.add((buffer+current) as T)
                        ""
                }else if(buffer.isEmpty()) {
                        current
                }else{
                        rList.add(buffer as T)
                        current
                }
        }
        if(buffer.isNotBlank()) rList.add(buffer as T)
        return rList
}
*/

//checking sequence
//https://tak8997.github.io/2021/04/24/Kotlin-Sequence/

//using fold()
//fold(): stack and add from collection's item, then return it.
//https://velog.io/@blucky8649/%EC%BD%94%ED%8B%80%EB%A6%B0-reduce-fold-%ED%95%A8%EC%88%98


//Lamda
//https://ddolcat.tistory.com/557
//https://ddolcat.tistory.com/557