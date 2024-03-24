package com.example.playlistmaker.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.App
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.sharing.data.EmailData
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val settingsViewModel by viewModel<SettingsViewModel>()

    private var userEgreement: String = ""
    private var emailData: EmailData? = null
    private var shareApp: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonShare = binding.share
        val buttonSupport = binding.support
        val buttonUserAgreement = binding.userAgreement
        val themeSwitcher = binding.themeSwitcher


        settingsViewModel.init()

        settingsViewModel.isDarkThemeLiveData().observe(viewLifecycleOwner) { isDark ->
            themeSwitcher.isChecked = isDark
        }
        themeSwitcher.setOnCheckedChangeListener { _, checken ->
            (requireActivity().applicationContext as App).switchTheme(checken) // доработать!!! не работает!
        }
        settingsViewModel.userLinkLiveData().observe(viewLifecycleOwner) {
            userEgreement = it
        }
        settingsViewModel.emailLiveData().observe(viewLifecycleOwner) {
            emailData = it
        }
        settingsViewModel.shareAppLiveData().observe(viewLifecycleOwner) {
            shareApp = it
        }


        buttonShare.setOnClickListener {
            val sharingSend = Intent(Intent.ACTION_SEND)
            sharingSend.setType("text/plain")
            sharingSend.putExtra(
                Intent.EXTRA_TEXT, shareApp
            )
            startActivity(sharingSend)
        }
        buttonSupport.setOnClickListener {
            val supportSend = Intent(Intent.ACTION_SENDTO)
            supportSend.data = Uri.parse("mailto:")
            emailData?.mail?.let {
                supportSend.putExtra(Intent.EXTRA_EMAIL, arrayListOf(it))
            }
            emailData?.subjectMessage?.let {
                supportSend.putExtra(Intent.EXTRA_SUBJECT, it)
            }
            emailData?.supportMessage?.let {
                supportSend.putExtra(Intent.EXTRA_TEXT, it)
            }
            startActivity(supportSend)
        }
        buttonUserAgreement.setOnClickListener {
            val goToUserAgreement =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(userEgreement)
                )
            startActivity(goToUserAgreement)
        }


    }
}