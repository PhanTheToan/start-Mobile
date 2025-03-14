package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.textView)

        // Số
        val button0: Button = findViewById(R.id.zero)
        val button1: Button = findViewById(R.id.one)
        val button2: Button = findViewById(R.id.tow)
        val button3: Button = findViewById(R.id.three)
        val button4: Button = findViewById(R.id.four)
        val button5: Button = findViewById(R.id.five)
        val button6: Button = findViewById(R.id.six)
        val button7: Button = findViewById(R.id.seven)
        val button8: Button = findViewById(R.id.eight)
        val button9: Button = findViewById(R.id.nice)

        // Các phép toán
        val buttonAdd: Button = findViewById(R.id.add)
        val buttonSubtract: Button = findViewById(R.id.subtract)
        val buttonMultiply: Button = findViewById(R.id.multiply)
        val buttonDivide: Button = findViewById(R.id.divide)
        val buttonEqual: Button = findViewById(R.id.equal)
        val buttonClear: Button = findViewById(R.id.c)
        val buttonClearEntry: Button = findViewById(R.id.ce)
        val buttonBackspace: Button = findViewById(R.id.bs)
        val buttonDecimal: Button = findViewById(R.id.dot)
        val buttonPositiveNegative: Button = findViewById(R.id.pos)

        // Thiết lập onClickListeners
        button0.setOnClickListener { appendNumber("0") }
        button1.setOnClickListener { appendNumber("1") }
        button2.setOnClickListener { appendNumber("2") }
        button3.setOnClickListener { appendNumber("3") }
        button4.setOnClickListener { appendNumber("4") }
        button5.setOnClickListener { appendNumber("5") }
        button6.setOnClickListener { appendNumber("6") }
        button7.setOnClickListener { appendNumber("7") }
        button8.setOnClickListener { appendNumber("8") }
        button9.setOnClickListener { appendNumber("9") }

        buttonAdd.setOnClickListener { performOperation("+") }
        buttonSubtract.setOnClickListener { performOperation("-") }
        buttonMultiply.setOnClickListener { performOperation("*") }
        buttonDivide.setOnClickListener { performOperation("/") }
        buttonEqual.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clearAll() }
        buttonClearEntry.setOnClickListener { clearEntry() }
        buttonBackspace.setOnClickListener { backspace() }
        buttonDecimal.setOnClickListener { appendDecimal() }
        buttonPositiveNegative.setOnClickListener { togglePositiveNegative() }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        updateResultTextView()
    }

    private fun appendDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            updateResultTextView()
        }
    }

    private fun togglePositiveNegative() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateResultTextView()
        }
    }

    private fun performOperation(operator: String) {
        try {
            operand1 = currentInput.toDouble()
            this.operator = operator
            currentInput = ""
            updateResultTextView()
        } catch (e: NumberFormatException) {
            resultTextView.text = "Error"
            clearAll()
        }
    }

    private fun calculateResult() {
        if (operand1 == null || operator == null || currentInput.isEmpty()) {
            resultTextView.text = "Error"
            return
        }

        try {
            val operand2 = currentInput.toDouble()
            val result = when (operator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> {
                    if (operand2 == 0.0) {
                        resultTextView.text = "Cannot divide by zero"
                        clearAll()
                        return
                    }
                    operand1!! / operand2
                }
                else -> 0.0
            }

            currentInput = result.toString()
            operand1 = null
            operator = null
            updateResultTextView()
        } catch (e: NumberFormatException) {
            resultTextView.text = "Error"
            clearAll()
        }
    }

    private fun clearAll() {
        currentInput = ""
        operand1 = null
        operator = null
        updateResultTextView()
    }

    private fun clearEntry() {
        currentInput = ""
        updateResultTextView()
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
            updateResultTextView()
        }
    }

    private fun updateResultTextView() {
        resultTextView.text = currentInput.ifEmpty { "0" }
    }
}
