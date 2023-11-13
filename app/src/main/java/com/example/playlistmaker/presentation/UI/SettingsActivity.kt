package com.example.playlistmaker.presentation.UI

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.playlistmaker.APP_SETTINGS_DAY_NIGHT_THEMES
import com.example.playlistmaker.App
import com.example.playlistmaker.KEY_THEMES
import com.example.playlistmaker.R
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<TextView>(R.id.share)
        val buttonSupport = findViewById<TextView>(R.id.support)
        val buttonArrowBack = findViewById<Button>(R.id.arrowBack)
        val buttonUserAgreement = findViewById<TextView>(R.id.userAgreement)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)


        var preferencesDayNightThemes: SharedPreferences =
            getSharedPreferences(APP_SETTINGS_DAY_NIGHT_THEMES, MODE_PRIVATE)
        val cheked = preferencesDayNightThemes.getBoolean(KEY_THEMES, false)
        themeSwitcher.isChecked = cheked

        themeSwitcher.setOnCheckedChangeListener { _, checken ->
            (applicationContext as App).switchTheme(checken)
        }


        buttonArrowBack.setOnClickListener {
            finish()
        }
        buttonShare.setOnClickListener {
            val sharingSend = Intent(Intent.ACTION_SEND)
            sharingSend.setType("text/plain")
            sharingSend.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.android_developer)
            )
            startActivity(sharingSend)
        }
        buttonSupport.setOnClickListener {
            val supportSend = Intent(Intent.ACTION_SENDTO)
            supportSend.data = Uri.parse("mailto:")
            supportSend.putExtra(Intent.EXTRA_EMAIL, arrayListOf(getString(R.string.yandex_mail)))
            supportSend.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subjectMessage))
            supportSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.supportMessage))
            startActivity(supportSend)
        }
        buttonUserAgreement.setOnClickListener {
            val goToUserAgreement =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.web)))
            startActivity(goToUserAgreement)
        }
    }

}