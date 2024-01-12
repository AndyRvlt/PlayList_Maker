package com.example.playlistmaker.player.UI


import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.search.data.DateFormater
import com.example.playlistmaker.search.domain.models.Track
import java.util.*

const val TRACK_SERIALIZABLE = "track_Serializable"

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var buttonArrowBack: Button
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var buttonPlay: ImageButton
    private lateinit var buttonPause: ImageButton
    private lateinit var audioPlayerViewModel: AudioPlayerViewModel

    private var trackTimePlay: TextView? = null


    val mediaPlayer = Creator.createPlayer()
    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        trackTimePlay = findViewById(R.id.track_time_play)

        buttonPlay = findViewById(R.id.button_play)

        buttonPause = findViewById(R.id.button_pause)

        audioPlayerViewModel = ViewModelProvider(
            this,
            AudioPlayerViewModel.getViewModelFactory()
        )[AudioPlayerViewModel::class.java]

        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(TRACK_SERIALIZABLE) as Track


        audioPlayerViewModel.getPreparePlayer(item)

        binding.apply {
            trackName.text = item.trackName
            singer.text = item.artistName
            songDuration.text = DateFormater.trackTimeFormatter(item.trackTimeMillis)
            nameAlbum.text = item.collectionName
            earAlbum.text = DateFormater.trackDateFormatter(item.releaseDate)
            nameGenre.text = item.primaryGenreName
            nameCountry.text = item.country
            val urlAlbumIcon = item.getCoverArtwork()

            Glide.with(this@AudioPlayerActivity)
                .load(urlAlbumIcon)
                .transform(RoundedCorners(25))
                .placeholder(R.drawable.place_holder)
                .into(albumImage)

            buttonPlay.setOnClickListener {
                audioPlayerViewModel.playbackControl()
                audioPlayerViewModel.play(item)
                buttonPause.isVisible = true

            }

            buttonPause.setOnClickListener {
                audioPlayerViewModel.playbackControl()
                buttonPlay.isVisible = true
                buttonPause.isVisible = false

            }

            val myRunnable = object : Runnable {
                override fun run() {
                    audioPlayerViewModel.play(item)
                    handler.postDelayed(this, DELEY)
                }
            }

            handler.post(myRunnable)

            mediaPlayer.setOnCompletionListener {
                handler.removeCallbacks(myRunnable)
                trackTimePlay?.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(0)
                buttonPlay.isVisible = true
                buttonPause.isVisible = false
            }

            audioPlayerViewModel.getPlayStatusLiveData()
                .observe(this@AudioPlayerActivity) { playStatus ->
                    trackTimePlay?.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                        .format(playStatus.onPlayProgressStatus)
                }

        }

        buttonArrowBack = findViewById(R.id.arrowBack)

        buttonArrowBack.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        buttonPlay.isVisible = true
        buttonPause.isVisible = false
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    companion object {
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
        const val STATE_DEFAULT = 0
        const val DELEY = 1000L
    }


}
