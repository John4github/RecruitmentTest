package com.crypto.recruitment.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.crypto.recruitment.test.R
import com.crypto.recruitment.test.databinding.CurrencyListFragmentBinding
import com.crypto.recruitment.test.db.entity.CurrencyInfo

/**
 * CurrencyListFragment should receive an array list of CurrencyInfo to create the ui.
 * CurrencyListFragment should provide a hook of item click listener to the parent.
 */
class CurrencyListFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyListFragment()
    }

    private lateinit var binding: CurrencyListFragmentBinding

    private lateinit var currencyAdapter: CurrencyAdapter

    var currencyClickCallback: CurrencyClickCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.currency_list_fragment, container, false)

        binding.isLoading = false
        binding.noData = true

        currencyAdapter = CurrencyAdapter(object : CurrencyClickCallback {
            override fun onClick(currencyInfo: CurrencyInfo?) {
                currencyClickCallback?.onClick(currencyInfo)
            }
        })
        binding.currencyList.adapter = currencyAdapter

        return binding.root
    }

    /**
     *  Receive an array list of CurrencyInfo to create the ui.
     */
    fun updateView(currencyInfoList: List<CurrencyInfo>?) {
        binding.isLoading = false

        val noData = currencyInfoList.isNullOrEmpty()
        binding.noData = noData
        if (!noData) {
            currencyAdapter.setCurrencyList(currencyInfoList)
        }

        binding.executePendingBindings()
    }

    fun showLoading() {
        binding.isLoading = true
        binding.executePendingBindings()
    }
}