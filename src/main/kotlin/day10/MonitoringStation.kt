package day10

import java.io.File


fun main() {
    val input = File("data/day10/input.txt").readLines().mapIndexed() { y, s ->
        s.mapIndexedNotNull() { x, c ->
            if (c == '#') Point(x, y)
            else null
        }
    }.flatten()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast.second)
    val station = resultBreakfast.first
    val resultLunch = runCatching { lunch(input, station) }
    resultLunch.fold(
        onSuccess = { println(it) },
        onFailure = { println(it.message) }
    )
}

fun getAsteroidsVisible(points: List<Point>, point: Point): Int {
    val set: MutableSet<Double> = mutableSetOf()
    val offsetAbove = 1000000.0
    val offsetBelow = -1000000.0
    points.forEach {
        when {
            it.y == point.y -> set.add(Double.MIN_VALUE)
            it.y < point.y -> {
                val dx = point.x - it.x
                val dy = point.y - it.y
                val slope = dx.toDouble() / dy.toDouble() + offsetAbove
                set.add(slope)
            }
            it.y > point.y -> {
                val dx = point.x - it.x
                val dy = point.y - it.y
                val slope = dx.toDouble() / dy.toDouble() + offsetBelow
                set.add(slope)
            }
        }
    }
    return set.size + 1
}

fun breakfast(points: List<Point>): Pair<Point, Int> {
    val result: MutableMap<Point, Int> = mutableMapOf()
    points.forEach {
        result[it] = getAsteroidsVisible(points, it)
    }
    return result.toList().sortedBy { it.second }.reversed().first()
}

data class Point(val x: Int, val y: Int)

class VaporizationException(message: String): Exception(message)

fun lunch(points: List<Point>, station: Point): Int {
    throw VaporizationException("not shooting asteroids yet")
    TODO()
}