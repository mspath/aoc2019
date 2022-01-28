package day12

import java.lang.Math.abs

data class Moon(
    var x: Int, var y: Int, var z: Int,
    var dx: Int = 0, var dy: Int = 0, var dz: Int = 0,
    var gx: Int = 0, var gy: Int = 0, var gz: Int = 0
)

val moonSampleOne = listOf(
    Moon(-1, 0, 2),
    Moon(2, -10, -7),
    Moon(4, -8, 8),
    Moon(3, 5, -1)
)

val moonSampleTwo = listOf(
    Moon(-8, -10, 0),
    Moon(5, 5, 10),
    Moon(2, -7, 3),
    Moon(9, -8, -3)
)

val moons = listOf(
    Moon(16, -11, 2),
    Moon(0, -4, 7),
    Moon(6, 4, -10),
    Moon(-3, -2, -4)
)

fun main() {
    breakfast(moons)
}

fun breakfast(moons: List<Moon>) {
    repeat(1000) {
        for (i in 0..3) {
            val m1 = moons[i]
            for (j in 0..3) {
                val m2 = moons[j]
                var g = when {
                    m1.x > m2.x -> -1
                    m1.x < m2.x -> 1
                    else -> 0
                }
                m1.gx += g
                g = when {
                    m1.y > m2.y -> -1
                    m1.y < m2.y -> 1
                    else -> 0
                }
                m1.gy += g
                g = when {
                    m1.z > m2.z -> -1
                    m1.z < m2.z -> 1
                    else -> 0
                }
                m1.gz += g
            }
        }
        moons.forEach {
            it.dx += it.gx
            it.dy += it.gy
            it.dz += it.gz
            it.gx = 0
            it.gy = 0
            it.gz = 0
            it.x += it.dx
            it.y += it.dy
            it.z += it.dz
        }
    }
    var result = 0
    moons.forEach {
        result += (abs(it.x) + abs(it.y) + abs(it.z)) * (abs(it.dx) + abs(it.dy) + abs(it.dz))
    }
    println(result)
}