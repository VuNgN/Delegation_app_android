package com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.layoutmanager.CustomLinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentNetworkingLogsBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.NetworkingLogsViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.impl.NetworkingLogsViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkingLogsFragment : Fragment() {
    private lateinit var binding: FragmentNetworkingLogsBinding
    private val vm: NetworkingLogsViewModel by viewModels<NetworkingLogsViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentNetworkingLogsBinding.inflate(inflater, container, false).also {
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
        vm.getState()
        adapter = LogAdapter()
        adapter.setListData(mutableListOf())
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
        }
        vm.log().observe(viewLifecycleOwner) { log ->
            adapter.addLog(log)
            adapter.notifyItemChanged(adapter.itemCount)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
        vm.isEnable().observe(viewLifecycleOwner) { isChecked ->
            binding.checkbox.isChecked = isChecked
        }
        vm.isLoading().observe(viewLifecycleOwner) { isLoading ->
            binding.indicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private val TAG = NetworkingLogsFragment::class.simpleName
    }
}