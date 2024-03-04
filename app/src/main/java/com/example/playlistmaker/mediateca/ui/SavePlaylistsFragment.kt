package com.example.playlistmaker.mediateca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavePlaylistsFragment : Fragment() {

    private val savePlaylistViewModel by viewModel<SavePlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_save_playlists, container, false)
    }


    companion object {

        @JvmStatic
        fun newInstance() = SavePlaylistsFragment()

    }
}