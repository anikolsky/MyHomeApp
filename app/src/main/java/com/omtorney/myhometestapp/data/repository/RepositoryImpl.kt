package com.omtorney.myhometestapp.data.repository

import com.omtorney.myhometestapp.data.remote.KtorService
import com.omtorney.myhometestapp.domain.model.Camera
import com.omtorney.myhometestapp.domain.model.Door
import com.omtorney.myhometestapp.domain.repository.Repository
import com.omtorney.myhometestapp.util.Resource
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    service: KtorService,
    db: Realm
) : Repository {

    private val cameraRepository: CameraRepository = CameraRepositoryImpl(service, db)
    private val doorRepository: DoorRepository = DoorRepositoryImpl(service, db)

    override suspend fun fetchRemoteCameras(): Flow<Resource<List<Camera>>> {
        return cameraRepository.fetchRemote()
    }

    override suspend fun getLocalCameras(): Flow<List<Camera>> {
        return cameraRepository.getLocal()
    }

    override suspend fun addCameras(cameras: List<Camera>) {
        return cameraRepository.add(cameras)
    }

    override suspend fun updateCameraFavorite(cameraId: Int) {
        return cameraRepository.update(cameraId)
    }

    override suspend fun deleteCamera(camera: Camera) {
        return cameraRepository.delete(camera)
    }

    override suspend fun fetchRemoteDoors(): Flow<Resource<List<Door>>> {
        return doorRepository.fetchRemote()
    }

    override suspend fun getLocalDoors(): Flow<List<Door>> {
        return doorRepository.getLocal()
    }

    override suspend fun addDoors(doors: List<Door>) {
        return doorRepository.add(doors)
    }

    override suspend fun updateDoorFavorite(door: Door, favorite: Boolean) {
        return doorRepository.updateFavorite(door, favorite)
    }

    override suspend fun updateDoorName(door: Door) {
        return doorRepository.updateName(door)
    }

    override suspend fun deleteDoor(door: Door) {
        return doorRepository.delete(door)
    }
}
