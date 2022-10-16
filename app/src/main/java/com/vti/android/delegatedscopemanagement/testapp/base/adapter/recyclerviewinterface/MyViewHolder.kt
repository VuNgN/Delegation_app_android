package com.vti.android.delegatedscopemanagement.testapp.base.adapter.recyclerviewinterface

interface IMyViewHolder {
    fun bind(position: Int)
    fun handleEvent(position: Int)
}