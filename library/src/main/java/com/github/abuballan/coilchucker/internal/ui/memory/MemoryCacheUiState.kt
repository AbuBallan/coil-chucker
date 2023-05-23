package com.github.abuballan.coilchucker.internal.ui.memory

import android.graphics.Bitmap

data class MemoryCacheImage(
    val key: String,
    val bitmap: Bitmap?
)

data class MemoryCacheUiState(
    val memorySize: Int,
    val memoryMaxSize: Int,
    val memoryCacheImageList: List<MemoryCacheImage>
)
