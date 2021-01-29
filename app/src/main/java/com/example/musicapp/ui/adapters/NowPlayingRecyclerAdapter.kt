package com.example.musicapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.Constants.Companion.METADATA_ARTIST
import com.example.musicapp.Constants.Companion.METADATA_TITLE
import com.example.musicapp.R
import com.example.musicapp.ui.entities.Song

class NowPlayingRecyclerAdapter() :
    ListAdapter<Song, NowPlayingRecyclerAdapter.NowPlayingViewHolder>(diffutil) {
    companion object {
        val diffutil = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.uri.equals(newItem.uri)
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_song_now_playing, parent, false)
        return NowPlayingViewHolder(v)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val song = getItem(position)
        with(holder) {
            songIndex.text = position.toString() + "."
            songTitle.text = song.metadata.get(METADATA_TITLE)
            songArtist.text = song.metadata.get(METADATA_ARTIST)
        }
    }

    class NowPlayingViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val songIndex = itemView.findViewById<TextView>(R.id.textview_songnp_index)
        val songTitle = itemView.findViewById<TextView>(R.id.textview_songnp_title)
        val songArtist = itemView.findViewById<TextView>(R.id.textview_songnp_artist)
    }
}