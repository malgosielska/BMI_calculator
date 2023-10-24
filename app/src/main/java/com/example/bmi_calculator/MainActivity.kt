package com.example.bmi_calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
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
            description.text = "Enter height"
            correct = false
        }
        return correct
    }

    private fun getResult(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.99 -> "Normal weight"
            bmi in 24.99..29.99 -> "Overweight"
            else -> "Obesity"
        }
    }

    private fun displayResult(bmi: Float) {
        val result = findViewById<TextView>(R.id.resultTV)
        val type = findViewById<TextView>(R.id.descTV)
        val cardViewResult = findViewById<CardView>(R.id.resultCV)

        result.text = bmi.toString()

        val color = getColor(bmi)
        val typeText = getResult(bmi)

        cardViewResult.setCardBackgroundColor(ContextCompat.getColor(this, color))
        type.text = typeText

        cardViewResult.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("bmi", result.text)
            intent.putExtra("type", type.text)
            startActivity(intent)
        }
    }
}

fun getColor(bmi: Float): Int {
    return when {
        bmi < 18.5 -> R.color.under_weight
        bmi in 18.5..24.99 -> R.color.normal_weight
        bmi in 24.99..29.99 -> R.color.over_weight
        else -> R.color.obesity
    }
}