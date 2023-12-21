package com.example.playlistmaker.sharing.domain

import android.content.Context
import com.example.playlistmaker.sharing.data.EmailData

interface ExternalNavigator {

    fun shareLink(context: Context): String

    fun getLink(context: Context): String

    fun openEmail(context: Context): EmailData
}