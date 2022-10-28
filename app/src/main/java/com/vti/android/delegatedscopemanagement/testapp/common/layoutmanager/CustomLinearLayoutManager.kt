package com.vti.android.delegatedscopemanagement.testapp.common.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun getPaddingBottom(): Int {
        return (height / 2) - 100
    }
}