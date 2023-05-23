package com.github.abuballan.coilchucker.internal.support

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.LongSparseArray
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.github.abuballan.coilchucker.R
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction
import com.github.abuballan.coilchucker.internal.ui.CoilChuckerActivity
import java.util.HashSet

internal class NotificationHelper(val context: Context) {

    companion object {

        private const val TRANSACTIONS_CHANNEL_ID = "coil_chucker_transactions"
        private const val TRANSACTION_NOTIFICATION_ID = 1140

        private const val BUFFER_SIZE = 10
        private val transactionBuffer = LongSparseArray<HttpTransaction>()
        private val transactionIdsSet = HashSet<Long>()
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val transactionsScreenIntent by lazy {
        PendingIntent.getActivity(
            context,
            TRANSACTION_NOTIFICATION_ID,
            Intent(
                context,
                CoilChuckerActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val transactionsChannel = NotificationChannel(
                TRANSACTIONS_CHANNEL_ID,
                context.getString(R.string.coil_chucker_notification_category),
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannels(listOf(transactionsChannel))
        }
    }

    private fun addToBuffer(transaction: HttpTransaction) {
        if (transaction.id == 0L) {
            return
        }
        synchronized(transactionBuffer) {
            transactionIdsSet.add(transaction.id)
            transactionBuffer.put(transaction.id, transaction)
            if (transactionBuffer.size() > BUFFER_SIZE) {
                transactionBuffer.removeAt(0)
            }
        }
    }

    private fun canShowNotifications(): Boolean {
        return notificationManager.areNotificationsEnabled()
    }

    fun show(transaction: HttpTransaction) {
        addToBuffer(transaction)
        if (! CoilChuckerActivity.isInForeground && canShowNotifications()) {
            val builder =
                NotificationCompat.Builder(context, TRANSACTIONS_CHANNEL_ID)
                    .setContentIntent(transactionsScreenIntent)
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.chucker_ic_transaction_notification)
                    .setColor(ContextCompat.getColor(context, R.color.chucker_color_primary))
                    .setContentTitle(context.getString(R.string.coil_chucker_http_notification_title))
                    .setAutoCancel(true)
            val inboxStyle = NotificationCompat.InboxStyle()
            synchronized(transactionBuffer) {
                var count = 0
                (transactionBuffer.size() - 1 downTo 0).forEach { i ->
                    val bufferedTransaction = transactionBuffer.valueAt(i)
                    if ((bufferedTransaction != null) && count < BUFFER_SIZE) {
                        if (count == 0) {
                            builder.setContentText(bufferedTransaction.notificationText)
                        }
                        inboxStyle.addLine(bufferedTransaction.notificationText)
                    }
                    count++
                }
                builder.setStyle(inboxStyle)
                builder.setSubText(transactionIdsSet.size.toString())
            }
            notificationManager.notify(TRANSACTION_NOTIFICATION_ID, builder.build())
        }
    }
}