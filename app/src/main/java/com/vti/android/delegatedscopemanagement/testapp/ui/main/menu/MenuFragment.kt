package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
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
                    vm.scopes().postValue(scopes)
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
        vm.getScopes()
        val recyclerViewData = getRecyclerViewData(scopes = vm.scopes().value ?: listOf())
        adapter = MenuAdapter(::onRecyclerViewItemClick)
        adapter.setData(recyclerViewData)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun handleEvent() {
        vm.scopes().observe(viewLifecycleOwner) { scopes ->
            val recyclerViewData = getRecyclerViewData(scopes)
            adapter.setData(recyclerViewData)
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
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
            ScopeType.DELEGATION_SECURITY_LOGGING -> goto(MenuFragmentDirections.actionMenuFragmentToSecurityLogsFragment())
            ScopeType.DELEGATION_NETWORK_LOGGING -> goto(MenuFragmentDirections.actionMenuFragmentToNetworkingLogsFragment())
        }
    }

    private fun goto(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun getRecyclerViewData(scopes: List<String>): List<ScopeData> = listOf(
        ScopeData(
            "Certificate installation and management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.round_workspace_premium_24)!!,
            ScopeType.DELEGATION_CERT,
            scopes.contains(DevicePolicyManager.DELEGATION_CERT_INSTALL)
        ),
        ScopeData(
            "Managed configurations management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_manage_accounts_24)!!,
            ScopeType.DELEGATION_APP_RESTRICTIONS,
            scopes.contains(DevicePolicyManager.DELEGATION_APP_RESTRICTIONS)
        ),
        ScopeData(
            "Blocking uninstallation",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_block_24)!!,
            ScopeType.DELEGATION_BLOCK_UNINSTALL,
            scopes.contains(DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL)
        ),
        ScopeData(
            "Permission policy and permission grant state",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_policy_24)!!,
            ScopeType.DELEGATION_PERMISSION_GRANT,
            scopes.contains(DevicePolicyManager.DELEGATION_PERMISSION_GRANT)
        ),
        ScopeData(
            "Package access state",
            ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_round_system_security_update_good_24
            )!!,
            ScopeType.DELEGATION_PACKAGE_ACCESS,
            scopes.contains(DevicePolicyManager.DELEGATION_PACKAGE_ACCESS)
        ),
        ScopeData(
            "Enabling system apps",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_inventory_24)!!,
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP,
            scopes.contains(DevicePolicyManager.DELEGATION_ENABLE_SYSTEM_APP)
        ),
        ScopeData(
            "Package management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_get_app_24)!!,
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT,
            if (VERSION.SDK_INT >= VERSION_CODES.P) scopes.contains(DevicePolicyManager.DELEGATION_KEEP_UNINSTALLED_PACKAGES) || scopes.contains(
                DevicePolicyManager.DELEGATION_INSTALL_EXISTING_PACKAGE
            ) else false
        ),
        ScopeData(
            "Networking logs",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_rss_feed_24)!!,
            ScopeType.DELEGATION_NETWORK_LOGGING,
            if (VERSION.SDK_INT >= VERSION_CODES.S) scopes.contains(DevicePolicyManager.DELEGATION_NETWORK_LOGGING) else false
        ),
        ScopeData(
            "Security logs",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_security_24)!!,
            ScopeType.DELEGATION_SECURITY_LOGGING,
            if (VERSION.SDK_INT >= VERSION_CODES.S) scopes.contains(DevicePolicyManager.DELEGATION_SECURITY_LOGGING) else false
        ),
    )

    override fun onDestroy() {
        super.onDestroy()
        try {
            requireActivity().unregisterReceiver(receiver)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "onDestroy: ${e.message}")
        }
    }

    companion object {
        private val TAG = MenuFragment::class.simpleName
    }
}