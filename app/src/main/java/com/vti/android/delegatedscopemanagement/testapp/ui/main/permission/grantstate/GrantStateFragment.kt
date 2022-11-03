package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.layoutmanager.CustomLinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentGrantStateBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.GrantState
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.GrantStateViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.impl.GrantStateViewModelImpl
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GrantStateFragment : Fragment() {
    private lateinit var binding: FragmentGrantStateBinding
    private val vm: GrantStateViewModel by viewModels<GrantStateViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        val logs = mutableListOf<Log>()
        adapter = LogAdapter()
        adapter.setListData(logs)
        binding.apply {
            packageNameTextField.setSimpleItems(getInstalledPackageName())
            autoCompleteTextView.setSimpleItems(getPermission())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = CustomLinearLayoutManager(requireContext())
        }
    }

    private fun handleEvent() {
        binding.apply {
            packageNameTextField.setOnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    true -> {}
                    false -> {
                        autoCompleteTextView.setSimpleItems(getPermission())
                    }
                }
            }
            defaultButton.setOnClickListener {
                vm?.grantState()?.postValue(GrantState.DEFAULT)
            }
            grantButton.setOnClickListener {
                vm?.grantState()?.postValue(GrantState.GRANT)
            }
            denyButton.setOnClickListener {
                vm?.grantState()?.postValue(GrantState.DENY)
            }
        }
        vm.grantState().observe(viewLifecycleOwner) {
            vm.setPermission()
        }
        vm.log().observe(viewLifecycleOwner) { log ->
            adapter.addLog(log)
            adapter.notifyItemChanged(adapter.itemCount)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
    }

    private fun getPermission(): Array<String> {
        val p = requireContext().packageManager
        val appInstall = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p.getInstalledPackages(PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong()))
        } else {
            @Suppress("DEPRECATION") p.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        }
        for (pInfo in appInstall) {
            if (pInfo.packageName.equals(vm.packageName().value)) {
                val reqPermission = pInfo.requestedPermissions
                android.util.Log.d(TAG, "getPermission: package name -> ${pInfo.packageName}")
                for (permission in reqPermission) {
                    android.util.Log.d(TAG, "getPermission: Permission list -> $permission")
                }
                return reqPermission
            }
        }
        return arrayOf()
    }

    private fun getInstalledPackageName(): Array<String> {
        val packageNames = mutableListOf<String>()
        val appInstall = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().packageManager.getInstalledPackages(
                PackageManager.PackageInfoFlags.of(
                    PackageManager.GET_PERMISSIONS.toLong()
                )
            )
        } else {
            @Suppress("DEPRECATION") requireContext().packageManager.getInstalledPackages(
                PackageManager.GET_PERMISSIONS
            )
        }
        for (pInfo in appInstall) {
            packageNames.add(pInfo.packageName)
        }
        return packageNames.toTypedArray()
    }

    companion object {
        private val TAG = GrantStateFragment::class.simpleName
    }
}