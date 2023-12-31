package com.omtorney.myhometestapp.data.repository

import com.omtorney.myhometestapp.domain.model.Door
import com.omtorney.myhometestapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface DoorRepository {

    suspend fun fetchRemote(): Flow<Resource<List<Door>>>
    suspend fun getLocal(): Flow<List<Door>>
    suspend fun add(doors: List<Door>)
    suspend fun updateFavorite(door: Door, favorite: Boolean)
    suspend fun updateName(door: Door)
    suspend fun delete(door: Door)
}
