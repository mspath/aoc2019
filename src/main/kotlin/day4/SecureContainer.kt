package day4


fun main() {
    val input = 265275..781584
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    val resultLunch = lunch(input)
    println(resultLunch)
}

fun Int.isSixDigits() = this.isInRange(100000..999999)

fun Int.isInRange(range: IntRange) = this in range

fun Int.hasTwoAdjacent(): Boolean {
    val pairs = this.toString().windowed(2)
    return pairs.any { it.first() == it.last() }
}

fun Int.hasNoDecreasing(): Boolean {
    val pairs = this.toString().windowed(2)
    return pairs.all { it.first() <= it.last() }
}

fun Int.hasTwoUngroupedAdjacent(): Boolean {
    val digits = this.toString()
    val groups = digits.groupBy { it }
    // this should work since previous filters ensure lower digits don't return
    return groups.any{
        it.value.size == 2
    }
}

fun breakfast(input: IntRange): Int {
    val valid = input.filter {
        it.isSixDigits()
                && it.isInRange(input)
                && it.hasTwoAdjacent()
                && it.hasNoDecreasing()
    }
    return valid.size
}

fun lunch(input: IntRange): Int {
    val valid = input.filter {
        it.isSixDigits()
                && it.isInRange(input)
                && it.hasTwoAdjacent()
                && it.hasNoDecreasing()
                && it.hasTwoUngroupedAdjacent()
    }
    return valid.size
}