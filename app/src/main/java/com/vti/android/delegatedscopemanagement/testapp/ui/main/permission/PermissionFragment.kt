package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vti.android.delegatedscopemanagement.testapp.R
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentPermissionBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.adapter.PermissionCollectionAdapter

class PermissionFragment : Fragment() {
    private lateinit var binding: FragmentPermissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPermissionBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        handleEvent()
    }

    private fun setupUi() {
        val permissionCollectionAdapter = PermissionCollectionAdapter(this)
        binding.apply {
            viewPager.adapter = permissionCollectionAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = context?.resources?.getString(R.string.grant_state)
                    1 -> tab.text = context?.resources?.getString(R.string.policy)
                }
            }.attach()
        }
    }

    private fun handleEvent() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}