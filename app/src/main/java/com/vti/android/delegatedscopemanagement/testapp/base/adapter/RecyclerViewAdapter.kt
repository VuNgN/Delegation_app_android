package com.vti.android.delegatedscopemanagement.testapp.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.vti.android.delegatedscopemanagement.testapp.base.adapter.recyclerviewinterface.IMyRecyclerView
import com.vti.android.delegatedscopemanagement.testapp.base.adapter.recyclerviewinterface.IMyViewHolder

abstract class RecyclerViewAdapter<U> : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(),
    IMyRecyclerView<U> {
    private lateinit var listData: List<U>

    abstract class MyViewHolder(binding: ViewBinding) : ViewHolder(binding.root),
        IMyViewHolder

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
        holder.handleEvent(position)
    }

    override fun getItemCount(): Int = listData.size

    override fun getListData(): List<U> = listData

    override fun setListData(listData: List<U>) {
        this.listData = listData
    }
}
