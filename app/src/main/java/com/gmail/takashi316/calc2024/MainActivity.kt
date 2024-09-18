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
    private var firstOperand = ""
    private var operator = ""
    private var isSecondOperand = false

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

        // Set click listeners for operator buttons
        setOperatorButtonListeners()

        // Set click listener for the plus-minus toggle button
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            toggleSign()
        }

        // Set click listener for the AC button
        findViewById<Button>(R.id.btnAC).setOnClickListener {
            resetInput()
        }

        // Set click listener for the equals button
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult()
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
        // Check if we are entering the second operand
        if (isSecondOperand) {
            currentInput += number
        } else {
            // If first operand, append to current input or replace if it's just "0"
            if (currentInput == "0") {
                currentInput = number
            } else {
                currentInput += number
            }
        }
        // Update the display TextView with the current input
        displayTextView.text = currentInput
    }

    private fun setOperatorButtonListeners() {
        val operatorIds = listOf(
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide
        )

        operatorIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                onOperatorButtonClick((it as Button).text.toString())
            }
        }
    }

    private fun onOperatorButtonClick(op: String) {
        // Set the first operand and operator when an operator button is clicked
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput
            operator = op
            currentInput = "" // Clear input for second operand
            isSecondOperand = true
        }
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
        // Reset the current input, operands, and operator to the initial state
        currentInput = "0"
        firstOperand = ""
        operator = ""
        isSecondOperand = false
        // Update the display TextView to show the initial state
        displayTextView.text = currentInput
    }

    private fun calculateResult() {
        // Perform the calculation based on the first operand, operator, and second operand
        if (firstOperand.isNotEmpty() && operator.isNotEmpty() && currentInput.isNotEmpty()) {
            val first = firstOperand.toDouble()
            val second = currentInput.toDouble()
            val result = when (operator) {
                "+" -> first + second
                "-" -> first - second
                "*" -> first * second
                "/" -> {
                    if (second == 0.0) {
                        displayTextView.text = "Error" // Handle division by zero
                        return
                    } else {
                        first / second
                    }
                }
                else -> return
            }
            // Display the result and reset the operation state
            currentInput = result.toString()
            displayTextView.text = currentInput
            firstOperand = ""
            operator = ""
            isSecondOperand = false
        }
    }
}
