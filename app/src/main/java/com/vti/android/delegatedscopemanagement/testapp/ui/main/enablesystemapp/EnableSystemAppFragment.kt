package com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.layoutmanager.CustomLinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentEnableSystemAppBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp.contract.EnableSystemAppViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp.contract.impl.EnableSystemAppViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnableSystemAppFragment : Fragment() {
    private lateinit var binding: FragmentEnableSystemAppBinding
    private val vm: EnableSystemAppViewModel by viewModels<EnableSystemAppViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentEnableSystemAppBinding.inflate(inflater, container, false).also {
            binding = it
            binding.vm = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        handleEvent()
    }

    private fun setupUi() {
        val logs = mutableListOf<Log>()
        adapter = LogAdapter()
        adapter.setListData(logs)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = CustomLinearLayoutManager(requireContext())
        }
    }

    private fun handleEvent() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            textField.setEndIconOnClickListener {
                vm?.enableApp()
            }
        }
        vm.log().observe(viewLifecycleOwner) { log ->
            adapter.addLog(log)
            adapter.notifyItemChanged(adapter.itemCount)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
    }
}