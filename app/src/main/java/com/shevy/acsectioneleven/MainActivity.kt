package com.shevy.acsectioneleven

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import com.shevy.acsectioneleven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference: SharedPreferences = this.getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("newHelloText", "Hello Shared Preferences")
        //editor.remove("newHelloText")
        //editor.clear()
        editor.apply()

        val textView = binding.textView
        textView.text = sharedPreference.getString("newHelloText", "Default text")
    }
}