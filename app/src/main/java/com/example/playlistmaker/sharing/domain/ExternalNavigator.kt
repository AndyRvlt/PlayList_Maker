package com.example.playlistmaker.sharing.domain

import com.example.playlistmaker.sharing.data.EmailData

interface ExternalNavigator {

    fun shareLink(): String

    fun getLink(): String

    fun openEmail(): EmailData
}