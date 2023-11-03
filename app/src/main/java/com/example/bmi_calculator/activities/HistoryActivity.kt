package com.example.bmi_calculator.activities

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.bmi_calculator.MyAdapter
import com.example.bmi_calculator.R
import com.example.bmi_calculator.getLastTenMeasurements
import com.example.bmi_calculator.readFile


class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.historyLV)

        val measurements = readFile(this)
        val lastTenMeasurements = getLastTenMeasurements(measurements)

        val adapter = MyAdapter(this, lastTenMeasurements)
        listView.adapter = adapter
    }
}
