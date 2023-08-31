package com.omtorney.myhometestapp.presentation.door_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omtorney.myhometestapp.domain.model.Door
import com.omtorney.myhometestapp.domain.repository.Repository
import com.omtorney.myhometestapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DoorViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _doorState = MutableStateFlow(DoorScreenState())
    val doorState = _doorState.asStateFlow()

    init {
        getLocal()
        viewModelScope.launch(Dispatchers.IO) {
            val localDoorsEmpty = repository.getLocalDoors().first().isEmpty()
            if (localDoorsEmpty) getRemote()
        }
    }

    private fun getLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalDoors().collect { doors ->
                _doorState.update { it.copy(data = groupByRoom(doors), error = null) }
            }
        }
    }

    fun getRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchRemoteDoors().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _doorState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val doors = result.data ?: emptyList()
                        save(doors)
                        _doorState.update { it.copy(data = groupByRoom(doors), isLoading = false) }
                    }

                    is Resource.Error -> {
                        _doorState.update { it.copy(error = result.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun groupByRoom(data: List<Door>): Map<String, List<Door>> {
        return data.groupBy { it.room }
    }

    private suspend fun save(doors: List<Door>) = repository.addDoors(doors)

    fun updateFavorite(door: Door, favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDoorFavorite(door, favorite)
        }
    }

    fun updateName(door: Door) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDoorName(door)
        }
    }
}
