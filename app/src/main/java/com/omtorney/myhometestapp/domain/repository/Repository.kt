package com.omtorney.myhometestapp.domain.repository

import com.omtorney.myhometestapp.domain.model.Camera
import com.omtorney.myhometestapp.domain.model.Door
import com.omtorney.myhometestapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun fetchRemoteCameras(): Flow<Resource<List<Camera>>>
    suspend fun getLocalCameras(): Flow<List<Camera>>
    suspend fun addCameras(cameras: List<Camera>)
    suspend fun updateCameraFavorite(cameraId: Int)
    suspend fun deleteCamera(camera: Camera)

    suspend fun fetchRemoteDoors(): Flow<Resource<List<Door>>>
    suspend fun getLocalDoors(): Flow<List<Door>>
    suspend fun addDoors(doors: List<Door>)
    suspend fun updateDoorFavorite(door: Door, favorite: Boolean)
    suspend fun updateDoorName(door: Door)
    suspend fun deleteDoor(door: Door)
}
