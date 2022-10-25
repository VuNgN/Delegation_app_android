package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentGrantStateBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.GrantStateViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.impl.GrantStateViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GrantStateFragment : Fragment() {
    private lateinit var binding: FragmentGrantStateBinding
    private val vm: GrantStateViewModel by viewModels<GrantStateViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentGrantStateBinding.inflate(inflater, container, false).also {
            binding = it
            binding.vm = vm
            binding.lifecycleOwner = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        handleEvent()
    }

    private fun setupUi() {
        val items = arrayOf("Item 1", "Item 2", "Item 3")
        val logs = getLogs()
        val adapter = LogAdapter()
        adapter.setListData(logs)
        binding.apply {
            autoCompleteTextView.setSimpleItems(items)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleEvent() {
        binding.apply {
            textInputEditText.setOnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    true -> {}
                    false -> {
                        //TODO: list all permissions of inserted package name
                    }
                }
            }
        }
    }

    private fun getLogs() = listOf(
        Log("Hello would", true),
        Log("Oh, hi!", false),
        Log(
            "Exception: java.lang.NullPointerException: Parameter specified as non-null is null: method kotlin.jvm.internal.Intrinsics.checkNotNullParameter, parameter title",
            false
        ),
        Log("Oh, hi!", false),
    )
}