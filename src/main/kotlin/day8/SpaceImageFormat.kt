package day8

import java.io.File

fun main() {
    val input = File("data/day8/input.txt").readText().mapNotNull {
        it.toString().toIntOrNull()
    }
    val layerSize = Pair(25, 6)
    breakfast(input, layerSize)
    lunch(input, layerSize)
}

fun breakfast(pixel: List<Int>, layerSize: Pair<Int, Int>) {
    val size = layerSize.first * layerSize.second
    val layers = pixel.windowed(size, size)
    val counts = layers.map { layer ->
        layer.groupingBy { it }.eachCount()
    }
    val layerMinZero = counts.sortedBy { it[0] }.first()
    val ones = layerMinZero.getOrDefault(1, 0)
    val twos = layerMinZero.getOrDefault(2, 0)
    println(ones * twos)
}

fun List<Int>.getPixelValue(): Int {
    if (!(this.contains(0) || this.contains(1))) return 2
    val firstBlack = this.indexOf(0)
    val firstWhite = this.indexOf(1)
    if (firstBlack == -1) return 1
    if (firstBlack < firstWhite) return 0
    return 1
}

fun lunch(pixel: List<Int>, layerSize: Pair<Int, Int>) {
    val size = layerSize.first * layerSize.second
    val layers = pixel.windowed(size, size)
    val pix = (0 until size).map { index ->
        layers.map { it[index] }.toList().getPixelValue()
    }
    for (i in 0 until size) {
        if (i % layerSize.first == 0) print("\n")
        if (pix[i] == 0) print("#")
        else print(".")
    }
    println()
}