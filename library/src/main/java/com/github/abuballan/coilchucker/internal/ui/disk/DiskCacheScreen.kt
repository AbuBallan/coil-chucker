package com.github.abuballan.coilchucker.internal.ui.disk

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abuballan.coilchucker.internal.ui.memory.MemoryCacheIndicator

@Composable
fun DiskCacheScreen(diskCacheUiState: DiskCacheUiState?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (diskCacheUiState != null && diskCacheUiState.diskMaxSize != 0L)
            item {
                MemoryCacheIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    memorySize = diskCacheUiState.diskSize,
                    memoryMaxSize = diskCacheUiState.diskMaxSize
                )
            }
        items(diskCacheUiState?.diskCacheImageList ?: listOf()) { diskCacheImage ->
            DiskCacheItem(diskCacheImage = diskCacheImage)
        }
    }
}