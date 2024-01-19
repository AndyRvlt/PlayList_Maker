package com.example.playlistmaker.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.EmailData
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    private var userEgreement: String = ""
    private var emailData: EmailData? = null
    private var shareApp: String = ""

    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<TextView>(R.id.share)
        val buttonSupport = findViewById<TextView>(R.id.support)
        val buttonArrowBack = findViewById<Button>(R.id.arrowBack)
        val buttonUserAgreement = findViewById<TextView>(R.id.userAgreement)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)

        settingsViewModel.init()

        settingsViewModel.isDarkThemeLiveData().observe(this) { isDark ->
            themeSwitcher.isChecked = isDark
        }
        themeSwitcher.setOnCheckedChangeListener { _, checken ->
            (applicationContext as App).switchTheme(checken)
        }
        settingsViewModel.userLinkLiveData().observe(this) {
            userEgreement = it
        }
        settingsViewModel.emailLiveData().observe(this) {
            emailData = it
        }
        settingsViewModel.shareAppLiveData().observe(this) {
            shareApp = it
        }

        buttonArrowBack.setOnClickListener {
            finish()
        }
        buttonShare.setOnClickListener {
            val sharingSend = Intent(Intent.ACTION_SEND)
            sharingSend.setType("text/plain")
            sharingSend.putExtra(
                Intent.EXTRA_TEXT, shareApp
            )
            startActivity(sharingSend)
        }
        buttonSupport.setOnClickListener {
            val supportSend = Intent(Intent.ACTION_SENDTO)
            supportSend.data = Uri.parse("mailto:")
            emailData?.mail?.let {
                supportSend.putExtra(Intent.EXTRA_EMAIL, arrayListOf(it))
            }
            emailData?.subjectMessage?.let {
                supportSend.putExtra(Intent.EXTRA_SUBJECT, it)
            }
            emailData?.supportMessage?.let {
                supportSend.putExtra(Intent.EXTRA_TEXT, it)
            }
            startActivity(supportSend)
        }
        buttonUserAgreement.setOnClickListener {
            val goToUserAgreement =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(userEgreement)
                )
            startActivity(goToUserAgreement)
        }
    }

}