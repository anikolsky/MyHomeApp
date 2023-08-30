package com.omtorney.myhometestapp.presentation.door_screen

import com.omtorney.myhometestapp.data.local.model.Door

data class DoorScreenState(
    val data: Map<String, List<Door>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
