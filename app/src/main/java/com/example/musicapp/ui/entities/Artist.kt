package com.example.musicapp.ui.entities

data class Artist(
    val name: String,
    val album: Array<Album>,
    val profileUri: String
) {
}