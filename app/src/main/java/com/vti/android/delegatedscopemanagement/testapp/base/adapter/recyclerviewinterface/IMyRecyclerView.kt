package com.vti.android.delegatedscopemanagement.testapp.base.adapter.recyclerviewinterface

interface IMyRecyclerView<U> {
    fun getListData(): List<U>
    fun setListData(listData: List<U>)
}