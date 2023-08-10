package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<TextView>(R.id.share)
        val buttonSupport = findViewById<TextView>(R.id.support)
        val subjectMessage = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
        val supportMessage = "Спасибо разработчикам и разработчицам за крутое приложение!"
        val buttonArrowBack = findViewById<Button>(R.id.arrowBack)
        val buttonUserAgreement = findViewById<TextView>(R.id.userAgreement)
        buttonArrowBack.setOnClickListener {
            finish()
        }
        buttonShare.setOnClickListener {
            val sharingSend = Intent(Intent.ACTION_SEND)
            sharingSend.setType("text/plain")
            sharingSend.putExtra(
                Intent.EXTRA_TEXT,
                "https://practicum.yandex.ru/android-developer/"
            )
            startActivity(sharingSend)
        }
        buttonSupport.setOnClickListener {
            val supportSend = Intent(Intent.ACTION_SENDTO)
            supportSend.data = Uri.parse("mailto:")
            supportSend.putExtra(Intent.EXTRA_EMAIL, arrayListOf("AndyRevolt@yandex.ru"))
            supportSend.putExtra(Intent.EXTRA_SUBJECT, subjectMessage)
            supportSend.putExtra(Intent.EXTRA_TEXT, supportMessage)
            startActivity(supportSend)
        }
        buttonUserAgreement.setOnClickListener {
            val goToUserAgreement =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/legal/practicum_offer/"))
            startActivity(goToUserAgreement)
        }
    }
}