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

        var timer = Timer()

        fun generateChord() {
            chordTextView.text = viewModel.generateNote()
            extensionTextView.text = viewModel.generateExtension()
            positionTextView.text = viewModel.generatePosition()
        }

        fun startTimer() {
            if (secondsInput.text.isNullOrEmpty() || secondsInput.text.toString() == "0")
                return

            timer = Timer()
            val milliseconds = secondsInput.text.toString().toLong() * 1000
            timer.scheduleAtFixedRate(0, milliseconds) { generateChord() }
        }

        fun stopTimer() {
            timer?.cancel()
        }

        fun restartTimer() {
            if (!timerCheckBox.isChecked)
                return

            stopTimer()
            startTimer()
        }

        positionA.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.positions.add("Position A")
            else viewModel.positions.remove("Position A")
        }

        positionB.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.positions.add("Position B")
            else viewModel.positions.remove("Position B")
        }

        min7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("-7")
            else viewModel.extensions.remove("-7")
        }

        dom7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("7")
            else viewModel.extensions.remove("7")
        }

        maj7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("maj7")
            else viewModel.extensions.remove("maj7")
        }

        min7b5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("-7b5")
            else viewModel.extensions.remove("-7b5")
        }

        maj7b9.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("7b9")
            else viewModel.extensions.remove("7b9")
        }

        aug7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("7#5#9")
            else viewModel.extensions.remove("7#5#9")
        }

        dim7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.extensions.add("dim7")
            else viewModel.extensions.remove("dim7")
        }

        randomizedButton.setOnClickListener {
            generateChord()
        }

        timerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) startTimer() else stopTimer()
        }

        secondsInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                restartTimer()
            }
        })

        startTimer()
    }
}
