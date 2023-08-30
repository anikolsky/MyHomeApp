package com.omtorney.myhometestapp.data.repository

import com.omtorney.myhometestapp.data.local.model.Camera
import com.omtorney.myhometestapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CameraRepository {

    suspend fun fetchRemote(): Flow<Resource<List<Camera>>>
    suspend fun getLocal(): Flow<List<Camera>>
    suspend fun add(cameras: List<Camera>)
    suspend fun update(cameraId: Int)
    suspend fun delete(camera: Camera)
}
