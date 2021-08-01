package com.crypto.recruitment.test.repo

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ResourceUtils
import com.crypto.recruitment.test.db.AppDatabase
import com.crypto.recruitment.test.db.entity.CurrencyInfo

/**
 * The Currency repository. Responsible for managing Currency data.
 */
object CurrencyRepository {

    private const val TAG: String = "RecruitmentTest"

    fun findCurrencyInfoList(context: Context): List<CurrencyInfo> {

        val db = AppDatabase.getInstance(context)
        val currencyDao = db.currencyDao()

        val allCurrency = currencyDao.getAllCurrency()

        if (allCurrency.isNullOrEmpty()) {
            // init data while the db is empty
            initData(context)
            return findCurrencyInfoList(context)
        }

        return allCurrency
    }

    private fun initData(context: Context) {
        Log.d(TAG, "Init db data.")
        val dataString = ResourceUtils.readAssets2String("data.json")
        val database = AppDatabase.getInstance(context)
        database.currencyDao().insertAll(
            JSON.parseArray(
                dataString,
                CurrencyInfo::class.java
            )
        )
    }
}
