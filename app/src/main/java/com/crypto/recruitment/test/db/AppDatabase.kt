package com.crypto.recruitment.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crypto.recruitment.test.db.dao.CurrencyDao
import com.crypto.recruitment.test.db.entity.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    // double check if the instance is null。
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app.db" // database name
                        ).build()
                    }
                }
            }
            return instance as AppDatabase
        }
    }
}