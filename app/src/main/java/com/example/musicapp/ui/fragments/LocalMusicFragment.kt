package com.example.musicapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import kotlinx.android.synthetic.main.fragment_local_music.*

class LocalMusicFragment : Fragment(), View.OnClickListener {


    interface LocalMusicCallback {
        fun onSongSelected(uri: String)
    }

    var uri: String? = null
    val SONG_REQUEST_CODE = 100
    var localMusicCallback: LocalMusicCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localMusicCallback = context as LocalMusicCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_local_music, container, false)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it) {
                button_choose_song -> {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.setType("audio/*")
                    startActivityForResult(intent, SONG_REQUEST_CODE)
                }
                button_play_song -> {
                    localMusicCallback?.onSongSelected(uri!!)
                }
                else -> {

                }
            }
        }
    }


}