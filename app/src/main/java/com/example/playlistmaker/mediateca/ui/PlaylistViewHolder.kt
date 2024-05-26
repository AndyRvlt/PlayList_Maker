package com.example.playlistmaker.mediateca.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.db.domain.models.PlayList

class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val playlistImageView: ImageView = itemView.findViewById(R.id.playlist_image)
    private val playlistTitle: TextView = itemView.findViewById(R.id.playlist_title)
    private val playlistQuantityOfTracks: TextView =
        itemView.findViewById(R.id.playlist_number_of_tracks)

    fun bind(playList: PlayList) {
        playlistTitle.text = playList.name
        playlistQuantityOfTracks.text = playList.trackCount.toString()
        val playlistIcon = playList.imageUri
        Glide.with(itemView).load(playlistIcon).transform(RoundedCorners(25))
            .placeholder(R.drawable.playlist_image_null).into(playlistImageView)
    }


}

