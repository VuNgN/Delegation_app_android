package com.vti.android.delegatedscopemanagement.testapp.data.model

class Counter {
    var value = 0
        @Synchronized get() = field

    @Synchronized
    fun updateCounter() {
        value++
    }
}