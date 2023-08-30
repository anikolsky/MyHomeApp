package com.omtorney.myhometestapp.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omtorney.myhometestapp.R
import com.omtorney.myhometestapp.data.local.model.Camera
import com.omtorney.myhometestapp.data.local.model.Door
import com.omtorney.myhometestapp.presentation.camera_screen.CameraScreen
import com.omtorney.myhometestapp.presentation.camera_screen.CameraScreenState
import com.omtorney.myhometestapp.presentation.door_screen.DoorScreen
import com.omtorney.myhometestapp.presentation.door_screen.DoorScreenState
import com.omtorney.myhometestapp.presentation.theme.TitleColor

@Composable
fun HomeScreen(
    cameraState: CameraScreenState,
    doorState: DoorScreenState,
    onCameraRefresh: () -> Unit,
    onDoorRefresh: () -> Unit,
    onCameraFavoriteUpdate: (Camera) -> Unit,
    onDoorFavoriteUpdate: (Door, Boolean) -> Unit,
    onDoorNameUpdate: (Door, String) -> Unit
) {
    var tabState by remember { (mutableIntStateOf(0)) }
    val tabTitles = listOf(
        stringResource(R.string.cameras),
        stringResource(R.string.doors)
    )

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        ) {
            Text(
                text = stringResource(R.string.my_home),
                fontSize = 21.sp,
                color = TitleColor,
            )
        }
        TabRow(
            selectedTabIndex = tabState,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = tabState == index,
                    onClick = { tabState = index }
                ) {
                    Text(
                        text = title,
                        fontSize = 17.sp,
                        color = TitleColor,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        when (tabState) {
            0 -> CameraScreen(
                cameraState = cameraState,
                onRefresh = onCameraRefresh,
                onFavoriteClick = { onCameraFavoriteUpdate(it) }
            )

            1 -> DoorScreen(
                doorState = doorState,
                onRefresh = onDoorRefresh,
                onUpdateFavorite = { door, fav ->
                    onDoorFavoriteUpdate(door, fav)
                },
                onUpdateName = { door, name ->
                    onDoorNameUpdate(door, name)
                }
            )
        }
    }
}
