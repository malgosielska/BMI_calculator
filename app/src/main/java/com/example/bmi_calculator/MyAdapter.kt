package com.example.bmi_calculator

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(context: Context, data: List<BMIResult>) :
    ArrayAdapter<BMIResult>(context, 0, data) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.row, parent, false)
        }

        val bmi = convertView!!.findViewById<TextView>(R.id.bmiScoreRow)
        val dateRow = convertView.findViewById<TextView>(R.id.dateRow)
        val weightRow = convertView.findViewById<TextView>(R.id.weightRow)
        val heightRow = convertView.findViewById<TextView>(R.id.heightRow)

        val bmiResult = getItem(position)

        if (bmiResult != null) {
            bmi.text = bmiResult.value.toString()
            val currentColor = getColor(context, bmi.text.toString().toDouble())
            bmi.setTextColor(currentColor)

            dateRow.text = "Date: " + bmiResult.date
            weightRow.text = "Weight: " + bmiResult.weight.toString() + " " + bmiResult.weightMetric
            heightRow.text = "Height: " + bmiResult.height.toString() + " " + bmiResult.heightMetric
        }
        return convertView
    }
}
