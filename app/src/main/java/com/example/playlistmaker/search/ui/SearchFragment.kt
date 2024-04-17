package com.example.playlistmaker.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.player.UI.AudioPlayerActivity
import com.example.playlistmaker.player.UI.TRACK_SERIALIZABLE
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), TracksAdapter.TrackListener {

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var binding: FragmentSearchBinding


    private val adapter = TracksAdapter(this)
    private val adapterHistory = TracksAdapter(this)

    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText = savedInstanceState?.getString(SEARCH_TEXT, "").orEmpty()

        searchViewModel.init()

        binding.storyTrack.clearHistorySearch.setOnClickListener {
            cleanHistory()
        }

        binding.storyTrack.historySearch.adapter = adapterHistory
        binding.searchPlayList.adapter = adapter

        binding.update.setOnClickListener {
            requestTrackList()
        }

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requestTrackList()
            }
            false
        }

        binding.clearIcon.setOnClickListener {
            searchText = ""
            binding.search.setText(searchText)
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.clearIcon.windowToken, 0)
            adapter.updateTracks(emptyList())
            searchNonWords()
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.clearIcon.visibility = clearButtonVisibility(s)
                searchText = binding.search.text.toString()
                if (s.isNotEmpty()) {
                    searchDebounce()
                } else {
                    searchNonWords()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.search.addTextChangedListener(searchTextWatcher)

        searchViewModel.getInitTracksLiveData().observe(viewLifecycleOwner) {
            adapterHistory.updateTracks(it)
            if (it.isNotEmpty()) {
                binding.storyTrack.clearHistorySearch.isVisible = true
                binding.storyTrack.textSearchHistory.isVisible = true
                binding.searchError.isVisible = false
                binding.textErrorNothing.isVisible = false

            }

        }

        searchViewModel.getTracksLiveData()
            .observe(viewLifecycleOwner) { tracks ->

                binding.progressBar.isVisible = false

                adapter.updateTracks(tracks ?: listOf())
                if (tracks != null) {
                    if (adapter.getTracks().isEmpty()) {
                        binding.searchPlayList.isVisible = false
                        binding.searchError.isVisible = true
                        binding.textErrorNothing.isVisible = true


                    } else {
                        binding.searchPlayList.isVisible = true
                        binding.searchError.isVisible = false
                        binding.textErrorNothing.isVisible = false
                        binding.searchServerError.isVisible = false
                        binding.textServerError.isVisible = false
                        binding.update.isVisible = false
                        binding.progressBar.isVisible = false
                    }
                }
            }

        searchViewModel.getPrefTracksLiveData().observe(viewLifecycleOwner) {
            val (trackDataHandler, track) = it

            track?.let {
                adapterHistory.addTrack(track, trackDataHandler)
                searchViewModel.writeTracks(trackDataHandler)
            }
        }
    }

    private var isClickAllowed = true

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }

        }
        return current
    }


    var job: Job? = null

    private fun searchDebounce() {
        job?.cancel()
        job = viewLifecycleOwner.lifecycleScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            requestTrackList()
        }
    }


    private fun searchNonWords() {
        job?.cancel()
        binding.searchPlayList.isVisible = false
        binding.storyTrack.historySearch.isVisible = true
        binding.storyTrack.storyTrackLiner.isVisible = true
        binding.searchError.isVisible = false
        binding.textErrorNothing.isVisible = false

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }


    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun requestTrackList() {
        binding.progressBar.isVisible = true
        binding.searchPlayList.isVisible = false
        binding.storyTrack.historySearch.isVisible = false
        binding.storyTrack.storyTrackLiner.isVisible = false
        binding.searchError.isVisible = false
        binding.textErrorNothing.isVisible = false

        searchViewModel.getTracks(searchText)
    }


    private fun cleanHistory() {
        searchViewModel.cleanHistory()
        adapterHistory.updateTracks(emptyList())
        binding.storyTrack.storyTrackLiner.isVisible = false
    }

    override fun onClick(track: Track) {
        if (clickDebounce()) {
            val displayAudioPlayer =
                Intent(requireContext(), AudioPlayerActivity::class.java).apply {
                    putExtra(TRACK_SERIALIZABLE, track)
                }
            searchViewModel.getTracksPref(track)
            startActivity(displayAudioPlayer)

        }
    }
}

