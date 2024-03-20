package com.example.playlistmaker.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.player.UI.AudioPlayerActivity
import com.example.playlistmaker.player.UI.TRACK_SERIALIZABLE
import com.example.playlistmaker.search.domain.models.Track
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), TracksAdapter.TrackListener {

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var search: EditText? = null
    private var clearIcon: ImageView? = null
    private var searchError: ImageView? = null
    private var searchPlayList: RecyclerView? = null
    private var textErrorNothing: TextView? = null
    private var searchServerError: ImageView? = null
    private var textServerError: TextView? = null
    private var update: Button? = null
    private var storyTrackLiner: LinearLayout? = null
    private var clearHistorySearch: Button? = null
    private var historyTrackList: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var textSearchHistory: TextView? = null

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var binding: FragmentSearchBinding


    private fun initViews() {
        search = binding.search
        clearIcon = binding.clearIcon
        searchPlayList = binding.searchPlayList
        textErrorNothing = binding.textErrorNothing
        searchError = binding.searchError
        searchServerError = binding.searchServerError
        textServerError = binding.textServerError
        update = binding.update
        progressBar = binding.progressBar
        clearHistorySearch = binding.storyTrack.clearHistorySearch
        storyTrackLiner = binding.storyTrack.storyTrackLiner
        historyTrackList = binding.storyTrack.historySearch
        textSearchHistory = binding.storyTrack.textSearchHistory

    }

    val adapter = TracksAdapter(this) //????
    val adapterHistory = TracksAdapter(this)//????

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

        searchText = savedInstanceState?.getString(SEARCH_TEXT, "") ?: ""

        searchViewModel.init()

        initViews()

        historyTrackList?.adapter = adapterHistory
        searchPlayList?.adapter = adapter


        clearHistorySearch?.setOnClickListener {
            cleanHistory()
        }

        update?.setOnClickListener {
            requestTrackList()
        }

        search?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requestTrackList()
                searchPlayList?.isVisible = true
                storyTrackLiner?.isVisible = false
            }
            false
        }

        clearIcon?.setOnClickListener {
            searchText = ""
            search?.setText(searchText)
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(clearIcon?.windowToken, 0)
            adapter.updateTracks(emptyList())
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                clearIcon?.visibility = clearButtonVisibility(s)
                searchText = search?.text.toString()
                if (s.isNotEmpty()) {
                    searchDebounce()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        search?.addTextChangedListener(searchTextWatcher)

        searchViewModel.getInitTracksLiveData().observe(viewLifecycleOwner) {
            adapterHistory.updateTracks(it)
            if (it.isNotEmpty()) {
                clearHistorySearch?.isVisible = true
                textSearchHistory?.isVisible = true

            }
        }

        searchViewModel.getTracksLiveData()
            .observe(viewLifecycleOwner) { tracks ->

                progressBar?.isVisible = false

                adapter.updateTracks(tracks ?: listOf())
                if (tracks != null) {
                    if (adapter.getTracks().isEmpty()) {
                        searchPlayList?.isVisible = false
                        searchError?.isVisible = true
                        textErrorNothing?.isVisible = true


                    } else {
                        searchPlayList?.isVisible = true
                        searchError?.isVisible = false
                        textErrorNothing?.isVisible = false
                        searchServerError?.isVisible = false
                        textServerError?.isVisible = false
                        update?.isVisible = false
                    }
                }
            }

        searchViewModel.getPrefTracksLiveData().observe(viewLifecycleOwner) {
            val (trackDataHandler, track) = it

            track?.let {
                adapterHistory.addTrack(track, trackDataHandler)
                searchViewModel.writeTracks(trackDataHandler) // поменял
            }
        }
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private val searchRunnable = Runnable { requestTrackList() }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
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
        progressBar?.isVisible = true
        searchPlayList?.isVisible = false
        historyTrackList?.isVisible = false
        storyTrackLiner?.isVisible = false

        searchViewModel.getTracks(searchText)

    }

    private fun cleanHistory() {
        searchViewModel.cleanHistory()
        adapterHistory.updateTracks(emptyList())
        storyTrackLiner?.isVisible = false
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

