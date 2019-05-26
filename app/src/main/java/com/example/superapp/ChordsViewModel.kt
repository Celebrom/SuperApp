package com.example.superapp

import android.arch.lifecycle.ViewModel
import kotlin.random.Random

class ChordsViewModel : ViewModel() {

    val notes = hashSetOf("Ab", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G")
    val positions = hashSetOf("Position A", "Position B")
    val extensions = hashSetOf("-7", "7", "maj7", "-7b5", "7b9", "7#5#9", "dim7")

    fun generateExtension(): String {
        if (extensions.isEmpty()) return ("")
        return (extensions.elementAt(Random.nextInt(until = extensions.size)).toString())
    }

    fun generateNote(): String {
        if (notes.isEmpty()) return ("")
        return (notes.elementAt(Random.nextInt(until = notes.size)).toString())
    }

    fun generatePosition(): String {
        if (positions.isEmpty()) return ("")
        return (positions.elementAt(Random.nextInt(until = positions.size)).toString())
    }

    fun updateExtensionSet(elementToUpdate: String, mustAdd: Boolean) {
        if (mustAdd) extensions.add(elementToUpdate)
        else extensions.remove(elementToUpdate)
    }

    fun updatePositionSet(elementToUpdate: String, mustAdd: Boolean) {
        if (mustAdd) positions.add(elementToUpdate)
        else positions.remove(elementToUpdate)
    }
}
