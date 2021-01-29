package com.example.musicapp.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R
import com.example.musicapp.framework.MusicService
import com.example.musicapp.ui.entities.Playlist
import com.example.musicapp.ui.fragments.LocalMusicFragment
import com.example.musicapp.ui.fragments.NowPlayingFragment
import com.example.musicapp.usecase.BasicUseCase
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity(), NowPlayingFragment.BasicFragmentCallback,
    LocalMusicFragment.LocalMusicCallback, NavigationView.OnNavigationItemSelectedListener {

    lateinit var useCase: BasicUseCase
    var tempServConn: NowPlayingFragment.TempServConn? = null
    lateinit var nowPlayingFragment: NowPlayingFragment
    lateinit var localMusicFragment: LocalMusicFragment

    val servConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: MusicService.MusicServiceBinder = service as MusicService.MusicServiceBinder
            useCase = BasicUseCase(binder)
            tempServConn?.onServConn(useCase.exoplayerTemp())
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    //TODO Connect Navigation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawermain_local -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, localMusicFragment)
                    .commit()
                return true
            }
            R.id.drawermain_nowplaying -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, localMusicFragment)
                    .commit()
                return true
            }
            else -> {
                return false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nowPlayingFragment = NowPlayingFragment()
        localMusicFragment = LocalMusicFragment()
        bindService(Intent(this, MusicService::class.java), servConn, Context.BIND_AUTO_CREATE)
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_main, nowPlayingFragment)
            .commit()
    }

    override fun onSongSelected(uri: String) {
        useCase.playSong(uri)
    }

    override fun playPauseTemp() {
        useCase.playPause()
    }

    override fun setServConn(servConn: NowPlayingFragment.TempServConn) {
        tempServConn = servConn
    }

    override fun getPlaylist(): Flow<Playlist> {
        return useCase.getPlaylist()
    }
}