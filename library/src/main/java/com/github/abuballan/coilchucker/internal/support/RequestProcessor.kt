package com.github.abuballan.coilchucker.internal.support

import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import coil.request.ImageRequest

internal class RequestProcessor(
    private val collector: CoilChuckerCollector
) {

    fun process(request: ImageRequest, transaction: HttpTransaction) {
        transaction.path = request.data.toString()
        collector.onRequestSent(transaction)
    }

}