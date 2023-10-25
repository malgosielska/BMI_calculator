package com.example.bmi_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val givenWeight = findViewById<EditText>(R.id.weightET)
        val givenHeight = findViewById<EditText>(R.id.heightET)
        val calculateButton = findViewById<Button>(R.id.button)

        calculateButton.setOnClickListener {
            val weight = givenWeight.text.toString()
            val height = givenHeight.text.toString()

            if (checkInputs(weight, height)) {
                var bmi = weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
                bmi = String.format("%.2f", bmi).toFloat()
                displayResult(bmi)
            }
        }
    }



    private fun checkInputs(weight: String, height: String): Boolean {
        var correct = true
        if (weight.isEmpty()){
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = "Incorrect! "
            description.text = "Enter weight"
            correct = false
        }
        if (height.isEmpty()){
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = "Incorrect! "
            description.text = "Enter weight"
            correct = false
        }
        return correct
    }

    private fun displayResult(bmi: Float){
        val result = findViewById<TextView>(R.id.resultTV)
        val description = findViewById<TextView>(R.id.descTV)

        result.text = bmi.toString()

        var resultText = ""
        var color = 0

        when {
            bmi < 18.5 -> {
                resultText = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.5..24.99 -> {
                resultText = "Normal weight"
                color = R.color.normal_weight
            }
            bmi in 24.99..29.99 -> {
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99 -> {
                resultText = "Obesity"
                color = R.color.obesity
            }
        }

        description.setTextColor(ContextCompat.getColor(this, color))
        description.text = resultText

    }
}