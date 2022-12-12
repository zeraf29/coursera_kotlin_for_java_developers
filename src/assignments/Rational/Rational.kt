package rationals

import java.math.BigInteger


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )

}


// 1. Int, Long, BitInteger가 상속하는 Number 부모클래스를 Receiver & argument type으로 사용
infix fun Number.divBy(denominator: Number): Rational {
    if (denominator == 0) throw IllegalArgumentException("denominator must nor be zero")

    fun checkArgument(num: Number): BigInteger = when (num) {
        is Int, is Long -> num.toLong().toBigInteger()
        is BigInteger -> num
        else -> throw IllegalArgumentException("Must be of types Int, Long, or BigInteger")
    }

    return Rational(
        numerator = checkArgument(this),
        denominator = checkArgument(denominator)
    )
}

fun String.toRational(): Rational {
    return when {
        this.contains('/') -> Rational(
            this.substringBefore('/').toBigInteger(),
            this.substringAfter('/').toBigInteger()
        )
        else -> Rational(this.toBigInteger(), 1.toBigInteger())
    }
}

class Rational(val numerator: BigInteger, val denominator: BigInteger) {
    // 2. 재사용되는 속성과 연산에 대해 프로퍼티와 멤버 메소드 구현
    //추가적인 처리가 필요할 때는 게터를 변경해야 한다. 코틀린에서는 get() 을 사용하면 해당 속성을 참조할 때 실제 게터가 자동으로 호출된다.
    private val isNormalized: Boolean
        get() {
            return numerator.gcd(denominator).compareTo(1.toBigInteger()) == 0
        }
    private val normalized: Rational
        get() {
            var num1 = this.numerator
            var num2 = this.denominator
            if ( (num1 < BigInteger.ZERO && num2 < BigInteger.ZERO)
                || (num2 < BigInteger.ZERO)
            ){
                num1 *= (-1).toBigInteger()
                num2 *= (-1).toBigInteger()
            }
            val gcd = num1.gcd(num2)
            return Rational(num1 / gcd, num2 / gcd)
        }

    private fun getLcmOfDenominators(other: Rational): BigInteger {
        val gcd = this.denominator.gcd(other.denominator)
        return this.denominator.multiply(other.denominator).divide(gcd)
    }

    //산술연산자
    operator fun plus(other: Rational): Rational {
        val denominator = getLcmOfDenominators(other)
        val numerator =
            this.numerator * (denominator / this.denominator) + other.numerator * (denominator / other.denominator)
        return Rational(numerator, denominator)
    }

    operator fun minus(other: Rational): Rational {
        val denominator = getLcmOfDenominators(other)
        val numerator =
            this.numerator * (denominator / this.denominator) - other.numerator * (denominator / other.denominator)
        return Rational(numerator, denominator)
    }

    operator fun times(other: Rational): Rational {
        return Rational(this.numerator * other.numerator, this.denominator * other.denominator)
    }

    operator fun div(other: Rational): Rational {
        return Rational(this.numerator * other.denominator, this.denominator * other.numerator)
    }


    //단항연산자
    operator fun unaryMinus(): Rational {
        return Rational(-this.numerator, this.denominator)
    }

    //비교연산자
    operator fun compareTo(other: Rational): Int {
        val num1 = this.numerator * (getLcmOfDenominators(other) / this.denominator)
        val num2 = other.numerator * (getLcmOfDenominators(other) / other.denominator)
        return num1.compareTo(num2)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Rational) {
            (this.normalized.numerator == other.normalized.numerator) && (this.normalized.denominator == other.normalized.denominator)
        } else false // nullable type check
    }

    //range
    operator fun rangeTo(other: Rational): RationalRange {
        return RationalRange(this, other)
    }

    //toString
    override fun toString(): String {
        return when {
            this.isNormalized && denominator.compareTo(1.toBigInteger()) == 0 -> numerator.toString()
            this.isNormalized && denominator.compareTo(1.toBigInteger()) != 0 -> {
                if(numerator*denominator > BigInteger.ZERO){
                    "$numerator/$denominator"
                }else{
                    "${numerator * (-1).toBigInteger()}/${denominator * (-1).toBigInteger()}"
                }
            }
            else -> this.normalized.toString()
        }
    }


}


