package com.shevy.acsectioneleven

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shevy.acsectioneleven.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView
    var isTimerOn by Delegates.notNull<Boolean>()
    lateinit var timerSeekBar: SeekBar
    lateinit var button: Button
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isTimerOn = false
        button = binding.startStop
        timerSeekBar = binding.seekBar
        textView = binding.timer

        timerSeekBar.max = 600
        timerSeekBar.progress = 60

        updateTimer(timerSeekBar.progress.toLong() * 1000)

        timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                updateTimer(progress.toLong() * 1000)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.startStop.setOnClickListener {
            if (!isTimerOn) {
                button.text = "Stop"
                timerSeekBar.isEnabled = false
                isTimerOn = true
                countDownTimer = object : CountDownTimer(timerSeekBar.progress.toLong() * 1000, 1000) {
                    override fun onTick(p0: Long) {
                        updateTimer(p0)
                    }

                    override fun onFinish() {
                        Log.d("myTimer", "Finish!")
                        MediaPlayer.create(applicationContext, R.raw.music).start()

                    }
                }
                countDownTimer.start()
            } else {
                countDownTimer.cancel()
                button.text = "Start"
                timerSeekBar.isEnabled = true
                isTimerOn = false
                //textView.text = "01:00"
                //timerSeekBar.progress = 60
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTimer(millisUntilFinished: Long) {
        val minutes = millisUntilFinished.toInt() / 1000 / 60
        val seconds = millisUntilFinished.toInt() / 1000 - (minutes * 60)

        val minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        val secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString()
        }
        textView.text = "$minutesString:$secondsString"
    }
}