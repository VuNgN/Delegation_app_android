package com.vungn.android.mybase.adapter.recyclerviewinterface

interface IMyRecyclerView<U> {
    fun getListData(): List<U>
    fun setListData(listData: List<U>)
}