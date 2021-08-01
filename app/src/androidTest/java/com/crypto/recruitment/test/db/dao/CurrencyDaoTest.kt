package com.crypto.recruitment.test.db.dao

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alibaba.fastjson.JSON
import com.crypto.recruitment.test.db.AppDatabase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Currency dao test.
 */
@RunWith(AndroidJUnit4::class)
class CurrencyDaoTest {
    @Test
    fun testGetAllCurrency() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val appDatabase = AppDatabase.getInstance(appContext)

        val currencyDao = appDatabase.currencyDao()
        val allCurrency = currencyDao.getAllCurrency()

        Assert.assertNotNull(allCurrency)
        Log.d("RECRUITMENT_TEST", JSON.toJSONString(allCurrency))
    }
}