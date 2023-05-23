package com.github.abuballan.coilchucker.internal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.abuballan.coilchucker.internal.data.entity.HttpTransaction

@Database(entities = [HttpTransaction::class], version = 1)
internal abstract class CoilChuckerDatabase : RoomDatabase() {

    abstract fun transactionDao(): HttpTransactionDao

    companion object {

        private const val DB_NAME = "coil-chucker.db"

        fun create(applicationContext: Context): CoilChuckerDatabase {
            return Room.databaseBuilder(
                applicationContext,
                CoilChuckerDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}