package com.omtorney.myhometestapp.domain.model

data class Door(
    val id: Int,
    val name: String,
    val room: String,
    val preview: String,
    val isFavorite: Boolean,
)
