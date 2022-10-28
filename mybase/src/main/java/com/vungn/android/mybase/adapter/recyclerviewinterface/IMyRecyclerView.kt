package com.vungn.android.mybase.adapter.recyclerviewinterface

interface IMyRecyclerView<U> {
    fun getListData(): MutableList<U>
    fun setListData(listData: MutableList<U>)
}