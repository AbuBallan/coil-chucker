package com.github.abuballan.coilchucker.internal.ui.disk

import java.io.File

data class DiskCacheImage(
    val file: File
)

data class DiskCacheUiState(
    val diskSize: Long,
    val diskMaxSize: Long,
    val diskCacheImageList: List<DiskCacheImage>
)