package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeData
import com.vti.android.delegatedscopemanagement.testapp.databinding.ItemDelegatedScopeBinding

class MenuAdapter(private val callback: ((data: ScopeData) -> Unit)? = null) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private lateinit var data: List<ScopeData>

    inner class ViewHolder(private val binding: ItemDelegatedScopeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                textButton.text = data[position].title
                (textButton as MaterialButton).icon = data[position].icon
            }
        }

        fun handleEvent(position: Int) {
            binding.textButton.setOnClickListener {
                callback?.invoke(data[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDelegatedScopeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.handleEvent(position)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<ScopeData>) {
        this.data = data
    }
}