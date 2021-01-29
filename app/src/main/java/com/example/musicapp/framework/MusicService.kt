package com.example.musicapp.framework

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.example.musicapp.Constants.Companion.METADATA_ARTIST
import com.example.musicapp.Constants.Companion.METADATA_TITLE
import com.example.musicapp.data.MusicPlayer
import com.example.musicapp.data.SimplePlayerReadyListener
import com.example.musicapp.data.entities.PlaylistData
import com.example.musicapp.data.entities.SongData
import com.example.musicapp.ui.entities.Song
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

class MusicService : Service() {

    lateinit var binder: MusicServiceBinder
    override fun onCreate() {
        super.onCreate()
        binder = MusicServiceBinder(this)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    class MusicServiceBinder(val context: Context) : Binder(), MusicPlayer {

        var playlist: MutableList<SongData> = ArrayList()
        val playlistChannel = Channel<PlaylistData>()
        var readyListener: SimplePlayerReadyListener? = null
        var exoPlayer: ExoPlayer

        init {
            exoPlayer = SimpleExoPlayer.Builder(context).build()
            exoPlayer.addListener(object : Player.EventListener {
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_READY -> {
                            readyListener?.onPlayerStateReady((exoPlayer.duration / 1000).toInt())
                        }
                    }
                }
            })
        }

        override fun setPlayerStateListener(listener: SimplePlayerReadyListener) {
            this.readyListener = listener
        }

        override fun playSong(song: SongData) {
            // TODO Dummy variable to be removed
            val preferenceAddReplace = true
            if (preferenceAddReplace) {
                playlist.add(song)
                exoPlayer.addMediaItem(MediaItem.fromUri(Uri.parse(song.uri)))
                exoPlayer.prepare()
                exoPlayer.play()
            } else {
                playlist[0] = song
                exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(song.uri)))
                exoPlayer.prepare()
                exoPlayer.play()
            }
            runBlocking {
                playlistChannel.send(PlaylistData("Untitled", playlist))
            }
        }

        override fun playList(songs: List<SongData>) {
            val mediaItems: List<MediaItem> = songs.map { it ->
                MediaItem.fromUri(Uri.parse(it.uri))
            }
            exoPlayer.setMediaItems(mediaItems)
            exoPlayer.prepare()
            exoPlayer.play()
        }

        override fun next() {
            exoPlayer.next()
        }

        override fun previous() {
            exoPlayer.previous()
        }

        override fun stop() {
            exoPlayer.stop(true)
        }

        override fun getPlaylist(): Channel<PlaylistData> {
            return playlistChannel
        }

        override fun playPause() {
            if (exoPlayer.isPlaying) exoPlayer.play()
            else exoPlayer.pause()
        }

        override fun seek(timestamp: Int) {
            // Adding checks for currently playing now, will try to be more thorough later
            if (exoPlayer.isPlaying) {
                exoPlayer.seekTo(timestamp * 1000L)
            }
        }

        override fun playerTemp(): ExoPlayer {
            return exoPlayer
        }

        override fun playSongAtPos(index: Int) {
            exoPlayer.seekToDefaultPosition(index)
        }

        override fun songDataToSong(songData: SongData): Song {
            return Song(
                exoPlayer.duration.toInt(),
                "exoPlayer Farts",
                "uri",
                mapOf(
                    METADATA_TITLE to "Song",
                    METADATA_ARTIST to "Artist"
                )
            )
        }
    }
}
