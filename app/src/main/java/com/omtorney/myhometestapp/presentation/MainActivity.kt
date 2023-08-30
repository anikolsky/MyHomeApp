package com.omtorney.myhometestapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omtorney.myhometestapp.data.local.database.getRealmDatabase
import com.omtorney.myhometestapp.data.remote.KtorService
import com.omtorney.myhometestapp.data.repository.RepositoryImpl
import com.omtorney.myhometestapp.domain.repository.Repository
import com.omtorney.myhometestapp.presentation.home_screen.HomeScreen
import com.omtorney.myhometestapp.presentation.theme.MyHomeTestAppTheme
import com.omtorney.myhometestapp.presentation.camera_screen.CameraViewModel
import com.omtorney.myhometestapp.presentation.door_screen.DoorViewModel

class MainActivity : ComponentActivity() {

    private val service = KtorService.create()
    private val db = getRealmDatabase()

    private val cameraViewModel by viewModels<CameraViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: Repository = RepositoryImpl(service, db)
                return CameraViewModel(repository) as T
            }
        }
    }

    private val doorViewModel by viewModels<DoorViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: Repository = RepositoryImpl(service, db)
                return DoorViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyHomeTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val cameraState = cameraViewModel.cameraState.collectAsStateWithLifecycle().value
                    val doorState = doorViewModel.doorState.collectAsStateWithLifecycle().value

                    HomeScreen(
                        cameraState = cameraState,
                        doorState = doorState,
                        onCameraRefresh = cameraViewModel::getRemote,
                        onDoorRefresh = doorViewModel::getRemote,
                        onCameraFavoriteUpdate = cameraViewModel::update,
                        onDoorFavoriteUpdate = doorViewModel::updateFavorite,
                        onDoorNameUpdate = doorViewModel::updateName
                    )
                }
            }
        }
    }
}
