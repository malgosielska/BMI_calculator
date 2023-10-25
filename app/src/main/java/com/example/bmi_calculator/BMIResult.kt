package com.example.bmi_calculator

data class BMIResult(val date: String, val bmiResult: Float) {
    override fun toString(): String {
        return "Date: $date, BMI: $bmiResult"
    }
}