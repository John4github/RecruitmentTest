package com.crypto.recruitment.test.ui

import android.view.View
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun View.getValidClickStateFlow(interval: Long = 1000L): StateFlow<Long> {

    val clickFlow = MutableStateFlow(0L)

    var preTime = 0L

    setOnClickListener {
        // Only one click event per second is valid.
        val curTime = System.currentTimeMillis()
        if (curTime - preTime > interval) {
            preTime = curTime
            clickFlow.value = curTime
        }
    }

    return clickFlow

}