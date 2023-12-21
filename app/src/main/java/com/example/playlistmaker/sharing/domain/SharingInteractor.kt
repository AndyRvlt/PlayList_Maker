package com.example.playlistmaker.sharing.domain

import android.content.Context
import com.example.playlistmaker.sharing.data.EmailData

interface SharingInteractor {

    fun shareApp(context: Context): String

    fun userAgreement(context: Context): String

    fun getEmailData(context: Context): EmailData
}