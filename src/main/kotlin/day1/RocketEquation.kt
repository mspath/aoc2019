package day1

import java.io.File

fun main() {
    val input = File("data/day1/input.txt").readLines().map {
        it.toInt()
    }
    val resultBreakfast = breakfast(input)
    println(resultBreakfast) // 3477353
    val resultLunch = lunch(input)
    println(resultLunch) // 5213146
}

fun getFuel(mass: Int) = (mass / 3) - 2

fun breakfast(masses: List<Int>) = masses.sumOf { getFuel(it) }

fun getFuelRecursively(mass: Int): Int {
    return if (mass < 9) 0
    else {
        val fuel = getFuel(mass)
        fuel + getFuelRecursively(fuel)
    }
}

fun lunch(masses: List<Int>) = masses.sumOf { getFuelRecursively(it) }
