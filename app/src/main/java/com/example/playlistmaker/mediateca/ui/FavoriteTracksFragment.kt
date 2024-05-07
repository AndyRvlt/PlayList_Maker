package com.example.playlistmaker.mediateca.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker.player.UI.AudioPlayerActivity
import com.example.playlistmaker.player.UI.TRACK_SERIALIZABLE
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.ui.SearchFragment
import com.example.playlistmaker.search.ui.TracksAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteTracksFragment : Fragment(), TracksAdapter.TrackListener {

    private lateinit var binding: FragmentFavoriteTracksBinding

    private val favoriteTracksViewModel by viewModel<FavoriteTracksViewModel>()
    private val adapterFavoriteTracks = TracksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.favoritePlayList.adapter = adapterFavoriteTracks

        favoriteTracksViewModel.getFavoritesTracksFromDb()
            .observe(viewLifecycleOwner) {

                adapterFavoriteTracks.updateTracks(it)
                if (it.isEmpty() ) {
                    binding.imageFavoriteTracksNon.isVisible = true
                    binding.textFavoriteTracksNon.isVisible = true
                    binding.favoritePlayList.isVisible = false
                } else {
                    binding.imageFavoriteTracksNon.isVisible = false
                    binding.textFavoriteTracksNon.isVisible = false
                    binding.favoritePlayList.isVisible = true
                }
            }
    }

    override fun onResume() {
        super.onResume()
        favoriteTracksViewModel.getFavoritesTracks()

    }
    companion object {

        fun newInstance() = FavoriteTracksFragment()

    }

    private var isClickAllowed = true

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(SearchFragment.CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }

        }
        return current
    }

    override fun onClick(track: Track) {
        if (clickDebounce()) {
            val displayAudioPlayer =
                Intent(requireContext(), AudioPlayerActivity::class.java).apply {
                    putExtra(TRACK_SERIALIZABLE, track)
                }
            startActivity(displayAudioPlayer)

        }
    }
}