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

        // Pobieramy przekazane dane
        val bmi = intent.getStringExtra("bmi")
        val type = intent.getStringExtra("type")
        if (bmi != null && type != null) {
            display(bmi, type)
        }

        val calculateButton = findViewById<Button>(R.id.returnB)

        calculateButton.setOnClickListener {
            finish()
        }

    }

    private fun display(bmi: String, type: String){
        val bmiText = findViewById<TextView>(R.id.bmiScoreTV)
        val typeText = findViewById<TextView>(R.id.typeTV)
        val descriptionCardView = findViewById<CardView>(R.id.descriptionCV)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTV)

        bmiText.text = bmi
        typeText.text = type
        val color = getColor(bmi.toFloat())
        descriptionCardView.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }




}