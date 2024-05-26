package com.example.playlistmaker.mediateca.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.db.domain.models.PlayList
import com.example.playlistmaker.search.domain.models.Track

class PlaylistAdapter () :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    private var playlists: List<PlayList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(view.inflate(R.layout.playlist,parent,false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
       holder.bind(playlists[position])
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    fun updatePlaylist(playlists: List<PlayList>) {
        this.playlists = playlists.toSet().toMutableList()
        notifyDataSetChanged()
    }


}