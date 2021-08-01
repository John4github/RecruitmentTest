package com.crypto.recruitment.test.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.crypto.recruitment.test.db.entity.CurrencyInfo
import com.crypto.recruitment.test.repo.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Currency list view model. For binding currency data and view.
 */
class CurrencyListViewModel : ViewModel() {

    companion object {
        const val TAG: String = "RecruitmentTest"
    }

    val currencyInfoData: MutableLiveData<List<CurrencyInfo>> =
        MutableLiveData<List<CurrencyInfo>>()

    private var loadJob: Job? = null

    /**
     * To load currency info data.
     * @param context
     */
    fun loadCurrencyInfoData(context: Context) {
        // Only one loading coroutines is supported to run.
        if (loadJob == null || loadJob?.isCompleted == true || loadJob?.isCancelled == true) {
            loadJob = viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    // find data from repository in IO thread. Not in UI thread.
                    val currencyInfoList = CurrencyRepository.findCurrencyInfoList(context)
                    Log.d(TAG, "find currency info list: ${JSON.toJSONString(currencyInfoList)}")
                    currencyInfoData.postValue(currencyInfoList)
                }
            }
        } else {
            Log.d(TAG, "Pre job is running, ignore this request.")
        }
    }

    private var sortJob: Job? = null

    /**
     * To sort currency info data.
     */
    fun sortCurrencyInfoData() {
        // Only one sorting coroutines is supported to run.
        if (sortJob == null || sortJob?.isCompleted == true || sortJob?.isCancelled == true) {
            sortJob = viewModelScope.launch {
                Log.d(TAG, "Sorting...")
                withContext(Dispatchers.IO) {
                    val value = currencyInfoData.value
                    if (!value.isNullOrEmpty()) {
                        val newValue = value.sortedBy {
                            it.name
                        }
                        currencyInfoData.postValue(newValue)
                    }
                }
            }
        }
    }
}