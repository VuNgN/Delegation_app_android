package com.vti.android.delegatedscopemanagement.testapp.common.binding

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.vti.android.delegatedscopemanagement.testapp.R

object EndIconBindingAdapter {
    @BindingAdapter("endIcon")
    @JvmStatic
    fun bindingEndIcon(
        textInputLayout: TextInputLayout,
        isBlocked: Boolean
    ) {
        textInputLayout.endIconDrawable = if (isBlocked) ContextCompat.getDrawable(
            textInputLayout.context,
            R.drawable.ic_round_lock_open_24
        ) else ContextCompat.getDrawable(textInputLayout.context, R.drawable.ic_round_send_24)
    }
}