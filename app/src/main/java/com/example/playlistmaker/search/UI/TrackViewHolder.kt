package com.example.playlistmaker.search.UI

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.search.UI.TracksAdapter
import com.example.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.*

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val albumImage: ImageView = itemView.findViewById(R.id.album_image)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val singer: TextView = itemView.findViewById(R.id.singer)
    private val songDuration: TextView = itemView.findViewById(R.id.song_duration)
    private val trackFocus: LinearLayout = itemView.findViewById(R.id.track_focus)


    fun bind(item: Track, trackListener: TracksAdapter.TrackListener) {
        trackName.text = item.trackName
        singer.text = item.artistName
        songDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)
        val urlAlbumIcon = item.artworkUrl100
        Glide.with(itemView).load(urlAlbumIcon).transform(RoundedCorners(25))
            .placeholder(R.drawable.place_holder).into(albumImage)
        trackFocus.setOnClickListener {
            trackListener.onClick(item)
        }
    }
}