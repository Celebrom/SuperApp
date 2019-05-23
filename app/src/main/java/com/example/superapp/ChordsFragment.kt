package com.example.superapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chords_fragment.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random


class ChordsFragment : Fragment() {

    companion object {
        fun newInstance() = ChordsFragment()
    }

    private lateinit var viewModel: ChordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chords_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChordsViewModel::class.java)
        // TODO: Use the ViewModel

        var timer = Timer()
        val defaultSeconds = "10"

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

        fun generateChord() {
            chordTextView.text = generateNote()
            extensionTextView.text = generateExtension()
            positionTextView.text = generatePosition()
        }

        fun initializeTimer(mustStartTime: Boolean) {
            if (mustStartTime) {
                if (secondsInput.text.isNullOrEmpty())
                    secondsInput.setText(defaultSeconds)

                val milliseconds = secondsInput.text.toString().toLong() * 1000
                timer = Timer()
                timer.scheduleAtFixedRate(0, milliseconds) { generateChord() }
            } else
                timer.cancel()
        }

        positionA.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) positions.add("Position A")
            else positions.remove("Position A")
        }

        positionB.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) positions.add("Position B")
            else positions.remove("Position B")
        }

        min7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("-7")
            else extensions.remove("-7")
        }

        dom7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("7")
            else extensions.remove("7")
        }

        maj7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("maj7")
            else extensions.remove("maj7")
        }

        min7b5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("-7b5")
            else extensions.remove("-7b5")
        }

        maj7b9.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("7b9")
            else extensions.remove("7b9")
        }

        aug7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("7#5#9")
            else extensions.remove("7#5#9")
        }

        dim7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) extensions.add("dim7")
            else extensions.remove("dim7")
        }

        randomizedButton.setOnClickListener {
            generateChord()
        }

        timerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            initializeTimer(isChecked)
        }

        initializeTimer(true)
    }
}
