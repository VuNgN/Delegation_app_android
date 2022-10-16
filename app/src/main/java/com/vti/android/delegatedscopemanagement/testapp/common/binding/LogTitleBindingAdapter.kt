package com.vti.android.delegatedscopemanagement.testapp.common.binding

import android.util.TypedValue
import androidx.databinding.BindingAdapter
import com.vti.android.delegatedscopemanagement.testapp.R
import java.text.SimpleDateFormat
import java.util.*

object LogTitleBindingAdapter {
    @BindingAdapter(value = ["text", "isSuccess"], requireAll = false)
    @JvmStatic
    fun bindTextView(
        textView: com.google.android.material.textview.MaterialTextView,
        title: String?,
        state: Boolean
    ) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formatDate = formatter.format(date)
        val typeEnableValue = TypedValue()
        val typeDisableValue = TypedValue()
        textView.context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary,
            typeEnableValue,
            true
        )
        textView.context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorError,
            typeDisableValue,
            true
        )
        when (textView.id) {
            R.id.dateTextView -> textView.text = formatDate
            R.id.stateTextView -> textView.text =
                if (state) textView.context.getString(R.string.state_success)
                else textView.context.getString(R.string.state_failure)
            R.id.titleTextView -> if (title != null) textView.text = title
        }
        when (state) {
            true -> {
                textView.setTextColor(typeEnableValue.data)
            }
            false -> {
                textView.setTextColor(typeDisableValue.data)
            }
        }
    }
}