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
            com.google.android.material.R.attr.colorOnSurfaceVariant,
            typeDisableValue,
            true
        )
        when (state) {
            true -> {
                textView.text = textView.context.getString(R.string.state_enable)
                textView.setTextColor(typeEnableValue.data)
                textView.alpha = 1F
            }
            false -> {
                textView.text = textView.context.getString(R.string.state_disable)
                textView.setTextColor(typeDisableValue.data)
                textView.alpha = 0.6F
            }
        }
    }
}