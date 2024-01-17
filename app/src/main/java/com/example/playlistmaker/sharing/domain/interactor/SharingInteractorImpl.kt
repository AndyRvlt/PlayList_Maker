package com.example.playlistmaker.sharing.domain.interactor

import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository

class SharingInteractorImpl(
    private val externalNavigatorRepoImpl: ExternalNavigatorRepository
): SharingInteractor {

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


