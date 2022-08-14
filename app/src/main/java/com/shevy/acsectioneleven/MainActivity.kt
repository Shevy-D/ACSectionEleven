package com.shevy.acsectioneleven

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shevy.acsectioneleven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myTimer: CountDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                Log.d("myTimer", "${p0/1000} seconds left")
            }

            override fun onFinish() {
                Log.d("myTimer", "Finish!")
            }
        }
        myTimer.start()

/*        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                Log.d("Runnable", "Two seconds are passed")
                handler.postDelayed(this, 2000)
            }
        }
        handler.post(runnable)*/
    }
}