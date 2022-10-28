package com.vungn.android.mybase.adapter

import androidx.recyclerview.widget.RecyclerView
import com.vungn.android.mybase.adapter.recyclerviewinterface.IMyRecyclerView

abstract class RecyclerViewAdapter<U> : RecyclerView.Adapter<MyViewHolder>(),
    IMyRecyclerView<U> {
    private lateinit var listData: MutableList<U>

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
        holder.handleEvent(position)
    }

    override fun getItemCount(): Int = listData.size

    override fun getListData(): MutableList<U> = listData

    override fun setListData(listData: MutableList<U>) {
        this.listData = listData
    }
}
