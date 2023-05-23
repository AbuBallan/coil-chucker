package com.github.abuballan.coilchucker.internal.ui.memory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MemoryCacheItem(memoryCacheImage: MemoryCacheImage) {
    memoryCacheImage.bitmap?.let { bitmap ->
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (datasourceRef, pathRef, imageRef, rowRef, dividerRef) = createRefs()
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            Text(
                modifier = Modifier.constrainAs(datasourceRef) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(startGuideline)
                },
                text = "[MEMORY]"
            )
            Text(
                modifier = Modifier.constrainAs(pathRef) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(datasourceRef.end, 8.dp)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                },
                text = memoryCacheImage.key
            )
            Image(modifier = Modifier.constrainAs(imageRef) {
                top.linkTo(pathRef.bottom, 8.dp)
                centerHorizontallyTo(parent)
            }, bitmap = bitmap.asImageBitmap(), contentDescription = null)
            Row(modifier = Modifier
                .constrainAs(rowRef) {
                    top.linkTo(imageRef.bottom, 8.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${memoryCacheImage.bitmap.width} x ${memoryCacheImage.bitmap.height}"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${memoryCacheImage.bitmap.byteCount} bytes"
                )
            }

            Divider(
                modifier = Modifier.constrainAs(dividerRef) {
                    top.linkTo(rowRef.bottom, 8.dp)
                }
            )
        }
    }
}