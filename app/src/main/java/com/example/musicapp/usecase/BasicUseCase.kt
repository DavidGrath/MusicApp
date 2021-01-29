package com.example.musicapp.usecase

import com.example.musicapp.data.MusicPlayer
import com.example.musicapp.data.SimplePlayerReadyListener
import com.example.musicapp.data.entities.SongData
import com.example.musicapp.ui.entities.Playlist
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map

class BasicUseCase(val player: MusicPlayer) {
    fun playSong(uri: String) {
        player.playSong(SongData(uri))
    }

    fun playPause() {
        player.playPause()
    }

    fun exoplayerTemp(): ExoPlayer {
        return player.playerTemp()
    }

    fun getPlaylist(): Flow<Playlist> {
        return player.getPlaylist().consumeAsFlow().map { p ->
            Playlist(
                p.name,
                p.songs.map { it -> player.songDataToSong(it) }.toTypedArray()
            )
        }
    }

    fun setPlayerStateListener(listener: SimplePlayerReadyListener) {
        player.setPlayerStateListener(listener)
    }

    fun seekTo(timestamp: Int) {
        player.seek(timestamp)
    }
}