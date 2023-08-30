package com.omtorney.myhometestapp.data.remote.dto.camera

import kotlinx.serialization.Serializable

@Serializable
data class CameraResponse(
    val success: Boolean,
    val data: CameraData?
)
