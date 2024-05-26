package com.example.playlistmaker.mediateca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSavePlaylistsBinding
import com.example.playlistmaker.db.domain.models.PlayList
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistsFragment : Fragment() {

    private lateinit var binding: FragmentSavePlaylistsBinding

    private val savePlaylistViewModel by viewModel<PlaylistViewModel>()

    private val adapterPlaylists = PlaylistAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavePlaylistsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val playListRecyclerView = binding.playlistRecyclerView

        playListRecyclerView.layoutManager = GridLayoutManager(context, 2)

        playListRecyclerView.adapter = adapterPlaylists

        savePlaylistViewModel.getPlaylistsFromDb().observe(viewLifecycleOwner) {
            adapterPlaylists.updatePlaylist(it)
            if (it.isEmpty()) {
                binding.createNewPlaylist.isVisible = true
                binding.imageView.isVisible = true
                binding.textView.isVisible = true
                binding.playlistRecyclerView.isVisible = false
            } else {
                binding.createNewPlaylist.isVisible = true
                binding.imageView.isVisible = false
                binding.textView.isVisible = false
                binding.playlistRecyclerView.isVisible = true

            }

        }



        binding.createNewPlaylist.setOnClickListener {


            findNavController().navigate(R.id.action_mediaFragment_to_newPlayListFragment)
        }


    }

    override fun onResume() {
        super.onResume()
        savePlaylistViewModel.getPlaylists()
    }


    companion object {

        fun newInstance() = PlaylistsFragment()

    }

}
