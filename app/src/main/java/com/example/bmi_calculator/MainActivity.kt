package com.example.bmi_calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateBmi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun calculateBmi() {
        val givenWeight = findViewById<EditText>(R.id.weightET)
        val givenHeight = findViewById<EditText>(R.id.heightET)
        val calculateButton = findViewById<Button>(R.id.button)

        calculateButton.setOnClickListener {
            val weight = givenWeight.text.toString()
            val height = givenHeight.text.toString()

            if (checkInputs(weight, height)) {
                var bmi = weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
                bmi = String.format("%.2f", bmi).toFloat()
                val date = getCurrentDate()
                val resultString = "DATE: $date     BMI VALUE: $bmi\n"
                saveToFile(resultString)

                displayResult(bmi)
            }
        }
    }

    private fun checkInputs(weight: String, height: String): Boolean {
        var correct = true
        if (weight.isEmpty()){
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = getString(R.string.incorrect)
            description.text = getString(R.string.enter_weight)
            correct = false
        }
        if (height.isEmpty()){
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = getString(R.string.incorrect)
            description.text = getString(R.string.enter_height)
            correct = false
        }
        return correct
    }

    private fun getResult(bmi: Float): String {
        return when {
            bmi < 18.5 -> getString(R.string.underweight)
            bmi in 18.5..24.99 -> getString(R.string.normal_weight)
            bmi in 24.99..29.99 -> getString(R.string.overweight)
            else -> getString(R.string.obesity)
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

    private fun saveToFile(bmi: String) {
        val file = "bmi_results.txt"
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_APPEND)
            fileOutputStream.write(bmi.toByteArray())
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception){
            e.printStackTrace()
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

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Date()
    return sdf.format(currentDate)
}