package com.example.superapp

import android.arch.lifecycle.ViewModel
import kotlin.random.Random

class ChordsViewModel : ViewModel() {

    private val notes = hashSetOf("Ab", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G")
    private val positions = hashSetOf("Position A", "Position B")
    private val extensions = hashSetOf("-7", "7", "maj7", "-7b5", "7b9", "7#5#9", "dim7")
    private val chordsList = mutableListOf<Chord>()

    init {
        regenerateChords()
    }

    private fun regenerateChords() {
        chordsList.clear()
        for (note in notes) {
            for (extension in extensions) {
                for (position in positions) {
                    chordsList.add(Chord(note, extension, position))
                }
            }
        }
    }

    fun removeChord(chord: Chord){
        chordsList.remove(chord)
        if (chordsList.isEmpty()) regenerateChords()
    }

    fun getRandomChord() : Chord {
        return (chordsList.elementAt(Random.nextInt(until = chordsList.size)))
    }

    fun updateExtensionSet(elementToUpdate: String, mustAdd: Boolean) {
        if (mustAdd) extensions.add(elementToUpdate)
        else extensions.remove(elementToUpdate)

        if (extensions.isEmpty()) extensions.add("")
        else extensions.remove("")

        regenerateChords()
    }

    fun updatePositionSet(elementToUpdate: String, mustAdd: Boolean) {
        if (mustAdd) positions.add(elementToUpdate)
        else positions.remove(elementToUpdate)

        if (positions.isEmpty()) positions.add("")
        else positions.remove("")

        regenerateChords()
    }
}
