package com.shevy.acsectioneleven

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.shevy.acsectioneleven.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView
    private var isTimerOn by Delegates.notNull<Boolean>()
    lateinit var timerSeekBar: SeekBar
    lateinit var button: Button
    lateinit var countDownTimer: CountDownTimer
    var defaultInterval by Delegates.notNull<Int>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Cool Timer"

        isTimerOn = false
        button = binding.startStop
        timerSeekBar = binding.seekBar
        textView = binding.timer
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        timerSeekBar.max = 600
        setIntervalFromSharedPreference(sharedPreferences)

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
                countDownTimer =
                    object : CountDownTimer(timerSeekBar.progress.toLong() * 1000, 1000) {
                        override fun onTick(p0: Long) {
                            updateTimer(p0)
                        }

                        override fun onFinish() {
                            val sharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(applicationContext)
                            if (sharedPreferences.getBoolean("enable_sound", true)) {
                                when (sharedPreferences.getString("timer_melody", "bell")) {
                                    "bell" -> MediaPlayer.create(applicationContext, R.raw.bell)
                                        .start()
                                    "kolokolchik" -> MediaPlayer.create(
                                        applicationContext,
                                        R.raw.kolokolchik
                                    ).start()
                                    "melody" -> MediaPlayer.create(applicationContext, R.raw.melody)
                                        .start()
                                }
                            }
                            resetTimer()
                        }
                    }
                countDownTimer.start()
            } else {
                resetTimer()
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
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

    private fun resetTimer() {
        countDownTimer.cancel()
        button.text = "Start"
        timerSeekBar.isEnabled = true
        isTimerOn = false
        setIntervalFromSharedPreference(sharedPreferences)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(
                    Intent(this, SettingsActivity::class.java)
                )
            }
            R.id.action_about
            -> {
                startActivity(
                    Intent(this, AboutActivity::class.java)
                )
            }
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setIntervalFromSharedPreference(sharedPreferences: SharedPreferences){
        defaultInterval = sharedPreferences.getString("default_interval", "30")!!.toInt()
        updateTimer(defaultInterval.toLong()*1000)
        timerSeekBar.progress = defaultInterval
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1 == "default_interval") {
            setIntervalFromSharedPreference(sharedPreferences)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}