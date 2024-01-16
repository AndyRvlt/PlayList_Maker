package com.example.playlistmaker.sharing.domain.interactor


import com.example.playlistmaker.sharing.data.EmailData

interface SharingInteractor {

    fun shareApp(): String

    fun userAgreement(): String

    fun getEmailData(): EmailData
}