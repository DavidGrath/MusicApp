package com.example.musicapp.ui.entities

data class Song(
    val duration: Int,
    val artist_s: String,
    val uri: String,
    val metadata: Map<String, String>
) {
}