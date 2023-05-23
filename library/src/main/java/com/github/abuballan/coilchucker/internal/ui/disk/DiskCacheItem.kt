package com.github.abuballan.coilchucker.internal.ui.disk

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun DiskCacheItem(diskCacheImage: DiskCacheImage) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (datasourceRef, pathRef, imageRef, rowRef, dividerRef) = createRefs()
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)
        Text(
            modifier = Modifier.constrainAs(datasourceRef) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(startGuideline)
            },
            text = "[DISK]"
        )
        Text(
            modifier = Modifier.constrainAs(pathRef) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(datasourceRef.end, 8.dp)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
            },
            text = diskCacheImage.file.path
        )
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(diskCacheImage.file)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        Image(
            modifier = Modifier.constrainAs(imageRef) {
                top.linkTo(pathRef.bottom, 8.dp)
                centerHorizontallyTo(parent)
            },
            painter = painter, contentDescription = null
        )
        Row(modifier = Modifier
            .constrainAs(rowRef) {
                top.linkTo(imageRef.bottom, 8.dp)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
            }
        ) {
            if (painter.state is AsyncImagePainter.State.Success) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${(painter.state as AsyncImagePainter.State.Success).result.drawable.intrinsicWidth} x ${(painter.state as AsyncImagePainter.State.Success).result.drawable.intrinsicHeight}"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${diskCacheImage.file.length()} bytes"
                )
            }
        }

        Divider(
            modifier = Modifier.constrainAs(dividerRef) {
                top.linkTo(rowRef.bottom, 8.dp)
            }
        )
    }
}