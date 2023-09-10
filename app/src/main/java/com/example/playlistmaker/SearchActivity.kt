package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private var searchText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search = findViewById<EditText>(R.id.search)
        val clearIcon = findViewById<ImageView>(R.id.clearIcon)
        val buttonArrowBack = findViewById<Button>(R.id.arrowBack)
        buttonArrowBack.setOnClickListener {
            finish()
        }

        clearIcon.setOnClickListener {
            searchText = ""
            search.setText(searchText)
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(clearIcon.windowToken, 0)
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

        val songAdapter = TracksAdapter(
            listOf(
                Track(
                    getString(R.string.trackName1),
                    getString(R.string.artistName1),
                    getString(R.string.trackTime1),
                    getString(R.string.urlTrack1)
                ),
                Track(
                    getString(R.string.trackName2),
                    getString(R.string.artistName2),
                    getString(R.string.trackTime2),
                    getString(R.string.urlTrack2)
                ),
                Track(
                    getString(R.string.trackName3),
                    getString(R.string.artistName3),
                    getString(R.string.trackTime3),
                    getString(R.string.urlTrack3)
                ),
                Track(
                    getString(R.string.trackName4),
                    getString(R.string.artistName4),
                    getString(R.string.trackTime4),
                    getString(R.string.urlTrack4)
                ),
                Track(
                    getString(R.string.trackName5),
                    getString(R.string.artistName5),
                    getString(R.string.trackTime5),
                    getString(R.string.urlTrack5)
                ),
            )
        )
        val playList = findViewById<RecyclerView>(R.id.searchPlayList)
        playList.adapter = songAdapter
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
}