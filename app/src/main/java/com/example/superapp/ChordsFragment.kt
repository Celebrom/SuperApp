package com.example.superapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chords_fragment.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class ChordsFragment : Fragment() {

    companion object {
        fun newInstance() = ChordsFragment()
        var timer = Timer()
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

        startTimer()

        positionA.setOnCheckedChangeListener { _, isChecked -> viewModel.updatePositionSet("Position A", isChecked) }
        positionB.setOnCheckedChangeListener { _, isChecked -> viewModel.updatePositionSet("Position B", isChecked) }

        min7.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("-7", isChecked) }
        dom7.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("7", isChecked) }
        maj7.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("maj7", isChecked) }
        min7b5.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("-7b5", isChecked) }
        maj7b9.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("7b9", isChecked) }
        aug7.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("7#5#9", isChecked) }
        dim7.setOnCheckedChangeListener { _, isChecked -> viewModel.updateExtensionSet("dim7", isChecked) }

        randomizedButton.setOnClickListener { generateChord() }
        timerCheckBox.setOnCheckedChangeListener { _, isChecked -> if (isChecked) startTimer() else stopTimer() }

        secondsInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                restartTimer()
            }
        })
    }

    private fun generateChord() {
        val chord = viewModel.getRandomChord()
        noteTextView.text = chord.note
        extensionTextView.text = chord.extension
        positionTextView.text = chord.position

        if (neverRepeat.isChecked) viewModel.removeChord(chord)
    }

    private fun startTimer() {
        if (secondsInput.text.isNullOrEmpty() || secondsInput.text.toString() == "0")
            return

        timer = Timer()
        val milliseconds = secondsInput.text.toString().toLong() * 1000
        timer.scheduleAtFixedRate(0, milliseconds) { generateChord() }
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun restartTimer() {
        if (!timerCheckBox.isChecked)
            return

        stopTimer()
        startTimer()
    }
}
