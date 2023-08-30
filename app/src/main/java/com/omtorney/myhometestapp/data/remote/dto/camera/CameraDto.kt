package com.omtorney.myhometestapp.data.remote.dto.camera

import kotlinx.serialization.Serializable

@Serializable
data class CameraDto(
    val name: String,
    val snapshot: String,
    val room: String?,
    val id: Int,
    val favorites: Boolean,
    val rec: Boolean
)
