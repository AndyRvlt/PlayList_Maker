//package com.example.playlistmaker
//
//import androidx.appcompat.app.AppCompatActivity
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//const val TRACK_HISTORY = "track_history"
//const val TRACK = "track"
//
//class TracksSearchHistory() {
//
//    private val tracks: ArrayList<Track> = getArrayOfTracks()
//    val sharedPreferences = getSharedPreferences(TRACK_HISTORY, MODE_PRIVATE)
//
//    fun writeTrackSearchHistory(tracks: ArrayList<Track>) {
//        val json = createJsonFromTrack(tracks)
//        sharedPreferences.edit().putString(TRACK, json).apply()
//    }
//
//    private fun createJsonFromTrack(tracks: ArrayList<Track>): String {
//        return Gson().toJson(tracks)
//    }
//
//    fun readTrackSearchHistory(): ArrayList<Track> {
//        val json = sharedPreferences.getString(TRACK, null) ?: return ArrayList<Track>()
//        return createTrackFromJson(json)
//
//    }
//
//    private fun createTrackFromJson(json: String): ArrayList<Track> {
//        val token = object : TypeToken<ArrayList<Track>>() {}.type
//        return Gson().fromJson(json, token)
//    }
//
//}

//class SearchHistory(private val sharedPreferences: App) {
//
//    private companion object {
//        const val MAX_TRACKS_LIST_COUNT = 10
//    }
//
//    private val tracks: ArrayList<Track> = getArrayOfTracks()
//
//    private fun getArrayOfTracks(): ArrayList<Track> {
//        return sharedPreferences.readSearchHistory()
//    }
//
//    fun addTrack(track: Track) {
//        if (tracks.isEmpty()) {
//            tracks.add(track)
//            sharedPreferences.writeSearchHistory(tracks)
//            return
//        }
//        if (tracks.isNotEmpty()) {
//            for (item in tracks) {
//                if (item.trackId == track.trackId) {
//                    tracks.remove(item)
//                    tracks.add(0, track)
//                    sharedPreferences.writeSearchHistory(tracks)
//                    return
//                }
//            }
//        }
//        if (tracks.size < MAX_TRACKS_LIST_COUNT) {
//            tracks.add(0, track)
//        } else {
//            tracks.removeLast()
//            tracks.add(0, track)
//        }
//        sharedPreferences.writeSearchHistory(tracks)
//    }
//
//    fun getTracksHistory(): ArrayList<Track> {
//        return tracks
//    }
//
//    fun clean() {
//        tracks.clear()
//        sharedPreferences.writeSearchHistory(tracks)
//    }
//
//}
//package com.practicum.playlistmaker
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.View
//import android.view.inputmethod.EditorInfo
//import android.view.inputmethod.InputMethodManager
//import android.widget.Button
//import android.widget.EditText
//import android.widget.FrameLayout
//import android.widget.ImageButton
//import android.widget.LinearLayout
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class SearchActivity : AppCompatActivity(), IClickView {
//
//    private companion object {
//        const val SEARCH_TEXT = "SEARCH_TEXT"
//    }
//
//    private var query: String? = null
//    private var tracks = ArrayList<Track>()
//    private var tracksHistory = ArrayList<Track>()
//    private lateinit var searchHistory: SearchHistory
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://itunes.apple.com")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    private val searchApiService = retrofit.create(ISearchApiService::class.java)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search)
//
//        // in first step set all conditions
//        val backButton = findViewById<FrameLayout>(R.id.back_button)
//        val inputEditText = findViewById<EditText>(R.id.edit_view_search)
//        val clearButton = findViewById<ImageButton>(R.id.clear_text)
//        val trackRecyclerView = findViewById<RecyclerView>(R.id.track_recycler_view)
//        val errorConnection = findViewById<LinearLayout>(R.id.no_connection_error_layout)
//        val notFound = findViewById<LinearLayout>(R.id.not_found_layout)
//        val searchRefreshButton = findViewById<Button>(R.id.search_refresh_button)
//        val cleanHistoryButton = findViewById<Button>(R.id.clean_history_button)
//        val historyLayout = findViewById<View>(R.id.history_layout)
//        val trackRecyclerViewSearchHistory = findViewById<RecyclerView>(R.id.search_history)
//        var adapterSearch = TrackAdapter(tracks, this)
//        var adapterHistory = TrackAdapter(tracksHistory, this)
//        trackRecyclerView.adapter = adapterSearch
//        trackRecyclerViewSearchHistory.adapter = adapterHistory
//        searchHistory = SearchHistory(applicationContext as App)
//
//        trackRecyclerView.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        trackRecyclerViewSearchHistory.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        fun search() {
//
//            searchApiService.search(inputEditText.text.toString())
//                .enqueue(object : Callback<TrackResponse> {
//
//                    @SuppressLint("NotifyDataSetChanged")
//                    override fun onResponse(
//                        call: Call<TrackResponse>,
//                        response: Response<TrackResponse>
//                    ) {
//                        if (response.code() == 200) {
//                            errorConnection.visibility = View.GONE
//                            tracks.clear()
//                            if (response.body()?.results?.isNotEmpty() == true) {
//                                tracks.addAll(response.body()?.results!!)
//                            } else {
//                                notFound.visibility = View.VISIBLE
//                            }
//                        } else {
//                            errorConnection.visibility = View.VISIBLE
//                        }
//                        adapterSearch.notifyDataSetChanged()
//                    }
//
//                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
//                        tracks.clear()
//                        errorConnection.visibility = View.VISIBLE
//                    }
//                })
//        }
//
//        backButton.setOnClickListener {
//            finish()
//        }
//
//        fun refreshHistory() {
//            historyLayout.visibility = View.VISIBLE
//            tracksHistory = searchHistory.getTracksHistory()
//            adapterHistory.notifyDataSetChanged()
//        }
//
//        clearButton.setOnClickListener {
//            inputEditText.setText("")
//            hideSoftKeyboard(it)
//            notFound.visibility = View.GONE
//            errorConnection.visibility = View.GONE
//            tracks.clear()
//            adapterSearch.notifyDataSetChanged()
//            refreshHistory()
//        }
//
//        inputEditText.setOnEditorActionListener { _, actionId, _ ->
//            trackRecyclerView.visibility = View.VISIBLE
//            notFound.visibility = View.GONE
//            errorConnection.visibility = View.GONE
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                search()
//                true
//            }
//            false
//        }
//
//        inputEditText.setOnFocusChangeListener { view, hasFocus ->
//            if (hasFocus
//                && inputEditText.text.isEmpty()
//                && tracksHistory.isNotEmpty()
//            ) {
//                if (tracksHistory.isNotEmpty()) {
//                    refreshHistory()
//                }
//            } else {
//                historyLayout.visibility = View.GONE
//            }
//        }
//
//        cleanHistoryButton.setOnClickListener {
//            historyLayout.visibility = View.GONE
//            searchHistory.clean()
//        }
//
//        // at now all work process
//        val simpleTextWatcher = object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // empty
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                clearButton.visibility = clearButtonVisibility(s)
//                query = s.toString()
//                historyLayout.visibility =
//                    if (inputEditText.hasFocus()
//                        && s?.isEmpty() == true
//                        && tracksHistory.isNotEmpty()
//                    ) View.VISIBLE else View.GONE
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                query = inputEditText.text.toString()
//            }
//        }
//        inputEditText.addTextChangedListener(simpleTextWatcher)
//
//        if (savedInstanceState != null) {
//            inputEditText.setText(savedInstanceState.getString(SEARCH_TEXT, ""))
//        }
//
//        searchRefreshButton.setOnClickListener {
//            search()
//        }
//    }
//
//    private fun clearButtonVisibility(s: CharSequence?): Int {
//        return if (s.isNullOrEmpty()) {
//            View.GONE
//        } else {
//            View.VISIBLE
//        }
//    }
//
//    private fun hideSoftKeyboard(view: View) {
//        val imm =
//            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        query = savedInstanceState.getString(SEARCH_TEXT, "")
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(SEARCH_TEXT, query)
//    }
//
//    override fun onClick(track: Track) {
//        searchHistory.addTrack(track)
//    }
//
//}
//fun writeSearchHistory(tracks: ArrayList<Track>) {
//    val json = createJsonStringTracks(tracks)
//    sharedPrefs.edit()
//        .putString(SEARCH_HISTORY, json)
//        .apply()
//}
//
//fun readSearchHistory(): ArrayList<Track> {
//    val json = sharedPrefs.getString(SEARCH_HISTORY, null) ?: return ArrayList<Track>()
//    return createTracksFromJson(json)
//}
//
//private fun createJsonStringTracks(tracks: ArrayList<Track>): String {
//    return Gson().toJson(tracks)
//}
//
//private fun createTracksFromJson(json: String): ArrayList<Track> {
//    val token = object : TypeToken<ArrayList<Track>>() {}.type
//    return Gson().fromJson(json, token)
//}

