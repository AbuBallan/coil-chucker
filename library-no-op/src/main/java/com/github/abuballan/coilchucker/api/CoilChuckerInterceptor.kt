package com.github.abuballan.coilchucker.api

import android.content.Context
import coil.intercept.Interceptor
import coil.request.ImageResult

/**
 * No-op implementation.
 */
@Suppress("UNUSED_PARAMETER")
class CoilChuckerInterceptor(
    context: Context
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        return chain.proceed(chain.request)
    }
}