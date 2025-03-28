package com.example.currency

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivityCurrency : AppCompatActivity() {
    private lateinit var exchangeRateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerSource: Spinner = findViewById(R.id.spinner)
        val spinnerTarget: Spinner = findViewById(R.id.spinner2)
        val editTextAmount: EditText = findViewById(R.id.editTextAmount)
        val buttonConvert: Button = findViewById(R.id.buttonConvert)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        exchangeRateText = findViewById(R.id.textView6)

        editTextAmount.setText("0")

        val adapterSource = ArrayAdapter.createFromResource(
            this, R.array.currency_from, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerSource.adapter = adapterSource

        val adapterTarget = ArrayAdapter.createFromResource(
            this, R.array.currency_to, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerTarget.adapter = adapterTarget

        spinnerSource.setSelection(adapterSource.getPosition("USD"))
        spinnerTarget.setSelection(adapterTarget.getPosition("VND"))

        spinnerSource.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateExchangeRate(spinnerSource.selectedItem.toString(), spinnerTarget.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTarget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateExchangeRate(spinnerSource.selectedItem.toString(), spinnerTarget.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        buttonConvert.setOnClickListener {
            convertCurrency()
        }

        updateExchangeRate("USD", "VND")
    }

    private val exchangeRates = mapOf(
        "USD" to mapOf("EUR" to 0.85, "GBP" to 0.73, "JPY" to 110.0, "VND" to 23185.0),
        "EUR" to mapOf("USD" to 1.18, "GBP" to 0.86, "JPY" to 130.0, "VND" to 27000.0),
        "GBP" to mapOf("USD" to 1.37, "EUR" to 1.16, "JPY" to 150.0, "VND" to 31000.0),
        "JPY" to mapOf("USD" to 0.0091, "EUR" to 0.0077, "GBP" to 0.0067, "VND" to 210.0),
        "VND" to mapOf("USD" to 0.000043, "EUR" to 0.000037, "GBP" to 0.000032, "JPY" to 0.0048)
    )

    private fun updateExchangeRate(sourceCurrency: String, targetCurrency: String) {
        val rate = exchangeRates[sourceCurrency]?.get(targetCurrency) ?: 1.0

        // Định dạng tỷ giá theo định dạng phù hợp
        val formattedRate = if (rate >= 1) {
            NumberFormat.getNumberInstance(Locale.US).apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 2
            }.format(rate)
        } else {
            rate.toString()
        }

        exchangeRateText.text = "1 $sourceCurrency = $formattedRate $targetCurrency"
    }

    private fun convertCurrency() {
        val editTextAmount: EditText = findViewById(R.id.editTextAmount)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val spinnerSource: Spinner = findViewById(R.id.spinner)
        val spinnerTarget: Spinner = findViewById(R.id.spinner2)

        val amount = editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
        val sourceCurrency = spinnerSource.selectedItem.toString()
        val targetCurrency = spinnerTarget.selectedItem.toString()

        val rate = exchangeRates[sourceCurrency]?.get(targetCurrency) ?: 1.0
        val result = amount * rate

        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.maximumFractionDigits = 2
        val formattedResult = formatter.format(result)

        textViewResult.text = formattedResult
    }
}
