package com.example.playlistmaker.sharing.data.repository

import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.domain.ExternalNavigator
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl(
    private val externalNavigator: ExternalNavigator
) : ExternalNavigatorRepository {

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