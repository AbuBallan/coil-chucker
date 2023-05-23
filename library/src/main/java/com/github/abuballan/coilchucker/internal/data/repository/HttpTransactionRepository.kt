package com.github.abuballan.coilchucker.internal.data.repository

import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction

internal interface HttpTransactionRepository {

    suspend fun insertTransaction(transaction: HttpTransaction)

    suspend fun updateTransaction(transaction: HttpTransaction): Int

    suspend fun getAllTransactions(): List<HttpTransaction>
}