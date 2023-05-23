package com.github.abuballan.coilchucker.internal.support

import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import coil.request.ErrorResult
import coil.request.ImageResult
import coil.request.SuccessResult
import java.text.SimpleDateFormat
import java.util.Date

internal class ResponseProcessor(
    private val collector: CoilChuckerCollector
) {

    fun process(response: ImageResult, transaction: HttpTransaction, sentRequestAtMillis: Long): ImageResult {
        when (response) {
            is SuccessResult -> {
                val receivedResponseAtMillis = System.currentTimeMillis()
                transaction.datasource = response.dataSource.toString()
                transaction.tookMs = receivedResponseAtMillis - sentRequestAtMillis
                transaction.date = SimpleDateFormat("hh:MM a").format(Date())
                transaction.height = response.drawable.intrinsicHeight.toLong()
                transaction.width = response.drawable.intrinsicWidth.toLong()
            }

            is ErrorResult -> {
                transaction.error = response.throwable.message
            }
        }
        collector.onResponseReceived(transaction)
        return response
    }
}