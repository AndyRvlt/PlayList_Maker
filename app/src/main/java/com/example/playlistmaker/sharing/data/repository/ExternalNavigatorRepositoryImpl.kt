package com.example.playlistmaker.sharing.data.repository


import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl : ExternalNavigatorRepository {
    private val externalNavigator = ExternalNavigatorImpl()


    override fun shareLink(): String {
        return externalNavigator.shareLink()
    }

    override fun getLink(): String {
        return externalNavigator.getLink()
    }

    override fun getEmailData(): EmailData {
        return externalNavigator.openEmail()
    }
}