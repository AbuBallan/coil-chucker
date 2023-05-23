package com.github.abuballan.coilchucker.internal.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction

@Dao
internal interface HttpTransactionDao {

    @Insert
    suspend fun insert(transaction: HttpTransaction): Long?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: HttpTransaction): Int

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<HttpTransaction>
}