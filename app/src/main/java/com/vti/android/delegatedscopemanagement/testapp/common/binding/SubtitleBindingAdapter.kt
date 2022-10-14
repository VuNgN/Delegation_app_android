package com.vti.android.delegatedscopemanagement.testapp.common.binding

import android.util.TypedValue
import androidx.databinding.BindingAdapter
import com.vti.android.delegatedscopemanagement.testapp.R

object SubtitleBindingAdapter {
    @BindingAdapter("text")
    @JvmStatic
    fun bindTextView(
        textView: com.google.android.material.textview.MaterialTextView,
        state: Boolean
    ) {
        val typeEnableValue = TypedValue()
        val typeDisableValue = TypedValue()
        textView.context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary,
            typeEnableValue,
            true
        )
        textView.context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorSurfaceVariant,
            typeDisableValue,
            true
        )
        when (state) {
            true -> {
                textView.text = textView.context.getString(R.string.enable_state)
                textView.setTextColor(typeEnableValue.data)
            }
            false -> {
                textView.text = textView.context.getString(R.string.disable_state)
                textView.setTextColor(typeDisableValue.data)
            }
        }
    }
}