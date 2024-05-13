package com.example.playlistmaker.player.UI


import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.search.data.DateFormater
import com.example.playlistmaker.search.domain.models.Track
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

const val TRACK_SERIALIZABLE = "track_Serializable"

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioPlayerBinding

    private val audioPlayerViewModel by viewModel<AudioPlayerViewModel>()

    private val mediaPlayer: MediaPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(TRACK_SERIALIZABLE) as Track
        audioPlayerViewModel.saveTrack(item)


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

            audioPlayerViewModel.play(item)

            mediaPlayer.setOnCompletionListener {
                audioPlayerViewModel.stopPlay()

                trackTimePlay.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(0)
                buttonPlay.isVisible = true
                buttonPause.isVisible = false
            }

            audioPlayerViewModel.getAudioStateLiveData()
                .observe(this@AudioPlayerActivity) { state ->
                    trackTimePlay.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                        .format(state.onPlayProgressStatus)
                }

            arrowBack.setOnClickListener {
                finish()
            }

            buttonLike.setOnClickListener {
                audioPlayerViewModel.onFavoriteClicked()

            }
            buttonLikeOn.setOnClickListener {
                audioPlayerViewModel.onFavoriteClicked()
            }
        }

        audioPlayerViewModel.getTrackStateLiveData().observe(this@AudioPlayerActivity) {
            val isFavorite = (it)?.run { isFavorite } ?: false
            binding.buttonLike.isVisible = !isFavorite
            binding.buttonLikeOn.isVisible = isFavorite
        }
    }

    override fun onStop() {
        super.onStop()
        binding.buttonPlay.isVisible = true
        binding.buttonPause.isVisible = false
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    companion object {
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
        const val STATE_DEFAULT = 0
        const val DELEY = 1000L
    }

}
