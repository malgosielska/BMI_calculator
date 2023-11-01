package com.example.bmi_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val bmi = intent.getStringExtra("bmi")
        val type = intent.getStringExtra("type")
        if (bmi != null && type != null) {
            display(bmi, type)
        }
    }

    private fun display(bmi: String, type: String) {
        val bmiText = findViewById<TextView>(R.id.bmiScoreTV)
        val typeText = findViewById<TextView>(R.id.typeTV)
        val descriptionCardView = findViewById<CardView>(R.id.descriptionCV)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTV)

        bmiText.text = bmi
        typeText.text = type
        descriptionTextView.text = getDescription(type)
        val color = getColor(this, bmi.toFloat())
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