package com.omtorney.myhometestapp.presentation.camera_screen

import com.omtorney.myhometestapp.data.local.model.Camera

data class CameraScreenState(
    val data: Map<String, List<Camera>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
