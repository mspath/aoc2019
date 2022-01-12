package day2

import java.io.File

fun main() {
    val input = File("data/day2/input.txt").readText().split(",").map {
        it.toInt()
    }
    val resultBreakfast = breakfast(input.toMutableList(), 12, 2)
    println(resultBreakfast)

    val resultLunch = lunch(input, 19690720)
    println(resultLunch)
}

fun breakfast(intcode: MutableList<Int>, noun: Int, verb: Int): Int {
    intcode[1] = noun
    intcode[2] = verb
    var position = 0
    while (intcode[position] != 99) {
        val opcode = intcode[position]
        if (opcode !in 1..2) return -1
        require(opcode in 1..2)
        val a = intcode[position + 1]
        val b = intcode[position + 2]
        val c = intcode[position + 3]
        when (opcode) {
            1 -> {
                intcode[c] = intcode[a] + intcode[b]
            }
            2 -> {
                intcode[c] = intcode[a] * intcode[b]
            }
        }
        position += 4
    }

    return intcode[0]
}

fun lunch(intcode: List<Int>, result: Int): Int {
    for (noun in 0..99) {
        for (verb in 0..99) {
            val current = intcode.toMutableList()
            val end = breakfast(current, noun, verb)
            if (end == result) {
                return 100 * noun + verb
            }
        }
    }
    return -1
}