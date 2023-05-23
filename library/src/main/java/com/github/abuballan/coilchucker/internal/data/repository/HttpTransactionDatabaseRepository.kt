package com.github.abuballan.coilchucker.internal.data.repository

import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import com.github.abuballan.coilchucker.internal.data.room.CoilChuckerDatabase

internal class HttpTransactionDatabaseRepository(private val database: CoilChuckerDatabase) :
    HttpTransactionRepository {

    private val transactionDao get() = database.transactionDao()

    override suspend fun insertTransaction(transaction: HttpTransaction) {
        val id = transactionDao.insert(transaction)
        transaction.id = id ?: 0
    }

    override suspend fun updateTransaction(transaction: HttpTransaction): Int {
        return transactionDao.update(transaction)
    }

    override suspend fun getAllTransactions(): List<HttpTransaction> = transactionDao.getAll()
}