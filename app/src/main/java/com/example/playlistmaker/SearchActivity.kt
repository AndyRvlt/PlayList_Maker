package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(TrackApi::class.java)

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

        val trackDataHandler = TrackPreferences.read(sharedPreferences)
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
            trackService.search(searchText)
                .enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        progressBar?.isVisible = false
                        if (response.code() == 200) {
                            adapter.updateTracks(emptyList())
                            if (response.body()?.results?.isNotEmpty() == true) {
                                adapter.updateTracks(response.body()?.results ?: emptyList())
                            }

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
                        if (response.code() == 500) {
                            searchPlayList?.isVisible = false
                            searchServerError?.isVisible = true
                            textServerError?.isVisible = true
                            update?.isVisible = true
                            searchError?.isVisible = false
                            textErrorNothing?.isVisible = false

                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        progressBar?.isVisible = false
                        searchPlayList?.isVisible = false
                        searchServerError?.isVisible = true
                        textServerError?.isVisible = true
                        update?.isVisible = true
                    }

                })
    }

    object TrackPreferences {

        fun read(sharedPreferences: SharedPreferences): TrackDataHandler {
            val json = sharedPreferences.getString(TRACK, null) ?: return TrackDataHandler(
                mutableListOf()
            )
            return Gson().fromJson(json, TrackDataHandler::class.java)
        }

        fun write(sharedPreferences: SharedPreferences, trackHandler: TrackDataHandler) {
            val json = Gson().toJson(trackHandler)
            sharedPreferences.edit().putString(TRACK, json).apply()


        }
    }

    data class TrackDataHandler(
        val tracks: MutableList<Track>
    )

    private fun cleanHistory() {
        val trackDataHandler = TrackPreferences.read(sharedPreferences)
        trackDataHandler.tracks.clear()
        adapterHistory.updateTracks(trackDataHandler.tracks)
        TrackPreferences.write(sharedPreferences, trackDataHandler)
        storyTrackLiner?.isVisible = false
    }

    private fun startSearchActivity() {
        val trackDataHandler = TrackPreferences.read(sharedPreferences)
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

            val trackDataHandler = TrackPreferences.read(sharedPreferences)

            adapterHistory.addTrack(track, trackDataHandler)
            TrackPreferences.write(sharedPreferences, trackDataHandler)

        }
    }

}
