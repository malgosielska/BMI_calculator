package com.example.bmi_calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var currentMetric = MutableLiveData<String>().apply {
        value = "kg and cm"
    }
    var currentColor = MutableLiveData<Int>()
    var currentBmiResult = MutableLiveData<Double>()
    var currentBmiType = MutableLiveData<String>()

}