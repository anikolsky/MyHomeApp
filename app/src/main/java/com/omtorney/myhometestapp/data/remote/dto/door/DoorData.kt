package com.omtorney.myhometestapp.data.remote.dto.door

import kotlinx.serialization.Serializable

@Serializable
data class DoorData(
    val name: String,
    val room: String?,
    val id: Int,
    val favorites: Boolean,
    val snapshot: String? = null
)
