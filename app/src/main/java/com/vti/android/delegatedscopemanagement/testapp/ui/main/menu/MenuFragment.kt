package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.R
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeData
import com.vti.android.delegatedscopemanagement.testapp.data.pojo.ScopeType
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentMenuBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.adapter.MenuAdapter

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

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
            ScopeType.DELEGATION_APP_RESTRICTIONS -> {}
            ScopeType.DELEGATION_BLOCK_UNINSTALL -> {}
            ScopeType.DELEGATION_PERMISSION_GRANT -> {}
            ScopeType.DELEGATION_PACKAGE_ACCESS -> {}
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP -> {}
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT -> {}
            ScopeType.DELEGATION_LOGGING -> {}
        }
    }

    private fun goto(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun getRecyclerViewData(): List<ScopeData> = listOf(
        ScopeData(
            "Certificate installation and management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.round_workspace_premium_24)!!,
            ScopeType.DELEGATION_CERT
        ),
        ScopeData(
            "Managed configurations management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_manage_accounts_24)!!,
            ScopeType.DELEGATION_APP_RESTRICTIONS
        ),
        ScopeData(
            "Blocking uninstallation",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_block_24)!!,
            ScopeType.DELEGATION_BLOCK_UNINSTALL
        ),
        ScopeData(
            "Permission policy and permission grant state",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_policy_24)!!,
            ScopeType.DELEGATION_PERMISSION_GRANT
        ),
        ScopeData(
            "Package access state",
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_round_system_security_update_good_24
            )!!,
            ScopeType.DELEGATION_PACKAGE_ACCESS
        ),
        ScopeData(
            "Enabling system apps",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_inventory_24)!!,
            ScopeType.DELEGATION_ENABLE_SYSTEM_APP
        ),
        ScopeData(
            "Package management",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_get_app_24)!!,
            ScopeType.DELEGATION_PACKAGE_MANAGEMENT
        ),
        ScopeData(
            "Logging",
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_round_rss_feed_24)!!,
            ScopeType.DELEGATION_LOGGING
        ),
    )
}