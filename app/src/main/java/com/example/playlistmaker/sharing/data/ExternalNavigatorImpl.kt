package com.example.playlistmaker.sharing.data


import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl : ExternalNavigator {

    private val contextProvider = Creator.createContextProvider()

    override fun shareLink(): String {
        return contextProvider.getString(R.string.android_developer)
    }

    override fun getLink(): String {

        return contextProvider.getString(R.string.web)

    }

    override fun openEmail(): EmailData {
        return EmailData(
            contextProvider.getString(R.string.yandex_mail),
            contextProvider.getString(R.string.subjectMessage),
            contextProvider.getString(R.string.supportMessage)
        )
    }

}