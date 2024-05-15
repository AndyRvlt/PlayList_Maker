package com.example.playlistmaker.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.models.Track

const val MAX_TRACKS_HISTORY_LIST = 10

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
        this.tracks = tracks.toSet().toMutableList()
        notifyDataSetChanged()
    }

    fun addTrack(track: Track) {
        val foundTrack = tracks.find { it.trackId == track.trackId }

        if (tracks.size < MAX_TRACKS_HISTORY_LIST) {
            if (foundTrack == null) {
                tracks.add(0, track)
                notifyItemInserted(0)
            } else {
                val index = tracks.indexOf(track)
                if (index != -1) {
                    notifyItemMoved(tracks.indexOf(track), 0)
                    tracks.removeAt(tracks.indexOf(track))
                    tracks.add(0, track)
                }

            }
        } else {
            tracks.removeLast()
            addTrack(track)
        }
    }

    fun updateTrack(track: Track) {
        val index = tracks.indexOf(track)
        if (index != -1) {
            tracks[index] = track
            notifyItemChanged(index)
        }

    }

    interface TrackListener {
        fun onClick(track: Track)
    }

}