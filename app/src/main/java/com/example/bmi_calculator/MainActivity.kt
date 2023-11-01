package com.example.bmi_calculator

import android.annotation.SuppressLint
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
import java.io.IOException
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private var currentColor: Int = R.color.light_violet
    private var currentMetric: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentMetric = getString(R.string.kg_and_cm)

        calculateBmi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

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

            if (checkInputs(weight, height)) {
                // todo this should be placed in another Class - it is more about logic
                var bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                if (currentMetric == getString(R.string.ft)) {
                    bmi *= 703
                }
                bmi = String.format("%.2f", bmi).toFloat()
                val date = getCurrentDate()
                saveToFile(bmi, "$weight ${weightValueTV.text}","$height ${heightValueTV.text}", date )
                displayResult(bmi)
            }
        }
    }

    // todo this also should be moved to other class
    private fun checkInputs(weight: String, height: String): Boolean {
        var correct = true
        if (weight.isEmpty()) {
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = getString(R.string.incorrect)
            description.text = getString(R.string.enter_weight)
            correct = false
        }
        if (height.isEmpty()) {
            val result = findViewById<TextView>(R.id.resultTV)
            val description = findViewById<TextView>(R.id.descTV)

            result.text = getString(R.string.incorrect)
            description.text = getString(R.string.enter_height)
            correct = false
        }
        return correct
    }

    // todo to other class
    private fun getResult(bmi: Float): String {
        return when {
            bmi < 18.5 -> getString(R.string.underweight)
            bmi in 18.5..24.99 -> getString(R.string.normal_weight)
            bmi in 24.99..29.99 -> getString(R.string.overweight)
            else -> getString(R.string.obesity)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun displayResult(bmi: Float) {
        val result = findViewById<TextView>(R.id.resultTV)
        val type = findViewById<TextView>(R.id.descTV)
        val cardViewResult = findViewById<CardView>(R.id.resultCV)

        result.text = bmi.toString()

        currentColor = getColor(this, bmi)
        val typeText = getResult(bmi)

        cardViewResult.setCardBackgroundColor(currentColor)
        type.text = typeText

        cardViewResult.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("bmi", result.text)
            intent.putExtra("type", type.text)
            startActivity(intent)
        }
    }

    // todo to other class
    private fun saveToFile(bmi: Float, weight: String, height: String, date: String) {
        val file = "history.txt"
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_APPEND)
            val lineToSave = "bmi: $bmi,weight: $weight,height: $height,date: $date \n"
            fileOutputStream.write(lineToSave.toByteArray())
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

// todo to other class
fun getColor(context: Context, bmi: Float): Int {
    return when {
        bmi < 18.5 -> ContextCompat.getColor(context, R.color.under_weight)
        bmi in 18.5..24.99 -> ContextCompat.getColor(context, R.color.normal_weight)
        bmi in 24.99..29.99 -> ContextCompat.getColor(context, R.color.over_weight)
        else -> ContextCompat.getColor(context, R.color.obesity)
    }
}

// todo to other class
fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Date()
    return sdf.format(currentDate)
}


