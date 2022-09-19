package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.minus(trips.map { it.driver }.toSet())

fun TaxiPark.findFakeDrivers1(): Set<Driver> =
    allDrivers.filter { d -> trips.none { it.driver == d } }.toSet()

//This is slightly better since it does only one iteration via map.
//But the difference between these two solution isn't that noticeable for not the worst-case scenario.
fun TaxiPark.findFakeDrivers2(): Set<Driver> =
    allDrivers - trips.map { it.driver }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    if (minTrips == 0) this.allPassengers
    else trips.map { it.passengers.toList() }.flatten().groupingBy { it.name }.eachCount()
        .filter { it.value >= minTrips }.map { Passenger(it.key) }.toSet()

fun TaxiPark.findFaithfulPassengers1(minTrips: Int): Set<Passenger> =
    trips
        .flatMap(Trip::passengers)                          //.flatMap { it.passengers } -> change to a reference for clear read
        .groupBy { passenger -> passenger }                 //.groupBy { it -> it } -> change for clear
        .filterValues { group -> group.size >= minTrips }   //.filter { it.value.size >= minTrips } -> .filter { (_, group) -> group.size >= minTrips }
        .keys                                               //.map { it.key }.toSet()

fun TaxiPark.findFaithfulPassengers3(minTrips: Int): Set<Passenger> =
    allPassengers
        .partition { p ->
            trips.sumBy { t ->
                if (p in t.passengers) 1 else 0
            } >= minTrips
        }
        .first
        .toSet()

fun TaxiPark.findFaithfulPassengers3toClear(minTrips: Int): Set<Passenger> =
    allPassengers
        .filter { p ->                                      // Only need values about condition true. Not need second value -> change to filter
            trips.count { p in it.passengers } >= minTrips  // subMy -> count. check directly condition
        }.toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.trips.filter { it.driver.equals(driver) }.map { it.passengers.toList() }.flatten().groupingBy { it.name }
        .eachCount().filter { it.value > 1 }.map { Passenger(it.key) }.toSet()

//This solution is same logic with I made.
//But this is more clear and not need to unnecessary step.
fun TaxiPark.findFrequentPassengers1(driver: Driver): Set<Passenger> =
    trips.filter { trip -> trip.driver == driver }
        .flatMap(Trip::passengers)
        .groupBy { passenger -> passenger }
        .filterValues { group -> group.size > 1 }
        .keys

fun TaxiPark.findFrequentPassengers2(driver: Driver): Set<Passenger> =
    allPassengers
        .filter { p ->
            trips.count { it.driver == driver && p in it.passengers } > 1
        }
        .toSet()

fun TaxiPark.findSmartPassengers1(): Set<Passenger> {
    val pair = trips.partition { it.discount is Double }
    return allPassengers
        .filter { passenger ->
            pair.first.count { it.passengers.contains(passenger) } >
                    pair.second.count { it.passengers.contains(passenger) }
        }
        .toSet()
}

fun TaxiPark.findSmartPassengers2(): Set<Passenger> {
    val (tripsWithDiscount, tripsWithoutDiscount) =
        trips.partition { it.discount != null }
    return allPassengers
        .filter { passenger ->
            tripsWithDiscount.count { passenger in it.passengers } >
                    tripsWithoutDiscount.count { passenger in it.passengers }
        }
        .toSet()
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val pair = trips.partition { it.discount is Double }
    return allPassengers
        .filter { passenger ->
            pair.first.count { it.passengers.contains(passenger) } >
                    pair.second.count { it.passengers.contains(passenger) }
        }
        .toSet()
}

fun TaxiPark.findSmartPassengers4(): Set<Passenger> {
    val (tripsWithDiscount, tripsWithoutDiscount) = trips.partition { it.discount != null }
    return allPassengers
        .filter { passenger ->
            tripsWithDiscount.count { passenger in it.passengers } >
                    tripsWithoutDiscount.count { passenger in it.passengers }
        }
        .toSet()
}

fun TaxiPark.findSmartPassengers3(): Set<Passenger> =
    allPassengers
        .groupBy(
            { it },
            { p -> trips.filter { t -> p in t.passengers } }
        )
        .entries
        .filter {
            val group = it.value.first()
            val (withDiscount, withoutDiscount) = group
                .partition { it.discount != null }
            withDiscount.size > withoutDiscount.size
        }
        .map { it.key }
        .toSet()

fun TaxiPark.findSmartPassengers5(): Set<Passenger> =
    allPassengers
        .groupBy(
            { it },
            { p -> trips.filter { t -> p in t.passengers } }
        )
        .entries
        .filter {
            val group = it.value.first()
            val (withDiscount, withoutDiscount) = group
                .partition { it.discount != null }
            withDiscount.size > withoutDiscount.size
        }
        .map { it.key }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val duration = this.trips.map { it.duration }.groupingBy {
        (it / 10) * 10
    }.eachCount().maxByOrNull { it.value }?.key
    return duration?.let { IntRange(it, duration + 9) }
}


/*
fun TaxiPark.findTheMostFrequentTripDurationPeriod1(): IntRange? {
    return trips
        .groupBy { it.duration / 10 * 10..it.duration / 10 * 10..it.duration / 10 * 10 + 9 }
        .toList()
        .sortedByDescending{ it -> it.second.size }
        .firstOrNull()
        ?.first
}
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod1(): IntRange? =
    trips
        .groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end //start <= it.duration <= end
        }.toList()
        .maxByOrNull { (_, group) -> group.size }
        ?.first


/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()) return false
    val limit = this.allDrivers.size * 0.2
    // val totalCost = this.trips.map { it.cost }.sum()
    val totalCost = this.trips.sumOf { it.cost }
    val driverIncome = this.allDrivers.associate { it.name to 0.0 }.toMutableMap()
    this.trips.map { trip -> driverIncome[trip.driver.name] = driverIncome[trip.driver.name]!!.plus(trip.cost) }
    val driverIncomeList = driverIncome.filter { it.value > 0.0 }.toList().sortedByDescending { (_, value) -> value }
        .toMap().values.toList()
    var tripIncome = 0.0
    for (i in 0 until limit.toInt()) {
        tripIncome += driverIncomeList[i]
    }
    return tripIncome >= totalCost * 0.8
}


fun TaxiPark.checkParetoPrinciple2(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumOf(Trip::cost)
    //you can also use like below.
    //val totalIncome = trips.sumOf{ it.cost }
    val sortedDriversIncome: List<Double> = trips
        .groupBy(Trip::driver) // Map<driver, List<Trip>>
        //.groupBy{ it.driver } //you can also use like this.
        .map { (_, tripsByDriver) -> tripsByDriver.sumOf { it.cost } } //only get value(trips by driver), then sumOf it's cost
        .sortedDescending() //Descending. Top driver move to start index

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
        .take(numberOfTopDrivers) //Only Top drivers income (0~numberOfTopDrivers, cause already sortedDescending)
        .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}

