package com.omtorney.myhometestapp.domain.mapper

import com.omtorney.myhometestapp.data.local.model.Door
import com.omtorney.myhometestapp.data.remote.dto.door.DoorData

fun DoorData.toDoor(): Door {
    return Door().apply {
        id = this@toDoor.id
        name = this@toDoor.name
        room = this@toDoor.room ?: "Иное помещение"
        preview = snapshot ?: ""
        isFavorite = favorites
    }
}
