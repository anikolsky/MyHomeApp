package com.omtorney.myhometestapp.presentation.camera_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omtorney.myhometestapp.domain.model.Camera

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CameraScreen(
    cameraState: CameraScreenState,
    onRefresh: () -> Unit,
    onFavoriteClick: (Camera) -> Unit
) {
    val context = LocalContext.current
    val groupedCameras = cameraState.data.entries.toList()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = cameraState.isRefreshing,
        onRefresh = onRefresh
    )

    LaunchedEffect(cameraState.error) {
        cameraState.error?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            groupedCameras.forEach { (room, cameras) ->
                item {
                    Text(
                        text = room,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
                items(cameras) { camera ->
                    CameraCard(
                        camera = camera,
                        onFavoriteClick = { onFavoriteClick(camera) }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = cameraState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if (cameraState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
