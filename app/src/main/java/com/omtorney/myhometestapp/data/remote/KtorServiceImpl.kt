package com.omtorney.myhometestapp.data.remote

import com.omtorney.myhometestapp.data.remote.dto.camera.CameraResponse
import com.omtorney.myhometestapp.data.remote.dto.door.DoorResponse
import com.omtorney.myhometestapp.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.delay

class KtorServiceImpl(
    private val client: HttpClient
) : KtorService {

    override suspend fun fetchCameras(): CameraResponse {
        delay(1000L)
        return client.get {
            url(Constants.CAMERAS)
        }.body()
    }

    override suspend fun fetchDoors(): DoorResponse {
        delay(1000L)
        return client.get {
            url(Constants.DOORS)
        }.body()
    }
}
