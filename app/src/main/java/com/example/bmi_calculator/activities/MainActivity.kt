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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bmi_calculator.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        startObserving()

        setupCalculateBmiClickListener()
        setupCardViewResultClickListener()
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
                viewModel.currentMetric.value = getString(R.string.kg_and_cm)
                true
            }

            R.id.menu_pounds -> {
                viewModel.currentMetric.value = getString(R.string.lb_and_ft)
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

    private fun setupCardViewResultClickListener() {
        val cardViewResult = findViewById<CardView>(R.id.resultCV)
        cardViewResult.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("bmi", viewModel.currentBmiResult.value)
            intent.putExtra("type", viewModel.currentBmiType.value)
            intent.putExtra("color", viewModel.currentColor.value)
            startActivity(intent)
        }
    }

    private fun setupCalculateBmiClickListener() {
        val calculateButton = findViewById<Button>(R.id.button)

        calculateButton.setOnClickListener {
            val givenWeight = findViewById<EditText>(R.id.weightET)
            val givenHeight = findViewById<EditText>(R.id.heightET)

            val heightUnit = findViewById<TextView>(R.id.heightValueTV).text.toString()
            val weightUnit = findViewById<TextView>(R.id.weightValueTV).text.toString()
            val weight = givenWeight.text.toString()
            val height = givenHeight.text.toString()

            if (checkInputs(this, weight, height)) {
                val bmi = calculateBmiValue(this, weight, height, viewModel.currentMetric.value)
                setViewModelValues(bmi)
                val bmiResult = BMIResult(
                    bmi,
                    getCurrentDate(),
                    weight.toDouble(),
                    height.toDouble(),
                    weightUnit,
                    heightUnit
                )
                saveToFile(
                    this,
                    bmiResult
                )
            }
        }
    }

    private fun startObserving() {
        viewModel.currentMetric.observe(this, Observer {
            changeMetricView()
        })
        viewModel.currentColor.observe(this, Observer {
            changeCardView()
        })
        viewModel.currentBmiType.observe(this, Observer {
            changeBmiTypeView()
        })
        viewModel.currentBmiResult.observe(this, Observer {
            changeBmiResultView()
        })
    }

    // ok
    private fun changeMetricView() {
        val heightValueTV = findViewById<TextView>(R.id.heightValueTV)
        val weightValueTV = findViewById<TextView>(R.id.weightValueTV)

        val givenWeight = findViewById<EditText>(R.id.weightET)
        val givenHeight = findViewById<EditText>(R.id.heightET)

        givenWeight.setText("")
        givenHeight.setText("")

        if (viewModel.currentMetric.value == getString(R.string.lb_and_ft)) {
            heightValueTV.text = getString(R.string.ft)
            weightValueTV.text = getString(R.string.lb)
        } else {
            heightValueTV.text = getString(R.string.cm)
            weightValueTV.text = getString(R.string.kg)
        }
    }

    private fun changeCardView() {
        val cardViewResult = findViewById<CardView>(R.id.resultCV)
        viewModel.currentColor.value?.let { cardViewResult.setCardBackgroundColor(it) }
    }

    private fun changeBmiTypeView() {
        val type = findViewById<TextView>(R.id.descTV)
        type.text = viewModel.currentBmiType.value
    }

    private fun changeBmiResultView() {
        val result = findViewById<TextView>(R.id.resultTV)
        result.text = viewModel.currentBmiResult.value.toString()
    }

    @SuppressLint("ResourceAsColor")
    private fun setViewModelValues(bmi: Double) {
        viewModel.currentBmiResult.value = bmi
        viewModel.currentColor.value = getColor(this, bmi)
        viewModel.currentBmiType.value = getResult(this, bmi)
    }
}
