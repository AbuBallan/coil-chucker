package com.github.abuballan.coilchucker.internal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
internal class HttpTransaction (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "path") var path: String?,
    @ColumnInfo(name = "datasource") var datasource: String?,
    @ColumnInfo(name = "tookMs") var tookMs: Long?,
    @ColumnInfo(name = "error") var error: String?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "height") var height: Long?,
    @ColumnInfo(name = "width") var width: Long?,
) {
    @Ignore
    constructor(): this(
        path = null,
        datasource = null,
        tookMs = null,
        error = null,
        date = null,
        height = null,
        width = null
    )

    enum class Status {
        Requested,
        Complete,
        Failed
    }

    val status: Status
        get() = when {
            error != null -> Status.Failed
            datasource == null -> Status.Requested
            else -> Status.Complete
        }

    val notificationText: String
        get() {
            return when (status) {
                Status.Failed -> " ! ! !  $path"
                Status.Requested -> " . . .  $path"
                else -> "[$datasource] $path"
            }
        }
}