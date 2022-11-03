package com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess

import android.content.pm.PackageManager
import android.os.Build
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
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentPackageAccessBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess.contract.PackageAccessViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess.contract.impl.PackageAccessViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageAccessFragment : Fragment() {
    private lateinit var binding: FragmentPackageAccessBinding
    private val vm: PackageAccessViewModel by viewModels<PackageAccessViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPackageAccessBinding.inflate(inflater, container, false).also {
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
            packageNameTextField.setSimpleItems(getInstalledPackageName())
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
}