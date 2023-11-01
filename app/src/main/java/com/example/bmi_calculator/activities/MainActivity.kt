package com.example.bmi_calculator.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.bmi_calculator.*


class MainActivity : AppCompatActivity() {
    private var currentColor: Int = R.color.light_violet
    private var currentMetric: String = ""

//    private var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentMetric = getString(R.string.kg_and_cm) //todo do view model

        calculateBmi() // todo i dont like the way it looks
    }

    // ok
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // ok
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_kg -> {
                currentMetric = getString(R.string.kg_and_cm)
                changeMetricView()
                true
            }

            R.id.menu_pounds -> {
                currentMetric = getString(R.string.lb_and_ft)
                changeMetricView()
                true
            }

            R.id.about_me -> {
                val intent = Intent(this, AboutMeActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeMetricView() {
        val heightValueTV = findViewById<TextView>(R.id.heightValueTV)
        val weightValueTV = findViewById<TextView>(R.id.weightValueTV)

        val givenWeight = findViewById<EditText>(R.id.weightET)
        val givenHeight = findViewById<EditText>(R.id.heightET)

        givenWeight.setText("")
        givenHeight.setText("")

        if (currentMetric == getString(R.string.lb_and_ft)) {
            heightValueTV.text = getString(R.string.ft)
            weightValueTV.text = getString(R.string.lb)
        } else {
            heightValueTV.text = getString(R.string.cm)
            weightValueTV.text = getString(R.string.kg)
        }
    }

    // todo cala ta metoda jest dziwna
    private fun calculateBmi() {
        val givenWeight = findViewById<EditText>(R.id.weightET)
        val givenHeight = findViewById<EditText>(R.id.heightET)
        val calculateButton = findViewById<Button>(R.id.button)
        val heightValueTV = findViewById<TextView>(R.id.heightValueTV)
        val weightValueTV = findViewById<TextView>(R.id.weightValueTV)

        val currentMetric = heightValueTV.text

        calculateButton.setOnClickListener {
            val weight = givenWeight.text.toString()
            val height = givenHeight.text.toString()

            if (checkInputs(this, weight, height)) {
                // todo this should be placed in another Class - it is more about logic
                var bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                if (currentMetric == getString(R.string.ft)) {
                    bmi *= 703
                }
                bmi = String.format("%.2f", bmi).toFloat()

                val date = getCurrentDate()
                saveToFile(
                    this,
                    bmi,
                    "$weight ${weightValueTV.text}",
                    "$height ${heightValueTV.text}",
                    date
                )
                displayResult(bmi)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun displayResult(bmi: Float) {
        val result = findViewById<TextView>(R.id.resultTV)
        val type = findViewById<TextView>(R.id.descTV)
        val cardViewResult = findViewById<CardView>(R.id.resultCV)

        result.text = bmi.toString() //todo to view model

        currentColor = getColor(this, bmi) //todo to view model
        val typeText = getResult(this, bmi)
        type.text = typeText //todo to view model

        cardViewResult.setCardBackgroundColor(currentColor)

        cardViewResult.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("bmi", result.text)
            intent.putExtra("type", type.text)
            intent.putExtra("color", currentColor)
            startActivity(intent)
        }
    }
}