//3. in 키워드 처리를 위한 간소화된 RaionalRange 클래스 구현
class RationalRange(val start: Rational, val endInclusive: Rational) {
    private val gcd: BigInteger get() = start.denominator.gcd(endInclusive.denominator)

    private val lcm: BigInteger get() = start.denominator.multiply(endInclusive.denominator).divide(gcd)

    operator fun contains(element: Rational): Boolean {
        val gcd1 = start.denominator.gcd(element.denominator)
        val lcm1 = start.denominator.multiply(element.denominator).divide(gcd1)
        val gcd2 = endInclusive.denominator.gcd(element.denominator)
        val lcm2 = endInclusive.denominator.multiply(element.denominator).divide(gcd2)
        return (element.numerator * (lcm1 / element.denominator) >= start.numerator * (lcm1 / start.denominator))
                && (element.numerator * (lcm2 / element.denominator) <= endInclusive.numerator * (lcm2 / endInclusive.denominator))
    }
}


/**
 * Solution 2. data class with var
 */
/*
/*
class Rational(
    var numer: BigInteger,
    var denom: BigInteger
) {
    init {
        val g = numer.gcd(denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
    }

    private fun normalize() {

    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String = "$numer/$denom"
    operator fun unaryMinus(): Rational {
        this.numer = (-1).toBigInteger() * this.numer
        return this
    }

    operator fun times(target: Rational): Rational {
        this.numer *= target.numer
        this.denom *= target.denom
        val g = this.numer.gcd(this.denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
        return this
    }

    operator fun div(target: Rational): Rational {
        this.numer /= target.numer
        this.denom /= target.denom
        val g = this.numer.gcd(this.denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
        return this
    }

    operator fun plus(target: Rational): Rational {
        this.numer += target.numer
        this.denom += target.denom
        val g = this.numer.gcd(this.denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
        return this
    }

    operator fun minus(target: Rational): Rational {
        this.numer -= target.numer
        this.denom -= target.denom
        val g = this.numer.gcd(this.denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
        return this
    }

    operator fun compareTo(target: Rational): Int = (this.numer * target.denom - target.numer * this.denom).signum()

    operator fun rangeTo(twoThirds: Rational): ClosedRange<Ration>

}

infix fun Int.divBy(d: Int): Rational = Rational(this.toBigInteger(), d.toBigInteger())
fun String.toRational(): Rational {
    val value = this.split("/")
    val numer = value[0].toBigInteger()
    val denom = if (value.size > 1) value[1].toBigInteger() else (1).toBigInteger()
    return Rational(numer, denom)
}

 */

/**
 * Solution 1. data class with var
 */
/*
data class Rational(
    var numer: BigInteger,
    var denom: BigInteger
) {
    init {
        val g = numer.gcd(denom)
        val sign = denom.signum().toBigInteger()
        numer /= g * sign
        denom /= g * sign
    }
}
 */

/**
 * Solution 2. Normalization outside of the class
 */
/*
data class Rational(
    val numer: BigInteger,
    val denom: BigInteger
)

fun createRational(n: BigInteger, d: BigInteger) = normalize(n, d)

// val r1 = Rational(a, b)
// val r2 = createRational(a, b)
 */

/**
 * Solution 3. Making constructor private
 */
/*
data class Rational
private constructor(val numer: BigInteger, val denom: BigInteger){
    companion object{
        fun create(n: BigInteger, d: BigInteger): Rational = normalize(n, d)
        fun normalize(n: BigInteger, d: BigInteger): Rational {}
    }
}
 */