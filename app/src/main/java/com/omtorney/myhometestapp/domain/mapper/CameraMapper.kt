package com.omtorney.myhometestapp.domain.mapper

import com.omtorney.myhometestapp.data.remote.dto.camera.CameraDto
import com.omtorney.myhometestapp.data.local.dto.CameraRealm
import com.omtorney.myhometestapp.data.local.dto.DoorRealm
import com.omtorney.myhometestapp.domain.model.Camera
import com.omtorney.myhometestapp.domain.model.Door

fun CameraDto.toCameraRealm() = CameraRealm().apply {
    id = this@toCameraRealm.id
    name = this@toCameraRealm.name
    room = this@toCameraRealm.room ?: "Иное помещение"
    preview = snapshot
    isFavorite = favorites
    isRecording = rec
}

fun CameraDto.toCamera() = Camera(
    id = id,
    name = name,
    room = this@toCamera.room ?: "Иное помещение",
    preview = snapshot,
    isFavorite = favorites,
    isRecording = rec
)

fun Camera.toCameraRealm() = CameraRealm().apply {
    id = this@toCameraRealm.id
    name = this@toCameraRealm.name
    room = this@toCameraRealm.room
    preview = this@toCameraRealm.preview
    isFavorite = this@toCameraRealm.isFavorite
    isRecording = this@toCameraRealm.isRecording
}

fun CameraRealm.toCamera() = Camera(
    id = id,
    name = name,
    room = room,
    preview = preview,
    isFavorite = isFavorite,
    isRecording = isRecording
)
