package lectures

import common.function.eq


//original
/*
class Words {
    private val list = mutableListOf<String>()

    // TODO

    override fun toString() = list.toString()
}

fun main(args: Array<String>) {
    val words = Words()
    with(words) {
        // The following two lines should compile:
        "one".record()
        +"two"
    }
    words.toString() eq "[one, two]"
}
 */

class Words {
    private val list = mutableListOf<String>()

    fun String.record(){
        list += this
    }

    operator fun String.unaryPlus(){
        record()
    }

    override fun toString() = list.toString()
}

fun main(args: Array<String>) {
    val words = Words()
    with(words) {
        // The following two lines should compile:
        "one".record()
        +"two"
    }
    words.toString() eq "[one, two]"
}