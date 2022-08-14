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
        //editor.putString("newHelloText", "Hello Shared Preferences")
        editor.remove("newHelloText")
        //editor.clear()
        editor.apply()

        val textView = binding.textView
        textView.text = sharedPreference.getString("newHelloText", "Default text")
    }

/*    override fun onStart() {
        super.onStart()

        Log.d("Lifecycle method", "onStart()")
        textView.append("onStart() \n")
    }

    override fun onResume() {
        super.onResume()

        Log.d("Lifecycle method", "onResume()")
        textView.append("onResume() \n")
    }

    override fun onPause() {
        super.onPause()

        Log.d("Lifecycle method", "onPause()")
        textView.append("onPause() \n")
    }

    override fun onStop() {
        super.onStop()

        Log.d("Lifecycle method", "onStop()")
        textView.append("onStop() \n")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Lifecycle method", "onDestroy()")
        textView.append("onDestroy() \n")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("onSaveInstanceState", "onSaveInstanceState()")
        textView.append("onSaveInstanceState() \n")
        outState.putString("textToBundle", textView.text.toString())
    }*/
}