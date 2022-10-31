package com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentBlockUninstallBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall.contract.BlockUninstallViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall.contract.impl.BlockUninstallViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockUninstallFragment : Fragment() {
    private lateinit var binding: FragmentBlockUninstallBinding
    private val vm: BlockUninstallViewModel by viewModels<BlockUninstallViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentBlockUninstallBinding.inflate(inflater, container, false).also {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
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
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            textField.setEndIconOnClickListener {
                vm?.blockUninstall()
            }
        }
        vm.log().observe(viewLifecycleOwner) { log ->
            adapter.addLog(log)
            adapter.notifyItemChanged(adapter.itemCount)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
        vm.packageName().observe(viewLifecycleOwner) {
            vm.getBlockUninstall()
        }
    }
}