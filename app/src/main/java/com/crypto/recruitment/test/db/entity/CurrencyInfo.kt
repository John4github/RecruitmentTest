package com.crypto.recruitment.test.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info")
class CurrencyInfo {

    @PrimaryKey
    @NonNull
    var id: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "symbol")
    var symbol: String? = null
}