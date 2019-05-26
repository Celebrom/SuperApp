package com.example.superapp

import android.arch.lifecycle.ViewModel
import kotlin.random.Random

class ChordsViewModel : ViewModel() {

    val notes = hashMapOf(
        0 to "Ab",
        1 to "A",
        2 to "Bb",
        3 to "B",
        4 to "C",
        5 to "C#",
        6 to "D",
        7 to "Eb",
        8 to "E",
        9 to "F",
        10 to "F#",
        11 to "G"
    )
    val positions = hashSetOf("Position A", "Position B")
    val extensions = hashSetOf("-7", "7", "maj7", "-7b5", "7b9", "7#5#9", "dim7")

    fun generateExtension(): String {
        if (extensions.isEmpty()) return ("")
        return (extensions.elementAt(Random.nextInt(until = extensions.size)).toString())
    }

    fun generateNote(): String {
        if (notes.isEmpty()) return ("")
        return (notes[Random.nextInt(until = 12)].toString())
    }

    fun generatePosition(): String {
        if (positions.isEmpty()) return ("")
        return (positions.elementAt(Random.nextInt(until = positions.size)).toString())
    }
}
