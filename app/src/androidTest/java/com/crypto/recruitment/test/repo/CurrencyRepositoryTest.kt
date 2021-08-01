package com.crypto.recruitment.test.repo

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alibaba.fastjson.JSON
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Currency repository test.
 */
@RunWith(AndroidJUnit4::class)
class CurrencyRepositoryTest {

    @Test
    fun testFindCurrencyInfoList() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        runBlocking {
            val currencyInfoList = CurrencyRepository.findCurrencyInfoList(appContext)
            Assert.assertNotNull(currencyInfoList)
            Log.d("RECRUITMENT_TEST", JSON.toJSONString(currencyInfoList))

            val size = currencyInfoList.size
            Assert.assertTrue(size > 0)
        }
    }
}