package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val TRACK_HISTORY = "track_history"
const val TRACK = "track"
private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

class SearchActivity : AppCompatActivity(), TracksAdapter.TrackListener {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(TrackApi::class.java)


    private lateinit var search: EditText
    private lateinit var clearIcon: ImageView
    private lateinit var buttonArrowBack: Button
    private lateinit var searchError: ImageView
    private lateinit var searchPlayList: RecyclerView
    private lateinit var textErrorNothing: TextView
    private lateinit var searchServerError: ImageView
    private lateinit var textServerError: TextView
    private lateinit var update: Button
    private lateinit var storyTrackLiner: LinearLayout
    private lateinit var updateHistorySearch: Button
    private lateinit var historyTrackList: RecyclerView

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
        updateHistorySearch = findViewById(R.id.update_history_search)
        storyTrackLiner = findViewById(R.id.storyTrackLiner)
        historyTrackList = findViewById(R.id.historySearch)
    }


    val adapter = TracksAdapter(this)
    val adapterHistory = TracksAdapter(this)


    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        historyTrackList.adapter = adapterHistory


        searchPlayList.adapter = adapter

        update.setOnClickListener {
            requestTrackList()
        }

        buttonArrowBack.setOnClickListener {
            finish()
        }

        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requestTrackList()
                true
            }
            false
        }

        clearIcon.setOnClickListener {
            searchText = ""
            search.setText(searchText)
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(clearIcon.windowToken, 0)
            adapter.updateTracks(emptyList())
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIcon.visibility = clearButtonVisibility(s)
                searchText = search.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        search.addTextChangedListener(searchTextWatcher)

    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
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
        if (searchText.isEmpty()) return
        trackService.search(searchText)
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        adapter.updateTracks(emptyList())
                        if (response.body()?.results?.isNotEmpty() == true) {
                            adapter.updateTracks(response.body()?.results ?: emptyList())
                        }

                        if (adapter.getTracks().isEmpty()) {
                            searchPlayList.isVisible = false
                            searchError.isVisible = true
                            textErrorNothing.isVisible = true
                        } else {
                            searchPlayList.isVisible = true
                            searchError.isVisible = false
                            textErrorNothing.isVisible = false
                            searchServerError.isVisible = false
                            textServerError.isVisible = false
                            update.isVisible = false
                        }

                    }
                    if (response.code() == 500) {
                        searchPlayList.isVisible = false
                        searchServerError.isVisible = true
                        textServerError.isVisible = true
                        update.isVisible = true
                        searchError.isVisible = false
                        textErrorNothing.isVisible = false

                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    searchPlayList.isVisible = false
                    searchServerError.isVisible = true
                    textServerError.isVisible = true
                    update.isVisible = true
                }

            })
    }

//    val sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)
//
//    data class TrackHendler(val list: List<Track>)
//
//    private fun createJsonFromTrack(track: Array<Track>): String {
//        val sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)
//        createTrackFromJson(sharedPreferences.getString(TRACK, "") ?: "")
//        return Gson().toJson(track)
//    }
//
//
//    private fun createTrackFromJson(json: String): TrackResponse? {
//        return Gson().fromJson(json, TrackResponse::class.java)
//    }
//
//    //Чтение
//    fun readHistoryTracks(sharedPreferences: SharedPreferences, tracks: Array<Track>): Array<Track>? {
//        val json = sharedPreferences.getString(TRACK, null) ?: return null
//        return Gson().fromJson(json, tracks::class.java)
//    }
//
//
//    fun saveHistoryTracks(tracks: Array<Track>) {
//        val json = createJsonFromTrack(tracks)
//        sharedPreferences.edit().putString(TRACK, json).apply()
//    }
//

//
//     fun addTrack(track: Track) {
//        if (tracks.isEmpty()) {
//            tracks.add(track)
//        }
//    }

    //    fun readSearchHistory(): ArrayList<Track> {
//        val json = sharedPreferences.getString(TRACK_HISTORY, null) ?: return ArrayList<Track>()
//        return createTrackFromJson(json)
//    }


    private val tracks: ArrayList<Track> = getArrayOfTracks()

    private fun getArrayOfTracks(): ArrayList<Track> {
        return tracks
    }

    class TrackPreferences {

        fun read(sharedPreferences: SharedPreferences): Array<Track> {
            val json = sharedPreferences.getString(TRACK, null) ?: return emptyArray()
            return Gson().fromJson(json, Array<Track>::class.java)
        }

        fun write(sharedPreferences: SharedPreferences, tracks: Array<Track>) {
            val json = Gson().toJson(tracks)
            sharedPreferences.edit().putString(TRACK, json).apply()
        }

    }

//    val sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)
//    val track = sharedPreferences.getString(TRACK, null)

//    private fun addTrack(track: Track) {
//
//        adapterHistory
//
//
//    }


    override fun onClick(track: Track) {




//        val sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)
//        val tracks = sharedPreferences.getString(TRACK, null)
        adapterHistory

//        addTrack(track)

        Toast.makeText(this, "Нажали на: ${track.trackName}", Toast.LENGTH_SHORT).show()

        searchPlayList.isVisible = false
        storyTrackLiner.isVisible = true


//

//
//        sharedPreferences.edit().putString(TRACK, createJsonFromTrack(track)).apply()


//        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
//
//            if (key == TRACK) {
//                sharedPreferences?.getString(TRACK, null)
//
//
//            }
//        }
//        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)


    }

}
