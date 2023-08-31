package com.omtorney.myhometestapp.domain.model

data class Camera(
    val id: Int,
    val name: String,
    val room: String,
    val preview: String,
    val isFavorite: Boolean,
    val isRecording: Boolean
)
