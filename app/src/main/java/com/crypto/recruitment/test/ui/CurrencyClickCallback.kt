package com.crypto.recruitment.test.ui

import com.crypto.recruitment.test.db.entity.CurrencyInfo

interface CurrencyClickCallback {

    fun onClick(currencyInfo: CurrencyInfo?)
}