package assignments

import common.function.eq

/**
 * isEmptyOrNull()
 *Add and implement an extension function 'isEmptyOrNull()' on the type String?.
 *It should return true, if the string is null or empty.
 */

fun main(args: Array<String>) {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s2.isEmptyOrNull() eq true

    val s3 = "   "
    s3.isEmptyOrNull() eq false
}

fun String?.isEmptyOrNull() = (this == null || this.isEmpty())