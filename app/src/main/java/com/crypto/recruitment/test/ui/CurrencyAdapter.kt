package com.crypto.recruitment.test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.crypto.recruitment.test.R
import com.crypto.recruitment.test.databinding.CurrencyItemBinding
import com.crypto.recruitment.test.db.entity.CurrencyInfo
import com.crypto.recruitment.test.ui.CurrencyAdapter.CurrencyViewHolder
import java.util.*

class CurrencyAdapter(private val currencyClickCallback: CurrencyClickCallback) :
    RecyclerView.Adapter<CurrencyViewHolder>() {
    private var mCurrencyInfoList: ArrayList<CurrencyInfo> = ArrayList()
    fun setCurrencyList(currencyInfoList: List<CurrencyInfo>?) {
        mCurrencyInfoList.clear()
        mCurrencyInfoList.addAll(currencyInfoList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: CurrencyItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.currency_item,
                parent, false
            )
        binding.callback = currencyClickCallback
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.binding.currencyInfo = mCurrencyInfoList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mCurrencyInfoList.size
    }

    class CurrencyViewHolder(val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    init {
        setHasStableIds(false)
    }
}