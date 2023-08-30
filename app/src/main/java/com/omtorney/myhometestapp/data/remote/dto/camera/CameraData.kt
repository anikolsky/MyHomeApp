package com.omtorney.myhometestapp.data.remote.dto.camera

import kotlinx.serialization.Serializable

@Serializable
data class CameraData(
    val room: List<String>,
    val cameras: List<CameraDto>
)
