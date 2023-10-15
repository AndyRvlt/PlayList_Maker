package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class TracksAdapter(val trackListener: TrackListener) : RecyclerView.Adapter<TrackViewHolder>() {
    private var tracks: MutableList<Track> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
        return TrackViewHolder(view.inflate(R.layout.track, parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position], trackListener)
    }

    override fun getItemCount() = tracks.size
    fun updateTracks(tracks: List<Track>) {
        this.tracks = tracks.toMutableList()
        notifyDataSetChanged()
    }

    fun addTrack(track: Track, trackDataHandler: SearchActivity.TrackDataHandler) {
        val foundTrack = tracks.find { it.trackId == track.trackId }

        if (trackDataHandler.tracks.size < MAX_TRACKS_HISTORY_LIST) {
            if (foundTrack == null) {
                tracks.add(0, track)
                trackDataHandler.tracks.add(0, track)
                notifyItemInserted(0)
            } else {
                notifyItemMoved(tracks.indexOf(track), 0)
                tracks.removeAt(tracks.indexOf(track))
                tracks.add(0, track)
                trackDataHandler.tracks.apply {
                    removeAt(indexOf(track))
                    add(0, track)
                }
            }
        } else {
            trackDataHandler.tracks.removeLast()
            tracks.removeLast()
            addTrack(track, trackDataHandler)
        }
    }



    fun getTracks(): List<Track> {
        return tracks
    }

    interface TrackListener {
        fun onClick(track: Track)
    }

}