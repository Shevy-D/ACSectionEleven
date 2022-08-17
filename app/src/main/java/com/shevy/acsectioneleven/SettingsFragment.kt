package com.shevy.acsectioneleven

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.*
import java.lang.NumberFormatException


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener,
Preference.OnPreferenceChangeListener{
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.timer_preference)

        val sharedPreferences = preferenceScreen.sharedPreferences
        val count = preferenceScreen.preferenceCount

        for (i in 0 until count) {
            val preference = preferenceScreen.getPreference(i)
            if (preference !is CheckBoxPreference) {
                val value = sharedPreferences.getString(preference.key, "")
                if (value != null) {
                    setPreferenceLabel(preference, value)
                }
            }
        }

        val preference = findPreference("default_interval")
        preference.onPreferenceChangeListener = this
    }

    private fun setPreferenceLabel(preference: Preference, value: String) {
        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(value)
            if (index >= 0) {
                preference.summary = preference.entries[index]
            }
        } else if (preference is EditTextPreference) {
            preference.summary = value
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val preference = findPreference(p1)
        if (preference !is CheckBoxPreference) {
            val value = preferenceScreen.sharedPreferences.getString(preference.key, "")
            if (value != null) {
                setPreferenceLabel(preference, value)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (preference?.key == "default_interval") {
            val defaultIntervalString = newValue.toString()

            try {
                val defaultInterval = Integer.parseInt(defaultIntervalString)
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Please enter an integer number", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }
}