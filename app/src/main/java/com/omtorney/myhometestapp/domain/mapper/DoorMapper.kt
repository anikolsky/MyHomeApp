package com.omtorney.myhometestapp.domain.mapper

import com.omtorney.myhometestapp.data.local.dto.DoorRealm
import com.omtorney.myhometestapp.data.remote.dto.door.DoorData
import com.omtorney.myhometestapp.domain.model.Door

fun DoorData.toDoorRealm() = DoorRealm().apply {
    id = this@toDoorRealm.id
    name = this@toDoorRealm.name
    room = this@toDoorRealm.room ?: "Иное помещение"
    preview = snapshot ?: ""
    isFavorite = favorites
}

fun DoorData.toDoor() = Door(
    id = id,
    name = name,
    room = this@toDoor.room ?: "Иное помещение",
    preview = snapshot ?: "",
    isFavorite = favorites
)

fun DoorRealm.toDoor() = Door(
    id = id,
    name = name,
    room = room,
    preview = preview,
    isFavorite = isFavorite
)

fun Door.toDoorRealm() = DoorRealm().apply {
    id = this@toDoorRealm.id
    name = this@toDoorRealm.name
    room = this@toDoorRealm.room
    preview = this@toDoorRealm.preview
    isFavorite = this@toDoorRealm.isFavorite
}
