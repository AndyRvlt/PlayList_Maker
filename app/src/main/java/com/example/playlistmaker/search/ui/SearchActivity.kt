package com.example.playlistmaker.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.player.UI.AudioPlayerActivity
import com.example.playlistmaker.player.UI.TRACK_SERIALIZABLE
import com.example.playlistmaker.search.domain.models.Track


const val MAX_TRACKS_HISTORY_LIST = 10


class SearchActivity : AppCompatActivity(), TracksAdapter.TrackListener {

    companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var search: EditText? = null
    private var clearIcon: ImageView? = null
    private var buttonArrowBack: Button? = null
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

    private lateinit var searchViewModel: SearchViewModel

    private fun initViews() {
        search = findViewById(R.id.search)
        clearIcon = findViewById(R.id.clearIcon)
        buttonArrowBack = findViewById(R.id.arrowBack)
        searchPlayList = findViewById(R.id.searchPlayList)
        textErrorNothing = findViewById(R.id.textErrorNothing)
        searchError = findViewById(R.id.searchError)
        searchServerError = findViewById(R.id.searchServerError)
        textServerError = findViewById(R.id.textServerError)
        update = findViewById(R.id.update)
        clearHistorySearch = findViewById(R.id.clear_history_search)
        storyTrackLiner = findViewById(R.id.storyTrackLiner)
        historyTrackList = findViewById(R.id.historySearch)
        progressBar = findViewById(R.id.progressBar)
        textSearchHistory = findViewById(R.id.textSearchHistory)
    }

    val adapter = TracksAdapter(this)
    val adapterHistory = TracksAdapter(this)


    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchViewModel = ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]


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

        buttonArrowBack?.setOnClickListener {
            finish()
        }

        search?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requestTrackList()
                searchPlayList?.isVisible = true
                storyTrackLiner?.isVisible = false
                true
            }
            false
        }

        clearIcon?.setOnClickListener {
            searchText = ""
            search?.setText(searchText)
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(clearIcon?.windowToken, 0)
            adapter.updateTracks(emptyList())
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIcon?.visibility = clearButtonVisibility(s)
                searchText = search?.text.toString()

                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        search?.addTextChangedListener(searchTextWatcher)

        searchViewModel.getInitTracksLiveData().observe(this) {
            adapterHistory.updateTracks(it)
            if (it.isNotEmpty()) {
                clearHistorySearch?.isVisible = true
                textSearchHistory?.isVisible = true

            }
        }

        searchViewModel.getTracksLiveData()
            .observe(this) { tracks ->

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
        searchViewModel.getPrefTracksLiveData().observe(this) {
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT, "")
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
            val displayAudioPlayer = Intent(this, AudioPlayerActivity::class.java).apply {
                putExtra(TRACK_SERIALIZABLE, track)
            }
            searchViewModel.getTracksPref(track)
            startActivity(displayAudioPlayer)

        }
    }
}
