package com.example.bmi_calculator

import androidx.lifecycle.ViewModel

class BMIHistory: ViewModel() {

    private val bmiHistory = mutableListOf<BMIResult>()

    fun getBMIHistory(): List<BMIResult> {
        return bmiHistory
    }

    fun addResult(entry: BMIResult) {
        bmiHistory.add(entry)
    }
}