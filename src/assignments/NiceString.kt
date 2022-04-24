package assignments


fun String.isNice(): Boolean {
    //My solution
    /*
    val fList = listOf("bu", "ba", "be")
    val sList = listOf("a", "e", "i", "o", "u")
    val tList = this.chunked(1)

    val fResult = (!fList.any{ this.contains(it) }).compareTo(false)
    val sResult = (tList.count{ sList.contains(it) } >= 3).compareTo(false)
    var tResult = 0
    for(i in 0 until (tList.size-1)){
        if(tList[i] == tList[i+1]) tResult++
    }


    return ((fResult+sResult+tResult)-1 > 0)
     */

    //lecture solution
    val noBadSubstring = setOf("bu", "ba", "be").none { this.contains(it) }
    val hasThreeVowels = count { it in "aeiou" } >= 3 //only 5 charactrers, make 1 string is not matter for performance
    val hasDouble = zipWithNext().any { it.first == it.second }

    return listOf(noBadSubstring, hasThreeVowels, hasDouble).count { it } >= 2

}


/**
 * Nice String
 * A string is nice if at least two of the following conditions are satisfied:
 *
 * It doesn't contain substrings bu, ba or be;
 * It contains at least three vowels (vowels are a, e, i, o and u);
 * It contains a double letter (at least two similar letters following one another), like b in "abba".
 * Your task is to check whether a given string is nice. Strings for this task will consist of lowercase letters only. Note that for the purpose of this task, you don't need to consider 'y' as a vowel.
 *
 * Note that any two conditions might be satisfied to make a string nice. For instance, "aei" satisfies only the conditions #1 and #2, and ```"nn"` satisfies the conditions #1 and #3, which means both strings are nice.
 *
 * Example 1
 * "bac" isn't nice. No conditions are satisfied: it contains a ba substring, contains only one vowel and no doubles.
 *
 * Example 2
 * "aza" isn't nice. Only the first condition is satisfied, but the string doesn't contain enough vowels or doubles.
 *
 * Example 3
 * "abaca" isn't nice. The second condition is satisfied: it contains three vowels a, but the other two aren't satisfied: it contains ba and no doubles.
 *
 * Example 4
 * "baaa" is nice. The conditions #2 and #3 are satisfied: it contains three vowels a and a double a.
 *
 * Example 5
 * "aaab" is nice, because all three conditions are satisfied.
 *
 * Run TestNiceString to check your solution.
 *
 *
 *
 */

/*
package nicestring

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestNiceString {

    private fun testNiceString(string: String, expected: Boolean) {
        Assert.assertEquals("Wrong result for \"$string\".isNice()", expected, string.isNice())
    }

    @Test
    fun testExample1() = testNiceString("bac", false)

    @Test
    fun testExample2() = testNiceString("aza", false)

    @Test
    fun testExample3() = testNiceString("abaca", false)

    @Test
    fun testExample4() = testNiceString("baaa", true)

    @Test
    fun testExample5() = testNiceString("aaab", true)

    @Test
    fun testNice01() = testNiceString("geaa", true)

    @Test
    fun testNice02() = testNiceString("ynzz", true)

    @Test
    fun testNice03() = testNiceString("ijao", true)

    @Test
    fun testNice04() = testNiceString("nn", true)

    @Test
    fun testNice05() = testNiceString("zuu", true)

    @Test
    fun testNice06() = testNiceString("uaa", true)

    @Test
    fun testNice07() = testNiceString("upui", true)

    @Test
    fun testNice08() = testNiceString("oouh", true)

    @Test
    fun testNice09() = testNiceString("wddf", true)

    @Test
    fun testNice10() = testNiceString("baii", true)

    @Test
    fun testNice11() = testNiceString("obee", true)

    @Test
    fun testNice12() = testNiceString("beiuu", true)

    @Test
    fun testNice13() = testNiceString("uyyxqptkvbtz", true)

    @Test
    fun testNice14() = testNiceString("limseelx", true)

    @Test
    fun testNice15() = testNiceString("zwhueqe", true)

    @Test
    fun testNice16() = testNiceString("iwuvevd", true)

    @Test
    fun testNice17() = testNiceString("qcdpogyeti", true)

    @Test
    fun testNice18() = testNiceString("ygmuuyuj", true)

    @Test
    fun testNice19() = testNiceString("cuimjyyakh", true)

    @Test
    fun testNice20() = testNiceString("eufalmmwwbnid", true)

    @Test
    fun testNice21() = testNiceString("kbzstzwhjeestb", true)

    @Test
    fun testNice22() = testNiceString("rdfieknqrwxx", true)

    @Test
    fun testNice23() = testNiceString("mzhevzkmmz", true)

    @Test
    fun testNice24() = testNiceString("mzhevzkmmz", true)

    @Test
    fun testNice25() = testNiceString("jootdvhbesdns", true)

    @Test
    fun testNice26() = testNiceString("crncuotgburrcv", true)

    @Test
    fun testNice27() = testNiceString("burppqqeivsrw", true)

    @Test
    fun testNotNice1() = testNiceString("", false)

    @Test
    fun testNotNice2() = testNiceString("hfrcnykh", false)

    @Test
    fun testNotNice3() = testNiceString("qc", false)

    @Test
    fun testNotNice4() = testNiceString("ymsetecw", false)

    @Test
    fun testNotNice5() = testNiceString("bei", false)

    @Test
    fun testNotNice6() = testNiceString("mbalqw", false)

    @Test
    fun testNotNice7() = testNiceString("bekqe", false)

    @Test
    fun testNotNice8() = testNiceString("luosbaqzdh", false)

    @Test
    fun testNotNice9() = testNiceString("zcgsdbuxeo", false)

    @Test
    fun testNotNice10() = testNiceString("bukipcmju", false)

    @Test
    fun testNotNice11() = testNiceString("sisxxjwlkbu", false)

    @Test
    fun testNotNice12() = testNiceString("bawbxffum", false)

    @Test
    fun testNotNice13() = testNiceString("bbau", false)

    @Test
    fun testNotNice14() = testNiceString("ax", false)

    @Test
    fun testNotNice15() = testNiceString("baa", false)

    @Test
    fun testNotNice16() = testNiceString("aebe", false)

    @Test
    fun testNotNice17() = testNiceString("bbau", false)

    @Test
    fun testNotNice18() = testNiceString("uibe", false)

    @Test
    fun testNotNice19() = testNiceString("srxn", false)

    @Test
    fun testNotNice20() = testNiceString("wvad", false)
}
 */