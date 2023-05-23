package com.github.abuballan.coilchucker.api

import android.content.Context
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import com.github.abuballan.coilchucker.internal.support.CoilChuckerCollector
import com.github.abuballan.coilchucker.internal.support.RequestProcessor
import com.github.abuballan.coilchucker.internal.support.ResponseProcessor
import coil.imageLoader
import coil.intercept.Interceptor
import coil.request.ImageResult
import java.io.IOException

class CoilChuckerInterceptor(
    context: Context
) : Interceptor {

    private val collector = CoilChuckerCollector(context)

    private val requestProcessor = RequestProcessor(
        collector = collector
    )

    private val responseProcessor = ResponseProcessor(
        collector = collector
    )

    private val diskCachePath by lazy {
        context.imageLoader.diskCache?.directory?.toString()
    }

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val transaction = HttpTransaction()
        val sentRequestAtMillis = System.currentTimeMillis()
        val request = chain.request
        if (diskCachePath == null || !request.data.toString().startsWith(diskCachePath !!))
            requestProcessor.process(request, transaction)
        val response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            transaction.error = e.toString()
            collector.onResponseReceived(transaction)
            throw e
        }
        return responseProcessor.process(response, transaction, sentRequestAtMillis)
    }
}