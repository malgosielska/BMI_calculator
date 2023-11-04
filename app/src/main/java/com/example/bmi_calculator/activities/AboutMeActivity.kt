package com.example.bmi_calculator.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bmi_calculator.R
import com.example.bmi_calculator.factsAboutMe
import java.util.Random


class AboutMeActivity : AppCompatActivity() {

    private lateinit var btnSpin: Button
    private lateinit var ivWheel: ImageView
    var currentPosition: Float = 18.0F
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        btnSpin = findViewById<Button>(R.id.btnSpin)
        ivWheel = findViewById<ImageView>(R.id.ivWheel)
        val winnerTV = findViewById<TextView>(R.id.factTV)

        val random = Random()

        btnSpin.setOnClickListener {
            btnSpin.isEnabled = false
            val spin = random.nextInt(5) + 5

            val degrees = spin * 36

            timer = object : CountDownTimer((degrees * 20).toLong(), 1) {
                override fun onTick(l: Long) {
                    currentPosition = ivWheel.rotation + 2
                    ivWheel.rotation = currentPosition
                }

                override fun onFinish() {
                    btnSpin.isEnabled = true
                    val fact = getFact(currentPosition)
                    winnerTV.text = fact
                }
            }.start()
        }
    }

    fun getFact(position: Float): String {
        val winner = (position.toInt() / 36) % 10
        return factsAboutMe[winner]
    }

}