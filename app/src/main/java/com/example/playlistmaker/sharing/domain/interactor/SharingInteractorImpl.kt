package com.example.playlistmaker.sharing.domain.interactor


import com.example.playlistmaker.Creator
import com.example.playlistmaker.sharing.data.EmailData

class SharingInteractorImpl: SharingInteractor {

    private val externalNavigatorRepoImpl = Creator.createExternalNavigatorRepository()

    override fun shareApp(): String {
        return externalNavigatorRepoImpl.shareLink()

    }

    override fun userAgreement( ): String {
        return externalNavigatorRepoImpl.getLink()
    }

    override fun getEmailData(): EmailData {
        return externalNavigatorRepoImpl.getEmailData()
    }

}


