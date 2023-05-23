package com.github.abuballan.coilchucker.internal.ui.memory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ProgressIndicatorDefaults.linearTrackColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.abuballan.coilchucker.internal.ui.theme.CoilChuckerTheme
import kotlin.math.roundToInt

@Composable
fun MemoryCacheScreen(
    memoryCacheUiState: MemoryCacheUiState?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (memoryCacheUiState != null && memoryCacheUiState.memoryMaxSize != 0)
            item {
                MemoryCacheIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    memorySize = memoryCacheUiState.memorySize.toLong(),
                    memoryMaxSize = memoryCacheUiState.memoryMaxSize.toLong()
                )
            }

        items(memoryCacheUiState?.memoryCacheImageList ?: listOf()) { memoryCacheImage ->
            MemoryCacheItem(memoryCacheImage = memoryCacheImage)
        }

    }
}

@Composable
fun MemoryCacheIndicator(
    modifier: Modifier = Modifier,
    memorySize: Long,
    memoryMaxSize: Long
) {
    ConstraintLayout(modifier = modifier) {
        val (progressBgRef, progressRef, progressTextRef, descriptionRef, dividerRef) = createRefs()
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .constrainAs(progressBgRef) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                },
            progress = 100f,
            color = linearTrackColor
        )
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .constrainAs(progressRef) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                },
            progress = memorySize.toFloat() / memoryMaxSize,
        )
        Text(
            modifier = Modifier.constrainAs(progressTextRef) {
                centerTo(progressRef)
            },
            text = "${(memorySize.toFloat() / memoryMaxSize * 100).roundToInt()}%"
        )
        Text(
            modifier = Modifier.constrainAs(descriptionRef) {
                centerVerticallyTo(parent)
                linkTo(
                    start = progressBgRef.end,
                    end = parent.end,
                    startMargin = 8.dp
                )
                width = Dimension.fillToConstraints
            },
            text = "Coil located $memorySize bytes of $memoryMaxSize bytes."
        )

        Divider(
            modifier = modifier.constrainAs(dividerRef) {
                top.linkTo(progressBgRef.bottom, 16.dp)
            }
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun MemoryCacheScreenPreview() {
    CoilChuckerTheme {
        MemoryCacheScreen(
            memoryCacheUiState = MemoryCacheUiState(
                memorySize = 50,
                memoryMaxSize = 100,
                memoryCacheImageList = listOf()
            )
        )
    }
}
