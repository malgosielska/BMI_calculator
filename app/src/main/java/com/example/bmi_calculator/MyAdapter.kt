package com.example.bmi_calculator

import android.content.Context
import android.opengl.ETC1.getHeight
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class MyAdapter(context: Context, data: List<String>) :
    ArrayAdapter<String>(context, 0, data) {

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

        val item = getItem(position)

        bmi.text = getBmi(item)
        val currentColor = getColor(context, bmi.text.toString().toFloat())
        bmi.setTextColor(currentColor)

        dateRow.text = getDate(item)
        weightRow.text = getWeight(item)
        heightRow.text = getHeight(item)

        return convertView
    }
}
