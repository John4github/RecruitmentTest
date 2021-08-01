package com.crypto.recruitment.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crypto.recruitment.test.db.entity.CurrencyInfo

@Dao
interface CurrencyDao {

    @Query("select * from currency_info")
    fun getAllCurrency(): List<CurrencyInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyInfoList: List<CurrencyInfo>)
}