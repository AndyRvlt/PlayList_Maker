package com.example.playlistmaker.settings.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.interactor.ThemePreferencesInteractor
import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.domain.interactor.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val themePreferencesInteractor: ThemePreferencesInteractor,
) : ViewModel() {

    private var isDarkTheme = MutableLiveData(false)
    private var userLink = MutableLiveData("")
    private var emailData: MutableLiveData<EmailData?> = MutableLiveData(null)
    private var shareApp = MutableLiveData("")

    fun isDarkThemeLiveData(): LiveData<Boolean> = isDarkTheme
    fun userLinkLiveData(): LiveData<String> = userLink
    fun emailLiveData(): LiveData<EmailData?> = emailData
    fun shareAppLiveData(): LiveData<String> = shareApp

    fun init() {
        updateDarkTheme()
        getUserAgreementLink()
        getEmailData()
        shareApp()
    }

    private fun shareApp() {
        shareApp.postValue(sharingInteractor.shareApp())
    }

    private fun getEmailData() {
        emailData.postValue(sharingInteractor.getEmailData())
    }

    private fun updateDarkTheme() {
        isDarkTheme.postValue(themePreferencesInteractor.getTheme())
    }

    fun getUserAgreementLink() {
        userLink.postValue(sharingInteractor.userAgreement())
    }

//    companion object {
//        fun getViewModelFactory(): ViewModelProvider.Factory =
//            object : ViewModelProvider.Factory {
//
//                @Suppress("UNCHECKED_CAST")
//                override fun <T : ViewModel> create(
//                    modelClass: Class<T>,
//                    extras: CreationExtras,
//                ): T {
//                    return SettingsViewModel(
//                        SharingInteractorImpl(),
//                        ThemePreferencesInteractorImpl(),
//                    ) as T
//                }
//            }
//    }
}