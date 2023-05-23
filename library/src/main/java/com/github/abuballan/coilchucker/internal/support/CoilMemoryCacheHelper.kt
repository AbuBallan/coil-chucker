package com.github.abuballan.coilchucker.internal.support

import android.content.Context
import com.github.abuballan.coilchucker.internal.ui.memory.MemoryCacheImage
import coil.imageLoader

class CoilMemoryCacheHelper(
    context: Context
) {

    private val memoryCache = context.imageLoader.memoryCache

    fun getMemoryCacheSize() = memoryCache?.size ?: 0

    fun getMemoryCacheMaxSize() = memoryCache?.maxSize ?: 0

    fun getMemoryCacheImageList(): List<MemoryCacheImage> =
        memoryCache?.let { memoryCache ->
            memoryCache.keys.map { key ->
                MemoryCacheImage(
                    key = key.key,
                    bitmap = memoryCache.get(key = key)?.bitmap
                )
            }
        } ?: listOf()
}