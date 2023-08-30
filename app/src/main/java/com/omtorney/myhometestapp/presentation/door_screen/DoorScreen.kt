package com.omtorney.myhometestapp.presentation.door_screen

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
import com.omtorney.myhometestapp.data.local.model.Door

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorScreen(
    doorState: DoorScreenState,
    onRefresh: () -> Unit,
    onUpdateFavorite: (Door, Boolean) -> Unit,
    onUpdateName: (Door, String) -> Unit
) {
    val context = LocalContext.current
    val groupedDoors = doorState.data.entries.toList()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = doorState.isRefreshing,
        onRefresh = onRefresh
    )

    LaunchedEffect(doorState.error) {
        doorState.error?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            groupedDoors.forEach { (room, doors) ->
                item {
                    Text(
                        text = room,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
                items(doors) { d ->
                    DoorCard(
                        door = d,
                        onUpdateFavorite = { door, fav ->
                            onUpdateFavorite(door, fav)
                        },
                        onUpdateName = { door, name ->
                            onUpdateName(door, name)
                        }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = doorState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if (doorState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
