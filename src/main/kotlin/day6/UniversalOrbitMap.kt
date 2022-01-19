package day6

import java.io.File

fun main() {
    val input = File("data/day6/input.txt").readLines().map {
        val (center, orbit) = it.split(')')
        Orbit(center, orbit)
    }
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    val resultLunch = lunch(input)
    println(resultLunch)
}

data class Orbit(val center: String, val orbit: String)

fun Map<String, String>.getDepth(orbit: String): Int {
    val center = this[orbit]
    return if (center.isNullOrEmpty()) 0
    else 1 + this.getDepth(center)
}

fun Map<String, String>.getPathToRoot(orbit: String, path: MutableList<String>) {
    val center = this[orbit]
    if (center.isNullOrEmpty()) return
    else {
        path.add(center)
        return this.getPathToRoot(center, path)
    }
}

fun breakfast(orbits: List<Orbit>): Int {
    val orbitCenterMap = orbits.associate {
        it.orbit to it.center
    }
    val orbitDepths = orbits.sumOf {
        orbitCenterMap.getDepth(it.orbit)
    }
    return orbitDepths
}

fun lunch(orbits: List<Orbit>): Int {
    val orbitCenterMap = orbits.associate {
        it.orbit to it.center
    }
    val pathYou: MutableList<String> = mutableListOf()
    orbitCenterMap.getPathToRoot("YOU", pathYou)
    val pathSan: MutableList<String> = mutableListOf()
    orbitCenterMap.getPathToRoot("SAN", pathSan)
    val pathCommon = pathYou.intersect(pathSan.toSet())
    val stepsBetweenYouAndSan = pathYou.size + pathSan.size - pathCommon.size * 2
    return stepsBetweenYouAndSan
}