package com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs

import android.app.admin.NetworkEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            val events: ArrayList<NetworkEvent> =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle?.getParcelableArrayList(
                        "com.vungn.delegation.NETWORK_EVENT",
                        NetworkEvent::class.java
                    )!!
                } else {
                    bundle?.getParcelableArrayList("com.vungn.delegation.NETWORK_EVENT")!!
                }
            vm.pushLog(events)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentNetworkingLogsBinding.inflate(inflater, container, false).also {
            binding = it
            binding.vm = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        handleEvent()
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            requireContext(),
            receiver,
            IntentFilter("com.vungn.delegation.NETWORK_LOGGING"),
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()
        try {
            requireActivity().unregisterReceiver(receiver)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "onDestroy: ${e.message}")
        }
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
    }

    companion object {
        private val TAG = NetworkingLogsFragment::class.simpleName
    }
}