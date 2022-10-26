package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu

import android.app.admin.DevicePolicyManager
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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.R
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeData
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeType
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentMenuBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.adapter.MenuAdapter
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.MenuViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.impl.MenuViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val vm: MenuViewModel by viewModels<MenuViewModelImpl>()
    private lateinit var adapter: MenuAdapter
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            requireActivity().runOnUiThread {
                Log.d(TAG, "onReceive: ${intent?.action}")
                val scopes: ArrayList<String>? =
                    intent?.getStringArrayListExtra(DevicePolicyManager.EXTRA_DELEGATION_SCOPES)
                if (scopes != null) {
                    for (scope in scopes) Log.d(TAG, "onReceive: scope -> $scope")
                    val isCertChange = scopes.contains(DevicePolicyManager.DELEGATION_CERT_INSTALL)
                    val isManagedConfigChange =
                        scopes.contains(DevicePolicyManager.DELEGATION_APP_RESTRICTIONS)
                    val isBlockUninstallChange =
                        scopes.contains(DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL)
                    val isPermissionChange =
                        scopes.contains(DevicePolicyManager.DELEGATION_PERMISSION_GRANT)
                    val isPackageAccessChange =
                        scopes.contains(DevicePolicyManager.DELEGATION_PACKAGE_ACCESS)
                    val isEnableSystemAppChange =
                        scopes.contains(DevicePolicyManager.DELEGATION_ENABLE_SYSTEM_APP)
                    val isPackageManagementChange =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            scopes.contains(DevicePolicyManager.DELEGATION_KEEP_UNINSTALLED_PACKAGES) || scopes.contains(
                                DevicePolicyManager.DELEGATION_INSTALL_EXISTING_PACKAGE
                            )
                        } else {
                            false
                        }
                    val isLoggingChange = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        scopes.contains(DevicePolicyManager.DELEGATION_NETWORK_LOGGING) || scopes.contains(
                            DevicePolicyManager.DELEGATION_SECURITY_LOGGING
                        )
                    } else {
                        false
                    }
                    vm.isCert().postValue(isCertChange)
                    vm.isManagedConfig().postValue(isManagedConfigChange)
                    vm.isBlockUninstall().postValue(isBlockUninstallChange)
                    vm.isPermission().postValue(isPermissionChange)
                    vm.isPackageAccess().postValue(isPackageAccessChange)
                    vm.isEnableSystemApp().postValue(isEnableSystemAppChange)
                    vm.isPackageManagement().postValue(isPackageManagementChange)
                    vm.isLogging().postValue(isLoggingChange)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        ContextCompat.registerReceiver(
            requireContext(),
            receiver,
            IntentFilter(DevicePolicyManager.ACTION_APPLICATION_DELEGATION_SCOPES_CHANGED),
            ContextCompat.RECEIVER_EXPORTED
        )
        // Inflate the layout for this fragment
        return FragmentMenuBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        handleEvent()
    }

    private fun setupUi() {
        val recyclerViewData = getRecyclerViewData()
        adapter = MenuAdapter(::onRecyclerViewItemClick)
        adapter.setData(recyclerViewData)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun handleEvent() {
        vm.isCert().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(0)
        }
        vm.isManagedConfig().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(1)
        }
        vm.isBlockUninstall().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(2)
        }
        vm.isPermission().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(3)
        }
        vm.isPackageAccess().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(4)
        }
        vm.isEnableSystemApp().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(5)
        }
        vm.isPackageManagement().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(6)
        }
        vm.isLogging().observe(viewLifecycleOwner) {
            adapter.setData(getRecyclerViewData())
            binding.recyclerView.adapter?.notifyItemChanged(7)
        }
    }

    private fun onRecyclerViewItemClick(data: ScopeData) {
        when (data.delegationValue) {
            ScopeType.DELEGATION_CERT -> goto(MenuFragmentDirections.actionMenuFragmentToCertFragment())
            ScopeType.DELEGATION_APP_RESTRICTIONS -> goto(MenuFragmentDirections.actionMenuFragmentToRestrictFragment())
            ScopeType.DELEGATION_BLOCK_UNINSTALL -> goto(MenuFragmentDirections.actionMenuFragmentToBlockUninstallFragment())
            ScopeType.DELEGATION_PERMISSION_GRANT -> goto(MenuFragmentDirections.actionMenuFragmentToPermissionFragment())
            ScopeType.DELEGATION_PACKAGE_ACCESS -> goto(MenuFragmentDirections.actionMenuFragmentToPackageAccessFragment())
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP -> goto(MenuFragmentDirections.actionMenuFragmentToEnableSystemAppFragment())
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT -> goto(MenuFragmentDirections.actionMenuFragmentToPackageManageFragment())
            ScopeType.DELEGATION_LOGGING -> goto(MenuFragmentDirections.actionMenuFragmentToLoggingFragment())
        }
    }

    private fun goto(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun getRecyclerViewData(): List<ScopeData> = listOf(
        ScopeData(
            "Certificate installation and management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.round_workspace_premium_24)!!,
            ScopeType.DELEGATION_CERT,
            vm.isCert().value
        ),
        ScopeData(
            "Managed configurations management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_manage_accounts_24)!!,
            ScopeType.DELEGATION_APP_RESTRICTIONS,
            vm.isManagedConfig().value
        ),
        ScopeData(
            "Blocking uninstallation",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_block_24)!!,
            ScopeType.DELEGATION_BLOCK_UNINSTALL,
            vm.isBlockUninstall().value
        ),
        ScopeData(
            "Permission policy and permission grant state",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_policy_24)!!,
            ScopeType.DELEGATION_PERMISSION_GRANT,
            vm.isPermission().value
        ),
        ScopeData(
            "Package access state", ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_round_system_security_update_good_24
            )!!, ScopeType.DELEGATION_PACKAGE_ACCESS, vm.isPackageAccess().value
        ),
        ScopeData(
            "Enabling system apps",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_inventory_24)!!,
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP,
            vm.isEnableSystemApp().value
        ),
        ScopeData(
            "Package management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_get_app_24)!!,
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT,
            vm.isPackageManagement().value
        ),
        ScopeData(
            "Logging",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_rss_feed_24)!!,
            ScopeType.DELEGATION_LOGGING,
            vm.isLogging().value
        ),
    )

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(receiver)
    }

    companion object {
        private val TAG = MenuFragment::class.simpleName
    }
}