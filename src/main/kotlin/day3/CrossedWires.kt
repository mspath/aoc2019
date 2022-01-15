package day3

import java.io.File
import java.lang.Integer.min
import java.lang.Integer.max
import kotlin.math.abs


fun main() {
    val input = File("data/day3/input.txt").readLines()
    breakfast(input.first(), input.last())
    lunch(input.first(), input.last())
}

data class Point(val x: Int, val y: Int)

data class Line(val start: Point, val end: Point) {
    fun isVertical() = start.x == end.x
    fun isHorizontal() = start.y == end.y
    val xRange = min(start.x, end.x)..max(start.x, end.x)
    val yRange = min(start.y, end.y)..max(start.y, end.y)
    val length: Int
        get() = abs(end.x - start.x) + abs(end.y - start.y)
}

fun IntRange.intersection(with: IntRange) = this.filter { it in with }.sorted()

fun List<Int>.manhattenMin(): Int {
    return if (0 in this) 0
    else if (this.last() < 0) this.last()
    else this.first()
}

fun MutableSet<Point>.addLine(line: Line) {
    if (line.isHorizontal()) {
        val y = line.start.y
        line.xRange.forEach { x ->
            this.add(Point(x, y))
        }
    }
    if (line.isVertical()) {
        val x = line.start.x
        for (y in line.yRange) {
            this.add(Point(x, y))
        }
    }
}

fun MutableMap<Point, Int>.addLineIndexed(line: Line, index: Int) {
    var next = index
    if (line.isHorizontal()) {
        val y = line.start.y
        if (line.start.x < line.end.x) {
            for (x in line.start.x until line.end.x) {
                if (!this.containsKey(Point(x, y))) {
                    this.put(Point(x, y), next)
                }
                next++
            }
        }
        else {
            for (x in line.start.x downTo line.end.x) {
                if (!this.containsKey(Point(x, y))) {
                    this.put(Point(x, y), next)
                }
                next++
            }
        }
    }
    else if (line.isVertical()) {
        val x = line.start.x
        if (line.start.y < line.end.y) {
            for (y in line.start.y until line.end.y) {
                if (!this.containsKey(Point(x, y))) {
                    this.put(Point(x, y), next)
                }
                next++
            }
        }
        else {
            for (y in line.start.y downTo line.end.y) {
                if (!this.containsKey(Point(x, y))) {
                    this.put(Point(x, y), next)
                }
                next++
            }
        }
    }
}

// this will return the intersection or closes point to origin if > 1
fun Line.intersection(with: Line): Point? {
    if (this.isHorizontal() && with.isHorizontal()) {
        if (this.start.y != with.start.y) return null
        else if (this.xRange.intersection(with.xRange).size == 0) return null
        else return Point(this.xRange.intersection(with.xRange).manhattenMin(), this.start.y)
    }
    else if (this.isVertical() && with.isVertical()) {
        if (this.start.x != with.start.x) return null
        else if (this.yRange.intersection(with.yRange).size == 0) return null
        else return Point(this.start.x, this.yRange.intersection(with.yRange).manhattenMin())
    }
    else {
        if (this.xRange.intersection(with.xRange).size == 0) return null
        if (this.yRange.intersection(with.yRange).size == 0) return null
        return Point(this.xRange.intersection(with.xRange).manhattenMin(), this.yRange.intersection(with.yRange).manhattenMin())
    }
}

class Instruction(val direction: Char, val value: Int) {

    companion object {
        fun fromString(s: String): Instruction {
            val c = s[0]
            val v = s.drop(1).toInt()
            return Instruction(c, v)
        }
    }
}

fun Instruction.buildLine(from: Point): Line {
    val to = when(this.direction) {
        'U' -> Point(from.x, from.y + value)
        'D' -> Point(from.x, from.y - value)
        'L' -> Point(from.x - value, from.y)
        'R' ->  Point(from.x + value, from.y)
        else -> TODO("should not happen")
    }
    return Line(from, to)
}

fun breakfast(pointsA: String, pointsB: String) {
    var point = Point(0, 0)
    val wireA: MutableSet<Point> = mutableSetOf()
    val cmdsA = pointsA.split(",").map { Instruction.fromString(it) }
    cmdsA.forEach {
        val line = it.buildLine(point)
        wireA.addLine(line)
        point = line.end
    }
    point = Point(0, 0)
    val wireB: MutableSet<Point> = mutableSetOf()
    val cmdsB = pointsB.split(",").map { Instruction.fromString(it) }
    cmdsB.forEach {
        val line = it.buildLine(point)
        wireB.addLine(line)
        point = line.end
    }
    val both = wireA.intersect(wireB)
    // first point is origin so drop it
    val result = both.sortedBy {
        abs(it.x) + abs(it.y)
    }.drop(1).first()
    println(result)
    println(abs(result.x) + abs(result.y))
}

fun lunch(pointsA: String, pointsB: String) {
    var point = Point(0, 0)
    var index = 0
    val wireA: MutableSet<Point> = mutableSetOf()
    val costA: MutableMap<Point, Int> = mutableMapOf()
    val cmdsA = pointsA.split(",").map { Instruction.fromString(it) }
    cmdsA.forEach {
        val line = it.buildLine(point)
        wireA.addLine(line)
        costA.addLineIndexed(line, index)
        point = line.end
        index += line.length
    }
    point = Point(0, 0)
    index = 0
    val wireB: MutableSet<Point> = mutableSetOf()
    val costB: MutableMap<Point, Int> = mutableMapOf()
    val cmdsB = pointsB.split(",").map { Instruction.fromString(it) }
    cmdsB.forEach {
        val line = it.buildLine(point)
        wireB.addLine(line)
        costB.addLineIndexed(line, index)
        point = line.end
        index += line.length
    }
    val both = wireA.intersect(wireB).filter { it != Point(0, 0) }
    val crossedCosts = both.map {
        val costA = costA.getOrDefault(it, Int.MAX_VALUE / 2)
        val costB = costB.getOrDefault(it, Int.MAX_VALUE / 2)
        costA + costB to it
    }.sortedBy { it.first }
    println(crossedCosts)
    println(crossedCosts.first().first)
}