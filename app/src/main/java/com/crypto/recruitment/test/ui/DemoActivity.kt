package com.crypto.recruitment.test.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.crypto.recruitment.test.R
import com.crypto.recruitment.test.db.entity.CurrencyInfo
import com.crypto.recruitment.test.vm.CurrencyListViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * DemoActivity should provide 1 dataset, Currency List A of CurrencyInfo to CurrencyListFragment and the dataset should be queried from local db.
 * DemoActivity should provide 2 buttons to do the demo.
 * - First button to load the data and display.
 * - Second button for sorting currency list.
 */
class DemoActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "RecruitmentTest"
    }

    private lateinit var viewModel: CurrencyListViewModel

    private val currencyClickCallback: CurrencyClickCallback = object : CurrencyClickCallback {
        override fun onClick(currencyInfo: CurrencyInfo?) {
            // Make sure the interface is visible before prompting。
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                currencyInfo?.let {
                    Toast.makeText(
                        this@DemoActivity as Context,
                        "${it.name} clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val currencyListFragment = CurrencyListFragment.newInstance()
            currencyListFragment.currencyClickCallback = currencyClickCallback
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, currencyListFragment)
                .commitNow()
        }

        // build view model
        viewModel = ViewModelProvider(this).get(CurrencyListViewModel::class.java)
        viewModel.currencyInfoData.observe(this, { currencyInfoList ->
            Log.d(TAG, "Demo receive currency info list.")
            val currencyListFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currencyListFragment != null && currencyListFragment is CurrencyListFragment) {
                currencyListFragment.updateView(currencyInfoList)
            }
        })

        // Make sure the click event is valid。
        lifecycleScope.launch {
            loadButton.getValidClickStateFlow()
                .filter { it > 0L }
                .collectLatest {
                    // Displays the loaded view during data loading。
                    showLoading()
                    viewModel.loadCurrencyInfoData(this@DemoActivity)
                }
        }

        // Make sure the click event is valid。
        lifecycleScope.launch {
            sortButton.getValidClickStateFlow()
                .filter { it > 0L }
                .collectLatest {
                    Log.d(TAG, "Collect new sort event.")
                    viewModel.sortCurrencyInfoData()
                }
        }
    }

    private fun showLoading() {
        val currencyListFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currencyListFragment != null && currencyListFragment is CurrencyListFragment) {
            currencyListFragment.showLoading()
        }
    }
}