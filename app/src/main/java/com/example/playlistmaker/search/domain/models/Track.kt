package com.example.playlistmaker.search.domain.models

import java.io.Serializable

data class Track (
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: String,
    val collectionName : String,
    val releaseDate: String,
    val primaryGenreName : String,
    val country: String,
    val previewUrl: String,

): Serializable{
    fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
    var isFavorite: Boolean = false
}