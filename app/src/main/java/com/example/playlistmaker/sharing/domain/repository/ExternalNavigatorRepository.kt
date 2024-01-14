package com.example.playlistmaker.sharing.domain.repository


import com.example.playlistmaker.sharing.data.EmailData

interface ExternalNavigatorRepository {

    fun shareLink(): String

    fun getLink(): String

    fun getEmailData(): EmailData
}