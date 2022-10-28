package com.vti.android.delegatedscopemanagement.testapp.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.databinding.ItemLogBinding
import com.vungn.android.mybase.adapter.MyViewHolder
import com.vungn.android.mybase.adapter.RecyclerViewAdapter


class LogAdapter : RecyclerViewAdapter<Log>() {
    inner class ViewHolder(private val binding: ItemLogBinding) :
        MyViewHolder(binding) {
        override fun bind(position: Int) {
            binding.apply {
                title = getListData()[position].title
                isSuccess = getListData()[position].isSuccess
            }
        }

        override fun handleEvent(position: Int) {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun addLog(log: Log) {
        getListData().add(log)
    }
}