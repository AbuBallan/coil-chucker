package com.github.abuballan.coilchucker.internal.support

import android.content.Context
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import com.github.abuballan.coilchucker.internal.data.repository.RepositoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoilChuckerCollector(
    context: Context
) {

    private val notificationHelper: NotificationHelper = NotificationHelper(context)
    private val scope = MainScope()

    init {
        RepositoryProvider.initialize(context)
    }

    internal fun onRequestSent(transaction: HttpTransaction) {
        scope.launch {
            withContext(Dispatchers.IO) {
                RepositoryProvider.transaction().insertTransaction(transaction)
            }
            notificationHelper.show(transaction)

            // TODO:
//            withContext(Dispatchers.IO) {
//                retentionManager.doMaintenance()
//            }
        }
    }

    internal fun onResponseReceived(transaction: HttpTransaction) {
        scope.launch {
            val updated = withContext(Dispatchers.IO) {
                RepositoryProvider.transaction().updateTransaction(transaction)
            }
            if (updated > 0) {
                notificationHelper.show(transaction)
            }
        }
    }
}