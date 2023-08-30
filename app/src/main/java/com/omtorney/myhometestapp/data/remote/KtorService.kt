package com.omtorney.myhometestapp.data.remote

import com.omtorney.myhometestapp.data.remote.dto.camera.CameraResponse
import com.omtorney.myhometestapp.data.remote.dto.door.DoorResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface KtorService {

    suspend fun fetchCameras(): CameraResponse

    suspend fun fetchDoors(): DoorResponse

    companion object {
        fun create(): KtorService {
            return KtorServiceImpl(
                client = HttpClient(Android) {
                    engine {
                        connectTimeout = 5000
                        socketTimeout = 5000
                    }
                    install(ContentNegotiation) {
                        json(Json {
                            isLenient = true
                        })
                    }
                }
            )
        }
    }
}
