package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentPolicyBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.contract.PolicyViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.contract.impl.PolicyViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyFragment : Fragment() {
    private lateinit var binding: FragmentPolicyBinding
    private val vm: PolicyViewModel by viewModels<PolicyViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPolicyBinding.inflate(inflater, container, false).also {
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
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleEvent() {
        vm.log().observe(viewLifecycleOwner) { log ->
            adapter.addLog(log)
            adapter.notifyItemChanged(adapter.itemCount)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
    }
}