package com.example.playlistmaker.sharing.domain.interactor

import android.content.Context
import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorRepositoryImpl

class SharingInteractorImpl(

) : SharingInteractor {
    private val externalNavigatorRepoImpl = ExternalNavigatorRepositoryImpl()
    override fun shareApp(context: Context): String {
        return externalNavigatorRepoImpl.shareLink(context)

    }

    override fun userAgreement(context: Context): String {
        return externalNavigatorRepoImpl.getLink(context)
    }

    override fun getEmailData(context: Context): EmailData {
        return externalNavigatorRepoImpl.getEmailData(context)
    }

}


