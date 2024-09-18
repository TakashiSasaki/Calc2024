package com.gmail.takashi316.calc2024

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the TextView for displaying input and results
        displayTextView = findViewById(R.id.tvDisplay)

        // Set click listeners for number buttons
        setNumberButtonListeners()

        // Set click listener for the plus-minus toggle button
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            toggleSign()
        }

        // Set click listener for the AC button
        findViewById<Button>(R.id.btnAC).setOnClickListener {
            resetInput()
        }
    }

    private fun setNumberButtonListeners() {
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                onNumberButtonClick((it as Button).text.toString())
            }
        }
    }

    private fun onNumberButtonClick(number: String) {
        // Append the clicked number to the current input
        if (currentInput == "0") {
            currentInput = number // Replace "0" with the new number
        } else {
            currentInput += number
        }
        // Update the display TextView with the current input
        displayTextView.text = currentInput
    }

    private fun toggleSign() {
        // Toggle the sign of the current input
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1) // Remove the negative sign
            } else {
                "-$currentInput" // Add the negative sign
            }
            // Update the display TextView with the toggled input
            displayTextView.text = currentInput
        }
    }

    private fun resetInput() {
        // Reset the current input to the initial state
        currentInput = "0"
        // Update the display TextView to show the initial state
        displayTextView.text = currentInput
    }
}
