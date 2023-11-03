package com.example.bmi_calculator

class BMIResult(
    val value: Double,
    val date: String,
    val weight: Double,
    val height: Double,
    val weightMetric: String,
    val heightMetric: String
) {

    override fun toString(): String {
        return "bmi: $value, weight: $weight $weightMetric, height: $height $heightMetric, date: $date\n"
    }
}