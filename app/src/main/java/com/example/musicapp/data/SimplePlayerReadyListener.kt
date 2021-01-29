package com.example.musicapp.data

interface SimplePlayerReadyListener {
    // TODO Refine interface
    fun onPlayerStateReady(length: Int)
    fun onPlayerProgressChanged(timestamp: Int)
}