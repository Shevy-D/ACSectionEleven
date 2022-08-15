package com.shevy.acsectioneleven

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.timer_preference)

        val sharedPreferences = preferenceScreen
            .sharedPreferences
        val preferenceScreen = preferenceScreen
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
    }

    private fun setPreferenceLabel(preference: Preference, value: String) {
        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(value)
            if (index >= 0) {
                preference.summary = preference.entries[index]
            }

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
}