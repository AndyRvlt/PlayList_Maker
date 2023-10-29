package com.example.playlistmaker


import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer.OnCompletionListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import java.time.ZoneId
import java.util.*

const val TRACK_SERIALIZABLE = "track_Serializable"

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var buttonArrowBack: Button
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var buttonPlay: ImageButton
    private lateinit var buttonPause: ImageButton


    companion object {
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
        const val STATE_DEFAULT = 0
        const val DELEY = 1000L
    }

    private var trackTimePlay: TextView? = null

    val mediaPlayer = MediaPlayer()
    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        trackTimePlay = findViewById(R.id.track_time_play)

        buttonPlay = findViewById(R.id.button_play)

        buttonPause = findViewById(R.id.button_pause)


        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getSerializableExtra(TRACK_SERIALIZABLE) as Track

        mediaPlayer.preparePlayer(item)

        binding.apply {
            trackName.text = item.trackName
            singer.text = item.artistName
            songDuration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)
            nameAlbum.text = item.collectionName
            earAlbum.text =
                SimpleDateFormat("yyyy")
                    .parse(item.releaseDate)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .year
                    .toString()
            nameGenre.text = item.primaryGenreName
            nameCountry.text = item.country
            val urlAlbumIcon = item.getCoverArtwork()

            Glide.with(this@AudioPlayerActivity)
                .load(urlAlbumIcon)
                .transform(RoundedCorners(25))
                .placeholder(R.drawable.place_holder)
                .into(albumImage)

            buttonPlay.setOnClickListener {
                mediaPlayer.playbackControl()
                buttonPause.isVisible = true

            }

            buttonPause.setOnClickListener {
                mediaPlayer.playbackControl()
                buttonPlay.isVisible = true
                buttonPause.isVisible = false

            }


            val myRunnable = object : Runnable {
                override fun run() {
                    trackTimePlay?.text =
                        SimpleDateFormat("mm:ss", Locale.getDefault())
                            .format(mediaPlayer.mediaPlayer.currentPosition)
                    handler.postDelayed(this, DELEY)
                }
            }

            handler.post(myRunnable)

            mediaPlayer.mediaPlayer.setOnCompletionListener {
                handler.removeCallbacks(myRunnable)
                trackTimePlay?.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(0)
                buttonPlay.isVisible = true
                buttonPause.isVisible = false
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
        mediaPlayer.mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null);
        mediaPlayer.mediaPlayer.stop()
        mediaPlayer.mediaPlayer.release()
    }
}
