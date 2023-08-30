package com.omtorney.myhometestapp.presentation.camera_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.omtorney.myhometestapp.R
import com.omtorney.myhometestapp.data.local.model.Camera
import com.omtorney.myhometestapp.presentation.theme.MyHomeTestAppTheme
import com.omtorney.myhometestapp.presentation.theme.ShadowColor
import com.omtorney.myhometestapp.presentation.theme.SurfaceColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CameraCard(
    camera: Camera,
    onFavoriteClick: () -> Unit
) {
    val swipeableState = rememberSwipeableState(0)
//    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
//    val screenSizePx = with(LocalDensity.current) { screenSizeDp.toPx() }
//    val anchors = mapOf(0f to 0, -screenSizePx to 1, screenSizePx to 2)
    val anchors = mapOf(0f to 0, -150f to 1)

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
                painter = if (camera.isFavorite) {
                    painterResource(R.drawable.ic_favorite_active)
                } else {
                    painterResource(R.drawable.ic_favorite_inactive)
                },
                contentDescription = stringResource(R.string.favorite),
                modifier = Modifier.clickable { onFavoriteClick() }
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
                    SubcomposeAsyncImage(
                        model = camera.preview,
                        contentDescription = stringResource(R.string.camera_preview),
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
                    if (camera.isRecording) {
                        Image(
                            painter = painterResource(R.drawable.ic_rec),
                            contentDescription = stringResource(R.string.recording),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        )
                    }
                    if (camera.isFavorite) {
                        Image(
                            painter = painterResource(R.drawable.ic_favorite_star),
                            contentDescription = stringResource(R.string.favorite),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }
                Text(
                    text = camera.name,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 26.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CameraCardPreview() {
    val camera = Camera().apply {
        id = 1
        name = "Camera 1"
        room = "Room 1"
        preview = "url"
        isFavorite = true
        isRecording = true
    }
    MyHomeTestAppTheme {
        Surface {
            CameraCard(camera, {})
        }
    }
}
