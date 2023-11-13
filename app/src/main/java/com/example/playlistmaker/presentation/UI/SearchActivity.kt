package com.example.playlistmaker.presentation.UI

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.data.response.TrackResponse
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.preferences.TrackPreferences
import com.example.playlistmaker.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.domain.interactor.GetTracksInteractorImpl
import com.example.playlistmaker.domain.interactor.TrackPrefencesInteractorImpl
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val TRACK_HISTORY = "track_history"
const val TRACK = "track"
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


    private lateinit var sharedPreferences: SharedPreferences


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

    }

    val adapter = TracksAdapter(this)
    val adapterHistory = TracksAdapter(this)
    val tracksInteractor = GetTracksInteractorImpl()
    val trackPrefencesInteractorImpl = TrackPrefencesInteractorImpl()

    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)

        initViews()
        historyTrackList?.adapter = adapterHistory
        searchPlayList?.adapter = adapter

        startSearchActivity()
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

        val trackDataHandler = trackPrefencesInteractorImpl.getTrackPreferences(sharedPreferences)
        adapterHistory.updateTracks(trackDataHandler.tracks)
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
        if (searchText.isNotEmpty())


            tracksInteractor.getTracks(searchText, object : GetTracksInteractor.GetTrackConsumer {
                override fun consume(tracks: List<Track>) {
                    handler.post {
                        progressBar?.isVisible = false

                        adapter.updateTracks(tracks)

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
            })
    }

    private fun cleanHistory() {
        trackPrefencesInteractorImpl.cleanHistory(sharedPreferences)
        adapterHistory.updateTracks(emptyList())
        storyTrackLiner?.isVisible = false
    }

    private fun startSearchActivity() {
        val trackDataHandler = trackPrefencesInteractorImpl.getTrackPreferences(sharedPreferences)
        if (trackDataHandler.tracks.isEmpty()) {
            storyTrackLiner?.isVisible = false
        }
    }

    override fun onClick(track: Track) {
        if (clickDebounce()) {
            val displayAudioPlayer = Intent(this, AudioPlayerActivity::class.java).apply {
                putExtra(TRACK_SERIALIZABLE, track)
            }
            startActivity(displayAudioPlayer)

            val trackDataHandler =
                trackPrefencesInteractorImpl.getTrackPreferences(sharedPreferences)

            adapterHistory.addTrack(track, trackDataHandler)

            trackPrefencesInteractorImpl.write(trackDataHandler, sharedPreferences)
        }
    }

}
