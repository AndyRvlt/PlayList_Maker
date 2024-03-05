package com.example.playlistmaker.mediateca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSavePlaylistsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavePlaylistsFragment : Fragment() {
    private lateinit var binding: FragmentSavePlaylistsBinding

    private val savePlaylistViewModel by viewModel<SavePlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavePlaylistsBinding.inflate(inflater, container, false)
        return binding.root

    }


    companion object {

        fun newInstance() = SavePlaylistsFragment()

    }
}