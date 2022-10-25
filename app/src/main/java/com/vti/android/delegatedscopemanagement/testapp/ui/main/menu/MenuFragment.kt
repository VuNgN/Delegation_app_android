package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentMenuBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        val recyclerViewData = getRecyclerViewData()
        val adapter = MenuAdapter(::onRecyclerViewItemClick)
        adapter.setData(recyclerViewData)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
            vm.isCert().value == true
        ),
        ScopeData(
            "Managed configurations management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_manage_accounts_24)!!,
            ScopeType.DELEGATION_APP_RESTRICTIONS,
            vm.isManagedConfig().value == true
        ),
        ScopeData(
            "Blocking uninstallation",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_block_24)!!,
            ScopeType.DELEGATION_BLOCK_UNINSTALL,
            vm.isBlockUninstall().value == true
        ),
        ScopeData(
            "Permission policy and permission grant state",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_policy_24)!!,
            ScopeType.DELEGATION_PERMISSION_GRANT,
            vm.isPermission().value == true
        ),
        ScopeData(
            "Package access state",
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_round_system_security_update_good_24
            )!!,
            ScopeType.DELEGATION_PACKAGE_ACCESS,
            vm.isPackageAccess().value == true
        ),
        ScopeData(
            "Enabling system apps",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_inventory_24)!!,
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP,
            vm.isEnableSystemApp().value == true
        ),
        ScopeData(
            "Package management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_get_app_24)!!,
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT,
            vm.isPackageManagement().value == true
        ),
        ScopeData(
            "Logging",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_rss_feed_24)!!,
            ScopeType.DELEGATION_LOGGING,
            vm.isLogging().value == true
        ),
    )
}