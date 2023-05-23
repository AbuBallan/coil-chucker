package com.github.abuballan.coilchucker.sample

import android.app.Application
import com.github.abuballan.coilchucker.api.CoilChuckerInterceptor
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun newImageLoader() = ImageLoader(this)
        .newBuilder()
        .components {
            add(
                CoilChuckerInterceptor(
                    context = this@App
                )
            )
        }
        .build()
}