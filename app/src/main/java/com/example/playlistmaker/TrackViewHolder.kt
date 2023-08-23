package com.example.playlistmaker

import android.net.Uri
import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val albumImage: ImageView = itemView.findViewById(R.id.album_image)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val singer: TextView = itemView.findViewById(R.id.singer)
    private val songDuration: TextView = itemView.findViewById(R.id.song_duration)

    fun bind(item: Track) {
        trackName.text = item.trackName
        singer.text = item.artistName
        songDuration.text = item.trackTime
        val urlAlbumIcon = item.artworkUrl100
        Glide.with(itemView).load(urlAlbumIcon).transform(RoundedCorners(25)).error(R.drawable.place_holder).into(albumImage)
    }
}