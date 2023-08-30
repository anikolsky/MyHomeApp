package com.omtorney.myhometestapp.presentation.door_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.TextField
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.omtorney.myhometestapp.R
import com.omtorney.myhometestapp.data.local.model.Door
import com.omtorney.myhometestapp.presentation.theme.ShadowColor
import com.omtorney.myhometestapp.presentation.theme.SurfaceColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorCard(
    door: Door,
    onUpdateFavorite: (Door, Boolean) -> Unit,
    onUpdateName: (Door, String) -> Unit
) {
    val swipeableState = rememberSwipeableState(0)
//    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
//    val screenSizePx = with(LocalDensity.current) { screenSizeDp.toPx() }
//    val anchors = mapOf(0f to 0, -screenSizePx to 1, screenSizePx to 2)
    val anchors = mapOf(0f to 0, -250f to 1)
    var editDialogState by remember { mutableStateOf(false) }
    var doorName by remember { mutableStateOf(door.name) }
    var doorFavorite by remember { mutableStateOf(door.isFavorite) }

    Box(
        modifier = Modifier
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.2f) }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = stringResource(R.string.edit_name),
                modifier = Modifier.clickable { editDialogState = true }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = if (door.isFavorite) {
                    painterResource(R.drawable.ic_favorite_active)
                } else {
                    painterResource(R.drawable.ic_favorite_inactive)
                },
                contentDescription = stringResource(R.string.favorite),
                modifier = Modifier.clickable { onUpdateFavorite(door, doorFavorite) }
            )
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceColor),
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = ShadowColor
                )
        ) {
            Column {
                Box {
                    if (door.preview.isNotEmpty()) {
                        SubcomposeAsyncImage(
                            model = door.preview,
                            contentDescription = stringResource(R.string.door_preview),
                            contentScale = ContentScale.FillWidth,
                            loading = { CircularProgressIndicator(modifier = Modifier.padding(100.dp)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.ic_button_play),
                            contentDescription = stringResource(R.string.play),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        if (door.isFavorite) {
                            Image(
                                painter = painterResource(R.drawable.ic_favorite_star),
                                contentDescription = stringResource(R.string.favorite),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 18.dp, vertical = 28.dp)
                    ) {
                        Column {
                            Text(text = door.name)
                            if (door.preview.isNotEmpty()) {
                                Text(
                                    text = stringResource(R.string.is_online),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        Image(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = stringResource(R.string.locked),
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                }
            }
        }
    }

    if (editDialogState) {
        AlertDialog(
            onDismissRequest = { editDialogState = false },
            backgroundColor = SurfaceColor,
            shape = RoundedCornerShape(12.dp),
            text = {
                TextField(
                    value = doorName,
                    onValueChange = { doorName = it }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateName(door, doorName)
                        editDialogState = false
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Text(text = stringResource(R.string.save)) }
            },
            dismissButton = {
                Button(
                    onClick = {
                        editDialogState = false
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Text(text = stringResource(R.string.cancel)) }
            }
        )
    }
}
