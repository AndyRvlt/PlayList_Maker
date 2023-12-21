package com.example.playlistmaker.sharing.data

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl() : ExternalNavigator {
    override fun shareLink(context: Context): String {
        return context.getString(R.string.android_developer)
    }

    override fun getLink(context: Context): String {
        return context.getString(R.string.web)

    }

    override fun openEmail(context: Context): EmailData {
        return EmailData(
            context.getString(R.string.yandex_mail),
            context.getString(R.string.subjectMessage),
            context.getString(R.string.supportMessage)
        )
    }

}