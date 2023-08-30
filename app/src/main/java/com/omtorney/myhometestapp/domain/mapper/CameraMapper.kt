package com.omtorney.myhometestapp.domain.mapper

import com.omtorney.myhometestapp.data.remote.dto.camera.CameraDto
import com.omtorney.myhometestapp.data.remote.dto.camera.CameraResponse
import com.omtorney.myhometestapp.data.local.model.Camera

fun CameraDto.toCamera(): Camera {
    return Camera().apply {
        id = this@toCamera.id
        name = this@toCamera.name
        room = this@toCamera.room ?: "Иное помещение"
        preview = snapshot
        isFavorite = favorites
        isRecording = rec
    }
}
