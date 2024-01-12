package com.example.playlistmaker.settings.UI

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.playlistmaker.settings.domain.interactor.ThemePreferencesInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemePreferencesInteractorImpl
import com.example.playlistmaker.sharing.data.EmailData
import com.example.playlistmaker.sharing.domain.interactor.SharingInteractor
import com.example.playlistmaker.sharing.domain.interactor.SharingInteractorImpl

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

    fun init(context: Context) {
        themePreferencesInteractor.init(context)
        updateDarkTheme()
        getUserAgreementLink(context)
        getEmailData(context)
        shareApp(context)
    }

    private fun shareApp(context: Context) {
        shareApp.postValue(sharingInteractor.shareApp(context))
    }

    private fun getEmailData(context: Context) {
        emailData.postValue(sharingInteractor.getEmailData(context))
    }

    private fun updateDarkTheme() {
        isDarkTheme.postValue(themePreferencesInteractor.getTheme())
    }

    fun getUserAgreementLink(context: Context) {
        userLink.postValue(sharingInteractor.userAgreement(context))
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    return SettingsViewModel(
                        SharingInteractorImpl(),
                        ThemePreferencesInteractorImpl(),
                    ) as T
                }
            }
    }
}