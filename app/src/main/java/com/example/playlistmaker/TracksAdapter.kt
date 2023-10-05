package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TracksAdapter( val trackListener: TrackListener) : RecyclerView.Adapter<TrackViewHolder>() {
    private var tracks: List<Track> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position], trackListener)
    }

    override fun getItemCount() = tracks.size
    fun updateTracks(tracks: List<Track>) {
        this.tracks = tracks
       notifyDataSetChanged()
    }

    fun getTracks(): List<Track> {
return tracks
    }

     interface TrackListener{
         fun onClick(track: Track)
     }
//    эксперимент
fun getTrack(track: Track): Track {
return track
}

}