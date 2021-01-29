package com.example.musicapp.data

import com.example.musicapp.data.entities.PlaylistData
import com.example.musicapp.data.entities.SongData
import com.example.musicapp.ui.entities.Song
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.channels.Channel

interface MusicPlayer {
    fun playSong(song: SongData)
    fun playList(songs: List<SongData>)
    fun playPause()
    fun next()
    fun previous()
    fun stop()
    fun seek(timestamp: Int)
    fun setPlayerStateListener(listener: SimplePlayerReadyListener)
    fun playerTemp(): ExoPlayer
    fun getPlaylist(): Channel<PlaylistData>
    fun playSongAtPos(index: Int)

    //TODO Mapping from one layer's entities to another is something that should be done
    // in another layer entirely, I think, let me catch up later
    fun songDataToSong(songData: SongData): Song
    // May not need this
    //fun playAlbum()
}