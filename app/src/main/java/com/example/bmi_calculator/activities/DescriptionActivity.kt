package com.example.bmi_calculator.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.bmi_calculator.R

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val bmi = intent.getDoubleExtra("bmi", 0.0)
        val type = intent.getStringExtra("type")
        val color = intent.getIntExtra("color", ContextCompat.getColor(this, R.color.light_violet))
        if (type != null) {
            display(bmi, type, color)
        }
    }

    private fun display(bmi: Double, type: String, color: Int) {
        val bmiText = findViewById<TextView>(R.id.bmiScoreTV)
        val typeText = findViewById<TextView>(R.id.typeTV)
        val descriptionCardView = findViewById<CardView>(R.id.descriptionCV)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTV)

        bmiText.text = bmi.toString()
        typeText.text = type
        descriptionTextView.text = getDescription(type)
        descriptionCardView.setCardBackgroundColor(color)
    }

    private fun getDescription(type: String): String {
        var desc = ""
        when (type) {
            getString(R.string.underweight) -> {
                desc = getString(R.string.underweight_description)
            }

            getString(R.string.overweight) -> {
                desc = getString(R.string.overweight_description)
            }

            getString(R.string.normal_weight) -> {
                desc = getString(R.string.normal_weight_description)
            }

            getString(R.string.obesity) -> {
                desc = getString(R.string.obesity_description)
            }
        }
        return desc
    }

}