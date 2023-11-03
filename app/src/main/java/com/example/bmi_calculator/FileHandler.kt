package com.example.bmi_calculator

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader


fun saveToFile(context: Context, bmiResult: BMIResult) {
    val file = "history.txt"
    val fileOutputStream: FileOutputStream
    try {
        fileOutputStream = context.openFileOutput(file, Context.MODE_APPEND)
        fileOutputStream.write(bmiResult.toString().toByteArray())
        fileOutputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun readFile(context: Context): List<BMIResult> {
    val fileInputStream: FileInputStream
    val results = mutableListOf<BMIResult>()

    try {
        fileInputStream = context.openFileInput("history.txt")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text: String?

        while (bufferedReader.readLine().also { text = it } != null) {
            val result = parseBMIResult(text ?: "")
            results.add(result)
        }
        fileInputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return results
}

fun parseBMIResult(input: String): BMIResult {
    val parts = input.split(", ")
    if (parts.size == 4) {
        val bmiPart = parts[0].split(" ")
        val bmi = bmiPart[1].toDouble()

        val weightPart = parts[1].split(" ")
        val weight = weightPart[1].toDouble()
        val weightMetric = weightPart[2]

        val heightPart = parts[2].split(" ")
        val height = heightPart[1].toDouble()
        val heightMetric = heightPart[2]

        val datePart = parts[3].split(" ")
        val date = datePart[1]

        return BMIResult(
            value = bmi,
            date = date,
            weight = weight,
            height = height,
            weightMetric = weightMetric,
            heightMetric = heightMetric
        )
    } else {
        throw IllegalArgumentException("Error")
    }
}


