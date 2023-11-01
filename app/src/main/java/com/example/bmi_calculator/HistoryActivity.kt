package com.example.bmi_calculator

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader


class HistoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.historyLV)

        val listData = readFile()
        // inaczej to wyswietlac trzeba
        val adapter = MyAdapter(this, listData)
        listView.adapter = adapter

    }

    // todo to other class
    private fun readFile(): List<String> {
        val fileInputStream: FileInputStream
        val lines = mutableListOf<String>()

        try {
            fileInputStream = openFileInput("history.txt")
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
        // todo do osobnej metody
        lines.reverse()
        return if (lines.size >= 10) {
            lines.subList(0, 10)
        } else {
            lines
        }
    }

}
