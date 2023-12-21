package com.example.playlistmaker.settings.data.preferences


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.APP_SETTINGS_DAY_NIGHT_THEMES
import com.example.playlistmaker.KEY_THEMES
import com.example.playlistmaker.settings.domain.ThemesPreferences


class ThemesPreferencesImpl(context: Context) : ThemesPreferences {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(APP_SETTINGS_DAY_NIGHT_THEMES, AppCompatActivity.MODE_PRIVATE)


    override fun getTheme(): Boolean {
        return prefs.getBoolean(KEY_THEMES, false)
    }

    override fun putTheme(putThemePref: Boolean) {
        prefs.edit().putBoolean(KEY_THEMES, putThemePref).apply()
    }

}