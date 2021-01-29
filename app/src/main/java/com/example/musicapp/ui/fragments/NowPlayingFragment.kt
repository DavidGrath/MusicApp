package com.example.musicapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import com.example.musicapp.ui.adapters.NowPlayingRecyclerAdapter
import com.example.musicapp.ui.entities.Playlist
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.coroutines.flow.Flow

class NowPlayingFragment : Fragment() {

    interface TempServConn {
        fun onServConn(exoPlayer: ExoPlayer)
    }

    interface BasicFragmentCallback {
        fun playPauseTemp()
        fun setServConn(servConn: TempServConn)
        fun getPlaylist(): Flow<Playlist>
    }

    var basicFragmentCallback: BasicFragmentCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        basicFragmentCallback = context as BasicFragmentCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_now_playing, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar_now_playing)
        requireActivity().title = "Now Playing"

        val nowPlayingAdapter = NowPlayingRecyclerAdapter()
        recyclerview_now_playing.adapter = nowPlayingAdapter

        basicFragmentCallback?.setServConn(object : TempServConn {
            override fun onServConn(exoPlayer: ExoPlayer) {
                requireActivity().runOnUiThread {
                    exocontroller.player = exoPlayer
                }
                // TODO Fix Weird ANR
                /*runBlocking {
                    basicFragmentCallback?.getPlaylist()?.collect {
                        nowPlayingAdapter.submitList(it.songs.toList())
                    }
                }*/
            }
        })
    }

}