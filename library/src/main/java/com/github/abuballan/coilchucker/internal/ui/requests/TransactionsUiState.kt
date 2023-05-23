package com.github.abuballan.coilchucker.internal.ui.requests

import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction

internal data class TransactionsUiState(
    val transactions: List<HttpTransaction>
)