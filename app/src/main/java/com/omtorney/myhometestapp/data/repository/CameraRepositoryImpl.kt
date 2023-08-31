package com.omtorney.myhometestapp.data.repository

import android.util.Log
import com.omtorney.myhometestapp.data.local.dto.CameraRealm
import com.omtorney.myhometestapp.data.remote.KtorService
import com.omtorney.myhometestapp.domain.mapper.toCamera
import com.omtorney.myhometestapp.domain.mapper.toCameraRealm
import com.omtorney.myhometestapp.domain.model.Camera
import com.omtorney.myhometestapp.util.Resource
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CameraRepositoryImpl(
    private val service: KtorService,
    private val db: Realm
) : CameraRepository {

    override suspend fun fetchRemote(): Flow<Resource<List<Camera>>> = flow {
        try {
            emit(Resource.Loading())
            val result = service.fetchCameras().data?.cameras?.map { it.toCamera() } ?: emptyList()
            emit(Resource.Success(data = result))
        } catch (e: HttpRequestTimeoutException) {
            emit(Resource.Error(message = "Request timed out: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun getLocal(): Flow<List<Camera>> {
        return db.query<CameraRealm>().asFlow().map { realmResults ->
            realmResults.list.map { it.toCamera() }
        }
    }

    override suspend fun add(cameras: List<Camera>) {
        cameras.forEach { camera ->
            db.write {
                copyToRealm(
                    instance = camera.toCameraRealm(),
                    updatePolicy = UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun update(cameraId: Int) {
        db.write {
            query<CameraRealm>(query = "id == $0", cameraId).first().find()?.apply {
                isFavorite = !isFavorite
            }
        }
    }

    override suspend fun delete(camera: Camera) {
        db.write {
            query<CameraRealm>(query = "id == $0", camera.id).first().find()?.let { camera ->
                try {
                    delete(camera)
                } catch (e: Exception) {
                    Log.d("CameraRepositoryImpl", e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}
