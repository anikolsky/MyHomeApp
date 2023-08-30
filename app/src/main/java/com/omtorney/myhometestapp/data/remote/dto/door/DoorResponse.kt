package com.omtorney.myhometestapp.data.remote.dto.door

import kotlinx.serialization.Serializable

@Serializable
data class DoorResponse(
    val success: Boolean,
    val data: List<DoorData>?
)
