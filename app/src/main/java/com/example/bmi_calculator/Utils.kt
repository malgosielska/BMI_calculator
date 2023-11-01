package com.example.bmi_calculator

import android.app.Activity
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.Date
import java.util.Locale


fun getColor(context: Context, bmi: Float): Int {
    return when {
        bmi < 18.5 -> ContextCompat.getColor(context, R.color.under_weight)
        bmi in 18.5..24.99 -> ContextCompat.getColor(context, R.color.normal_weight)
        bmi in 24.99..29.99 -> ContextCompat.getColor(context, R.color.over_weight)
        else -> ContextCompat.getColor(context, R.color.obesity)
    }
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Date()
    return sdf.format(currentDate)
}

fun getLastTenMeasurements(measurements: List<String>): List<String> {
    val reversedMeasurements = measurements.reversed()
    return if (reversedMeasurements.size >= 10) {
        reversedMeasurements.subList(0, 10)
    } else {
        reversedMeasurements
    }
}
fun getResult(context: Context, bmi: Float): String {
    return when {
        bmi < 18.5 -> context.getString(R.string.underweight)
        bmi in 18.5..24.99 -> context.getString(R.string.normal_weight)
        bmi in 24.99..29.99 -> context.getString(R.string.overweight)
        else -> context.getString(R.string.obesity)
    }
}

fun checkInputs(context: Context, weight: String, height: String): Boolean {
    val result = (context as Activity).findViewById<TextView>(R.id.resultTV)
    val description = context.findViewById<TextView>(R.id.descTV)

    if (height.isEmpty()) {
        result.text = context.getString(R.string.incorrect)
        description.text = context.getString(R.string.enter_height)
        return false
    }
    if (weight.isEmpty()) {
        result.text = context.getString(R.string.incorrect)
        description.text = context.getString(R.string.enter_weight)
        return false
    }
    return true
}




