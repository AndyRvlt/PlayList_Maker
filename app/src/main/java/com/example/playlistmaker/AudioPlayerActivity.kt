package com.example.playlistmaker

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import java.time.ZoneId
import java.util.*
const val TRACK_SERIALIZABLE = "track_Serializable"

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var buttonArrowBack: Button
    lateinit var binding: ActivityAudioPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getSerializableExtra(TRACK_SERIALIZABLE) as Track
        binding.apply {
            trackName.text = item.trackName
            singer.text = item.artistName
            songDuration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)
            nameAlbum.text = item.collectionName
            earAlbum.text =
                SimpleDateFormat("yyyy").parse(item.releaseDate).toInstant()
                    .atZone(ZoneId.systemDefault()).year.toString()
            nameGenre.text = item.primaryGenreName
            nameCountry.text = item.country
            val urlAlbumIcon = item.getCoverArtwork()

            Glide.with(this@AudioPlayerActivity)
                .load(urlAlbumIcon)
                .transform(RoundedCorners(25))
                .placeholder(R.drawable.place_holder)
                .into(albumImage)
        }

        buttonArrowBack = findViewById(R.id.arrowBack)

        buttonArrowBack.setOnClickListener {
            finish()
        }

    }
}