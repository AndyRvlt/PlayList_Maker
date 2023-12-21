package com.example.playlistmaker.sharing.data.repository

import android.content.Context
import com.example.playlistmaker.sharing.data.EmailData

interface ExternalNavigatorRepository {

    fun shareLink(context: Context): String

    fun getLink(context: Context): String

    fun getEmailData(context: Context): EmailData
}