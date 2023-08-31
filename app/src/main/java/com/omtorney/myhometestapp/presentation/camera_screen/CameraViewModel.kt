package com.omtorney.myhometestapp.presentation.camera_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omtorney.myhometestapp.domain.model.Camera
import com.omtorney.myhometestapp.domain.repository.Repository
import com.omtorney.myhometestapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _cameraState = MutableStateFlow(CameraScreenState())
    val cameraState = _cameraState.asStateFlow()

    init {
        getLocal()
        viewModelScope.launch(Dispatchers.IO) {
            val localCamerasEmpty = repository.getLocalCameras().first().isEmpty()
            if (localCamerasEmpty) getRemote()
        }
    }

    private fun getLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalCameras().collect { cameras ->
                val groupedCameras = groupByRoom(cameras)
                _cameraState.update { it.copy(data = groupedCameras, error = null) }
            }
        }
    }

    fun getRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchRemoteCameras().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _cameraState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val cameras = result.data ?: emptyList()
                        save(cameras)
                        _cameraState.update { it.copy(data = groupByRoom(cameras), isLoading = false) }
                    }

                    is Resource.Error -> {
                        _cameraState.update { it.copy(error = result.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun groupByRoom(data: List<Camera>): Map<String, List<Camera>> {
        return data.groupBy { it.room }
    }

    private suspend fun save(cameras: List<Camera>) = repository.addCameras(cameras)

    fun update(camera: Camera) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCameraFavorite(camera.id)
        }
    }
}
