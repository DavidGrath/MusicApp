package com.example.musicapp.ui.entities

data class Album(
    val name: String,
    val artist: String,
    val songs: Playlist,
    val releaseDate: Short,
    val artUri: String?
) {
}