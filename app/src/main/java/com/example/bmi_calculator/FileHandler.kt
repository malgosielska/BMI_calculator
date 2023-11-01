package com.example.bmi_calculator

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader


fun saveToFile(context: Context, bmi: Double, weight: String, height: String, date: String) {
    val file = "history.txt"
    val fileOutputStream: FileOutputStream
    try {
        fileOutputStream = context.openFileOutput(file, Context.MODE_APPEND)
        val lineToSave = "bmi: $bmi, weight: $weight, height: $height, date: $date \n"
        fileOutputStream.write(lineToSave.toByteArray())
        fileOutputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun readFile(context: Context): List<String> {
    val fileInputStream: FileInputStream
    val lines = mutableListOf<String>()

    try {
        fileInputStream = context.openFileInput("history.txt")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text: String?

        while (bufferedReader.readLine().also { text = it } != null) {
            lines.add(text ?: "")
        }
        fileInputStream.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return lines
}
