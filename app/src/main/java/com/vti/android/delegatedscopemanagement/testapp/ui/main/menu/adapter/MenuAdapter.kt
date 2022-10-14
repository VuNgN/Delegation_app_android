package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeData
import com.vti.android.delegatedscopemanagement.testapp.databinding.ItemDelegatedScopeBinding

class MenuAdapter(private val callback: ((data: ScopeData) -> Unit)? = null) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private lateinit var datas: List<ScopeData>

    inner class ViewHolder(private val binding: ItemDelegatedScopeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val data = datas[position]
            binding.apply {
                titleText = data.title
                isEnable = data.state
                icon = data.icon
            }
        }

        fun handleEvent(position: Int) {
            itemView.setOnClickListener {
                callback?.invoke(datas[position])
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

    override fun getItemCount(): Int = datas.size

    fun setData(data: List<ScopeData>) {
        this.datas = data
    }
}