package com.example.playlistmaker.sharing.data.repository

import android.content.Context
import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl : ExternalNavigatorRepository {
    private val externalNavigator = ExternalNavigatorImpl()

    override fun shareLink(context: Context): String {
        return externalNavigator.shareLink(context)
    }

    override fun getLink(context: Context): String {
        return externalNavigator.getLink(context)
    }

    override fun getEmailData(context: Context): EmailData {
        return externalNavigator.openEmail(context)
    }
}