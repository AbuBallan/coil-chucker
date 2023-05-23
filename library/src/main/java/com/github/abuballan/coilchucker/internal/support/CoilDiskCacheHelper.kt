package com.github.abuballan.coilchucker.internal.support

import android.content.Context
import com.github.abuballan.coilchucker.internal.ui.disk.DiskCacheImage
import coil.imageLoader

class CoilDiskCacheHelper constructor(
    context: Context
) {

    private val diskCache = context.imageLoader.diskCache

    fun getDiskCacheSize() = diskCache?.size ?: 0

    fun getDiskCacheMaxSize() = diskCache?.maxSize ?: 0

    fun getDiskCacheImageList(): List<DiskCacheImage> =
        diskCache?.let { diskCache ->
            val images = diskCache.directory.toFile().listFiles { _, name ->
                name?.endsWith(".1") ?: false
            }
            images.map {
                DiskCacheImage(it)
            }
        } ?: listOf()
}