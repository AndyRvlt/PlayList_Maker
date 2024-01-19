package com.example.playlistmaker.sharing.data

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl(
    val context: Context
) : ExternalNavigator {

    override fun shareLink(): String {
        return context.getString(R.string.android_developer)
    }

    override fun getLink(): String {
        return context.getString(R.string.web)

    }

    override fun openEmail(): EmailData {
        return EmailData(
            context.getString(R.string.yandex_mail),
            context.getString(R.string.subjectMessage),
            context.getString(R.string.supportMessage)
        )
    }

}